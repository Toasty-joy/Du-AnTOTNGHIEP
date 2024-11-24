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

    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        }

        // Truyền thông tin phone và address vào model
        model.addAttribute("address", loggedInUser.getAddress());
        model.addAttribute("phone", loggedInUser.getPhone());

        // Truyền giá trị paymentMethod vào model mặc định là 0 (Thanh toán khi nhận hàng)
        model.addAttribute("paymentMethod", 0);

        return "giohang";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("address") String address,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("paymentMethod") int paymentMethod,
                                  HttpSession session, Model model) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (loggedInUser == null) {
            return "redirect:/account/login"; // Chuyển hướng đến trang đăng nhập
        }

        // Kiểm tra nếu địa chỉ hoặc số điện thoại không hợp lệ
        if (address == null || address.isEmpty() || phone == null || phone.isEmpty()) {
            model.addAttribute("error", "Bạn cần nhập đầy đủ địa chỉ và số điện thoại.");
            return "redirect:/checkout"; // Chuyển hướng về trang checkout để nhập lại thông tin
        }

        // Lấy giỏ hàng và tính tổng giá trị
        List<CartEntity> cartItems = cartService.getCartItemsByUsername(loggedInUser.getUsername());
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm trước khi thanh toán.");
            return "redirect:/cart"; // Chuyển hướng về giỏ hàng
        }
        double totalPrice = cartService.getTotalPrice(loggedInUser.getUsername());

        // Tạo đơn hàng mới
        OrderEntity order = new OrderEntity();
        order.setAccount(loggedInUser);
        order.setCreateDate(LocalDateTime.now());
        order.setAddress(address);
        order.setPhone(phone); // Thêm số điện thoại vào đơn hàng
        order.setTotal(BigDecimal.valueOf(totalPrice));
        order.setStatus(0); // Đơn hàng chưa thanh toán
        order.setPaymentMethod(paymentMethod); // Lưu phương thức thanh toán vào đơn hàng

        // Lưu đơn hàng vào cơ sở dữ liệu
        orderService.saveOrder(order);

        // Lưu chi tiết đơn hàng và cập nhật tồn kho sản phẩm
        for (CartEntity cartItem : cartItems) {
            OrderDetailsEntity orderDetails = new OrderDetailsEntity();
            ProductEntity product = productService.getProductById(cartItem.getProduct().getId());

            // Giảm số lượng tồn kho của sản phẩm
            int newQuantity = product.getQuantity() - cartItem.getQuantity();
            if (newQuantity < 0) {
                model.addAttribute("error", "Sản phẩm " + product.getName() + " không đủ hàng trong kho.");
                return "redirect:/cart"; // Chuyển hướng lại giỏ hàng nếu không đủ hàng
            }
            product.setQuantity(newQuantity);
            productService.updateProduct(product); // Cập nhật lại thông tin sản phẩm trong cơ sở dữ liệu

            // Thiết lập thông tin sản phẩm cho đơn hàng
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setPrice(BigDecimal.valueOf(product.getPrice()));
            orderDetails.setQuantity(cartItem.getQuantity());
            orderDetails.setColor(cartItem.getColor()); // Thiết lập màu sắc từ CartItem
            orderDetails.setSize(cartItem.getSize());   // Thiết lập kích cỡ từ CartItem

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
        model.addAttribute("message", "Cảm ơn bạn đã đặt hàng!");
        return "camOn";
    }
}
