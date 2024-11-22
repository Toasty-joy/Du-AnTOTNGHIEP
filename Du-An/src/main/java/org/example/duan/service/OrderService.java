package org.example.duan.service;

import org.example.duan.entity.OrderDetailsEntity;
import org.example.duan.entity.OrderEntity;
import org.example.duan.repository.OrderDetailsRepository;
import org.example.duan.repository.OrderRepository;
import org.springframework.stereotype.Service;

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
}
