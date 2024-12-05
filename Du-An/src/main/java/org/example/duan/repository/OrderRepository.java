package org.example.duan.repository;

import org.example.duan.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByAccount_Username(String username);

    // Tìm kiếm đơn hàng theo trạng thái
    List<OrderEntity> findByStatus(int status);
    // Tính tổng doanh thu trong ngày
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM OrderEntity o WHERE DATE(o.createDate) = CURRENT_DATE AND o.status = 2")
    BigDecimal findTotalRevenueToday();

    // Đếm số đơn hàng trong ngày
    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE DATE(o.createDate) = CURRENT_DATE AND o.status = 2")
    long countOrdersToday();

    @Query("SELECT COUNT(DISTINCT o.account.username) " + "FROM OrderEntity o " + "WHERE DATE(o.createDate) = CURRENT_DATE") long countDistinctCustomersToday();

    @Query("SELECT o FROM OrderEntity o WHERE o.status IN (0, 1)")
    List<OrderEntity> findOrdersByStatusInProcessingOrConfirmed();
}
