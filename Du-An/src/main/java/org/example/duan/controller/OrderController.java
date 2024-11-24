package org.example.duan.controller;

import org.example.duan.entity.AccountEntity;
import org.example.duan.entity.OrderEntity;
import org.example.duan.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Hiển thị danh sách đơn hàng của người dùng hiện tại
    @GetMapping
    public String showUserOrders(HttpSession session, Model model) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/account/login"; // Nếu chưa đăng nhập, chuyển về trang login
        }

        List<OrderEntity> orders = orderService.getOrdersByUsername(loggedInUser.getUsername());
        model.addAttribute("orders", orders);
        return "donHang"; // Giao diện danh sách đơn hàng của người dùng
    }

    // Hiển thị chi tiết đơn hàng
    @GetMapping("/{id}")
    public String showOrderDetails(@PathVariable Long id, Model model, HttpSession session) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/account/login"; // Nếu chưa đăng nhập, chuyển về trang login
        }

        OrderEntity order = orderService.getOrderById(id);
        if (order == null || !order.getAccount().getUsername().equals(loggedInUser.getUsername())) {
            return "redirect:/orders"; // Nếu đơn hàng không tồn tại hoặc không thuộc về người dùng, quay lại danh sách
        }

        model.addAttribute("order", order);
        return "donHangChiTiet"; // Giao diện chi tiết đơn hàng
    }


}
