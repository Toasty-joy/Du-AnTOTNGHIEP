package org.example.duan.controller;

import jakarta.servlet.http.HttpSession;
import org.example.duan.entity.AccountEntity;
import org.example.duan.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CartService cartService;

    // Thêm tổng số lượng giỏ hàng vào model của tất cả các trang
    @ModelAttribute
    public void addCartInfoToModel(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        String username = null;
        if (session.getAttribute("loggedInUser") != null) {
            username = ((AccountEntity) session.getAttribute("loggedInUser")).getUsername(); // Lấy username từ session
        }

        if (username != null) {
            int totalQuantity = cartService.getTotalQuantity(username); // Lấy tổng số lượng sản phẩm trong giỏ hàng
            model.addAttribute("totalQuantity", totalQuantity);
        } else {
            model.addAttribute("totalQuantity", 0); // Nếu chưa đăng nhập, giỏ hàng sẽ trống
        }
    }
}
