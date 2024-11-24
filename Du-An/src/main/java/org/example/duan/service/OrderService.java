package org.example.duan.service;

import org.example.duan.entity.OrderDetailsEntity;
import org.example.duan.entity.OrderEntity;
import org.example.duan.repository.OrderDetailsRepository;
import org.example.duan.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    // Lưu đơn hàng
    public void saveOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    // Lưu chi tiết đơn hàng
    public void saveOrderDetails(OrderDetailsEntity orderDetails) {
        orderDetailsRepository.save(orderDetails);
    }

    // Lấy danh sách đơn hàng của người dùng theo tên tài khoản
    public List<OrderEntity> getOrdersByUsername(String username) {
        return orderRepository.findByAccount_Username(username);
    }

    // Lấy danh sách đơn hàng theo trạng thái
    public List<OrderEntity> getOrdersByStatus(int status) {
        return orderRepository.findByStatus(status);
    }

    // Lấy chi tiết đơn hàng theo ID
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mã đơn hàng không hợp lệ"));
    }

    // Cập nhật trạng thái đơn hàng
    @Transactional
    public OrderEntity updateOrderStatus(Long orderId, int status) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Mã đơn hàng không hợp lệ"));

        // Cập nhật trạng thái của đơn hàng
        order.setStatus(status);

        // Lưu đơn hàng với trạng thái mới
        return orderRepository.save(order);
    }
}
