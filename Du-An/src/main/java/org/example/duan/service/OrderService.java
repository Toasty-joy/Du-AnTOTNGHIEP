package org.example.duan.service;

import org.example.duan.entity.OrderDetailsEntity;
import org.example.duan.entity.OrderEntity;
import org.example.duan.repository.OrderDetailsRepository;
import org.example.duan.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
        // Nếu trạng thái là "Đã hủy", cập nhật tổng tiền về 0
        if (status == 3) {
            order.setTotal(BigDecimal.ZERO);
        }
        // Lưu đơn hàng với trạng thái mới
        return orderRepository.save(order);
    }
    // Lấy tổng doanh thu trong ngày
    public BigDecimal getTodayRevenue() {
        return orderRepository.findTotalRevenueToday();
    }

    // Lấy số đơn hàng trong ngày
    public long getTodayOrdersCount() {
        return orderRepository.countOrdersToday();
    }

    // Lấy tất cả đơn hàng
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
    // Lấy số khách hàng mua hàng hôm nay
    public long getTodayCustomersCount() {
        return orderRepository.countDistinctCustomersToday();
    }
    // Lấy danh sách đơn hàng đang chờ xác nhận (status = 0)
    public List<OrderEntity> getPendingOrders() {
        return orderRepository.findOrdersByStatusInProcessingOrConfirmed();
    }

    public List<Map<String, Object>> getMonthlyRevenue(int year) {
        List<Object[]> results = orderRepository.getRevenueByMonth(year);
        List<Map<String, Object>> revenues = new ArrayList<>();

        // Khởi tạo danh sách doanh thu cho 12 tháng với giá trị mặc định
        for (int i = 1; i <= 12; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("month", i);
            data.put("totalRevenue", BigDecimal.ZERO); // Doanh thu mặc định là 0
            revenues.add(data);
        }

        // Ghi đè doanh thu thực tế từ cơ sở dữ liệu
        for (Object[] result : results) {
            int month = (int) result[0]; // Lấy số tháng từ kết quả truy vấn
            BigDecimal totalRevenue = (BigDecimal) result[1]; // Lấy tổng doanh thu
            revenues.get(month - 1).put("totalRevenue", totalRevenue); // Cập nhật doanh thu cho tháng tương ứng
        }

        return revenues;
    }


    public List<Map<String, Object>> getYearlyRevenue() {
        List<Object[]> results = orderRepository.getRevenueByYear();
        List<Map<String, Object>> revenues = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("year", result[0]);  // Năm
            data.put("totalRevenue", result[1]);  // Tổng doanh thu
            revenues.add(data);
        }
        return revenues;
    }
    public List<Object[]> getTopSellingProducts() {
        return orderDetailsRepository.findTopSellingProducts();
    }
}
