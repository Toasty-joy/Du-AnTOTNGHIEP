package org.example.duan.repository;

import org.example.duan.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // Thêm các truy vấn tùy chỉnh nếu cần, ví dụ:
    // Optional<OrderEntity> findByUsername(String username);
}
