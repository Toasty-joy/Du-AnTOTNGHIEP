package org.example.duan.repository;

import org.example.duan.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {
    // Truy vấn để lấy tất cả các chi tiết đơn hàng theo id đơn hàng
    List<OrderDetailsEntity> findByOrderId(Long orderId);

    // Thêm các truy vấn tùy chỉnh nếu cần
}
