package org.example.duan.repository;

import org.example.duan.entity.ShoesImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoesImagesRepository extends JpaRepository<ShoesImagesEntity, Integer> {
    List<ShoesImagesEntity> findTop6ByProductIdOrderByOrderImageAsc(Integer productId);

    // Truy vấn tất cả các ảnh của sản phẩm, không cần giới hạn số lượng
    List<ShoesImagesEntity> findByProductId(Integer productId);
}
