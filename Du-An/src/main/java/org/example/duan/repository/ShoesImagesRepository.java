package org.example.duan.repository;

import org.example.duan.entity.ShoesImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoesImagesRepository extends JpaRepository<ShoesImagesEntity, Integer> {

    // Lấy 6 ảnh đầu tiên của sản phẩm theo sản phẩm ID, sắp xếp theo thứ tự ảnh
    List<ShoesImagesEntity> findTop6ByProductIdOrderByOrderImageAsc(Integer productId);

    // Lấy tất cả ảnh của sản phẩm
    List<ShoesImagesEntity> findByProductId(Integer productId);

    // Truy vấn tất cả các ảnh
    List<ShoesImagesEntity> findAll();
}
