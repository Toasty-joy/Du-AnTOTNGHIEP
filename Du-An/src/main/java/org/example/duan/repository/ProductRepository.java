package org.example.duan.repository;

import org.example.duan.entity.ColorEntity;
import org.example.duan.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    // Tìm 4 sản phẩm đầu tiên theo categoryId, sắp xếp theo ID tăng dần
    List<ProductEntity> findTop4ByCategoryIdOrderByIdAsc(Integer categoryId);

    // Tìm sản phẩm theo tên, không phân biệt chữ hoa/thường
    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    // Tìm sản phẩm theo sizeId
    @Query("SELECT p FROM ProductEntity p JOIN p.sizesProducts sp WHERE sp.size.id = :sizeId")
    List<ProductEntity> findBySizeId(@Param("sizeId") int sizeId);

    // Tìm sản phẩm theo màu
    List<ProductEntity> findByShoesImages_Color(ColorEntity color);
    @Query("SELECT SUM(p.quantity) FROM ProductEntity p WHERE p.quantity > 0")
    Long sumQuantityInStock();
}
