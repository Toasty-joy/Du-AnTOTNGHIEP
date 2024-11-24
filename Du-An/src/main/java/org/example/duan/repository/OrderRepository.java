package org.example.duan.repository;

import org.example.duan.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByAccount_Username(String username);

    // Tìm kiếm đơn hàng theo trạng thái
    List<OrderEntity> findByStatus(int status);
}
