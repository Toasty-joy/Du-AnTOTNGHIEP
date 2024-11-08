package org.example.duan.repository;

import org.example.duan.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    // Truy vấn 4 sản phẩm đầu tiên theo categoryId, sắp xếp theo Id tăng dần
    List<ProductEntity> findTop4ByCategoryIdOrderByIdAsc(Integer categoryId);
}
