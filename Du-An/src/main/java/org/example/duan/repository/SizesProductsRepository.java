package org.example.duan.repository;

import org.example.duan.entity.SizesProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizesProductsRepository extends JpaRepository<SizesProductsEntity, Integer> {
    // Kiểm tra nếu bản ghi đã tồn tại trong bảng Sizes_Products
    @Query("SELECT sp FROM SizesProductsEntity sp WHERE sp.product.id = :productId AND sp.size.id = :sizeId")
    Optional<SizesProductsEntity> findByProductIdAndSizeId(@Param("productId") int productId, @Param("sizeId") int sizeId);
}
