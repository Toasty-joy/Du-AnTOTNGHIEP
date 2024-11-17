package org.example.duan.repository;

import org.example.duan.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Integer> {

    // Truy vấn để lấy các kích cỡ cho một sản phẩm từ bảng Sizes_Products
    @Query("SELECT s FROM SizeEntity s JOIN SizesProductsEntity sp ON s.id = sp.size.id WHERE sp.product.id = :productId")
    List<SizeEntity> findByProductId(@Param("productId") int productId);
}
