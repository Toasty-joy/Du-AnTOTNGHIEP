package org.example.duan.controller;


import jakarta.servlet.http.HttpSession;
import org.example.duan.entity.*;
import org.example.duan.service.CartService;
import org.example.duan.service.OrderService;
import org.example.duan.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final OrderService orderService;

    public CartController(CartService cartService, ProductService productService, OrderService orderService) {
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public String showCartPage(HttpSession session, Model model) {
        // Lấy thông tin người dùng từ session (AccountEntity)
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Chuyển đến trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy username từ AccountEntity
        String username = loggedInUser.getUsername();

        // Lấy giỏ hàng của người dùng
        List<CartEntity> cartItems = cartService.getCartItemsByUsername(username);

        // Tính tổng giá trị giỏ hàng
        double totalPrice = cartService.getTotalPrice(username);

        // Thêm thông tin vào model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        // Truyền thông tin phone và address vào model
        model.addAttribute("address", loggedInUser.getAddress());
        model.addAttribute("phone", loggedInUser.getPhone());
        // Truyền giá trị paymentMethod vào model mặc định là 0 (Thanh toán khi nhận hàng)
        model.addAttribute("paymentMethod", 0);
        model.addAttribute("totalPrice", totalPrice);

        return "giohang"; // View giỏ hàng
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam("quantity") int quantity,
                            @RequestParam("colorId") int colorId,
                            @RequestParam("sizeId") int sizeId,
                            @RequestParam(value = "action", required = false) String action,
                            HttpSession session,
                            Model model) {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Chuyển đến trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy username từ AccountEntity
        String username = loggedInUser.getUsername();

        // Gọi service để thêm sản phẩm vào giỏ hàng
        cartService.addProductToCart(username, productId, quantity, colorId, sizeId);



        if ("buyNow".equals(action)) {
            // Chuyển hướng đến trang thanh toán nếu bấm "Mua ngay"
            return "redirect:/cart";
        } else {
            // Thêm thông báo thành công vào model nếu bấm "Thêm vào giỏ hàng"
            model.addAttribute("notification", "Sản phẩm đã được thêm vào giỏ hàng!");

            // Chuyển hướng đến trang giỏ hàng
            return "redirect:/product/"+productId;
        }
    }


    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/remove")
    public String removeFromCart(
            @RequestParam("productId") int productId,
            @RequestParam("colorId") int colorId,
            @RequestParam("sizeId") int sizeId,
            HttpSession session,
            Model model) {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Chuyển đến trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy username từ AccountEntity
        String username = loggedInUser.getUsername();

        // Gọi service để xóa sản phẩm khỏi giỏ
        cartService.removeProductFromCart(username, productId, colorId, sizeId);

        // Thêm thông báo thành công vào model
        model.addAttribute("notification", "Sản phẩm đã được xóa khỏi giỏ hàng!");

        // Quay lại trang giỏ hàng
        return "redirect:/cart";
    }
    @PostMapping("/updateQuantity")
    public String updateQuantity(
            @RequestParam("productId") int productId,
            @RequestParam("colorId") int colorId,
            @RequestParam("sizeId") int sizeId,
            @RequestParam("quantity") int quantity,
            @RequestParam("action") String action,
            HttpSession session,
            Model model) {

        // Kiểm tra xem người dùng đã đăng nhập chưa
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Chuyển đến trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy username từ AccountEntity
        String username = loggedInUser.getUsername();

        // Lấy số lượng còn lại trong kho
        ProductEntity product = productService.getProductById(productId);
        int maxQuantity = product.getQuantity();

        // Kiểm tra số lượng người dùng muốn thay đổi
        if (quantity > maxQuantity) {
            quantity = maxQuantity; // Nếu số lượng vượt quá số lượng trong kho, đặt lại thành số lượng tối đa
        }

        // Nếu người dùng muốn tăng số lượng
        if (action.equals("increase")) {
            if (quantity < maxQuantity) {
                quantity++; // Tăng số lượng nếu không vượt quá số lượng trong kho
            }
        }

        // Nếu người dùng muốn giảm số lượng
        else if (action.equals("decrease") && quantity > 1) {
            quantity--; // Giảm số lượng nếu số lượng lớn hơn 1
        } else if (action.equals("decrease") && quantity == 1) {
            quantity = 0; // Nếu giảm xuống 1, đặt số lượng thành 0 để xóa sản phẩm khỏi giỏ hàng
        }

        // Cập nhật lại số lượng trong giỏ hàng
        cartService.updateProductQuantity(username, productId, colorId, sizeId, quantity);

        // Quay lại trang giỏ hàng
        return "redirect:/cart";
    }

}






