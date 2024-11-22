package org.example.duan.controller;

import jakarta.servlet.http.HttpSession;
import org.example.duan.entity.*;
import org.example.duan.service.CartService;
import org.example.duan.service.OrderService;
import org.example.duan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("address") String address,
                                  @RequestParam("phone") String phone,
                                  HttpSession session, Model model) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (loggedInUser == null) {
            return "redirect:/account/login"; // Chuyển hướng đến trang đăng nhập
        }

        // Kiểm tra nếu địa chỉ hoặc số điện thoại không hợp lệ
        if (address == null || address.isEmpty() || phone == null || phone.isEmpty()) {
            model.addAttribute("error", "Bạn cần nhập đầy đủ địa chỉ và số điện thoại.");
            return "redirect:/account/profile"; // Chuyển hướng về trang profile để nhập thông tin
        }

        // Lấy giỏ hàng và tính tổng giá trị
        List<CartEntity> cartItems = cartService.getCartItemsByUsername(loggedInUser.getUsername());
        double totalPrice = cartService.getTotalPrice(loggedInUser.getUsername());

        // Tạo đơn hàng mới
        OrderEntity order = new OrderEntity();
        order.setAccount(loggedInUser);
        order.setCreateDate(LocalDateTime.now());
        order.setAddress(address);
        order.setPhone(phone);  // Thêm số điện thoại vào đơn hàng
        order.setTotal(BigDecimal.valueOf(totalPrice));
        order.setStatus(false); // Đơn hàng chưa thanh toán

        // Lưu đơn hàng vào cơ sở dữ liệu
        orderService.saveOrder(order);

        // Lưu chi tiết đơn hàng
        for (CartEntity cartItem : cartItems) {
            OrderDetailsEntity orderDetails = new OrderDetailsEntity();
            ProductEntity product = productService.getProductById(cartItem.getProduct().getId());

            // Thiết lập thông tin sản phẩm cho đơn hàng
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setPrice(BigDecimal.valueOf(product.getPrice()));
            orderDetails.setQuantity(cartItem.getQuantity());

            // Thiết lập màu sắc từ CartItem
            orderDetails.setColor(cartItem.getColor());  // Thiết lập màu sắc

            // Thiết lập kích cỡ từ CartItem
            orderDetails.setSize(cartItem.getSize());  // Thiết lập kích cỡ ở đây

            // Lưu chi tiết vào DB
            orderService.saveOrderDetails(orderDetails);
        }

        // Xóa giỏ hàng sau khi thanh toán
        cartService.clearCart(loggedInUser.getUsername());

        // Thêm thông báo thành công vào model
        model.addAttribute("notification", "Đơn hàng đã được thanh toán thành công!");

        // Chuyển hướng tới trang cảm ơn
        return "redirect:/camOn";
    }
    @GetMapping("/camOn")
    public String showThankYouPage(Model model) {
        model.addAttribute("message", "Cảm ơn bạn đã đặt hàng!"); // Optional message for the user
        return "camOn"; // This should match the name of your Thymeleaf template (e.g., "thankYou.html")
    }
}

