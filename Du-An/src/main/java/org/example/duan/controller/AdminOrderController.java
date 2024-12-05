package org.example.duan.controller;

import org.example.duan.entity.OrderEntity;
import org.example.duan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    // Hiển thị danh sách đơn hàng
    @GetMapping
    public String listOrders(@RequestParam(value = "status", required = false) Integer status, Model model) {
        List<OrderEntity> orders = (status == null)
                ? orderService.getAllOrders()
                : orderService.getOrdersByStatus(status);

        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    // Cập nhật trạng thái đơn hàng
    @PostMapping("/{id}/update-status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam("status") int status) {
        // Gọi service để cập nhật trạng thái và tổng tiền nếu trạng thái là "Đã hủy"
        orderService.updateOrderStatus(id, status);

        return "redirect:/admin/orders";
    }
    @GetMapping("/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        OrderEntity order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "admin/order-details";
    }


}

