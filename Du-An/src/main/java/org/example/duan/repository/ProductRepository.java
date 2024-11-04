package org.example.duan.repository;

import org.example.duan.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query(value = "SELECT * FROM Products WHERE category_id = :categoryId AND is_delete = 0 ORDER BY create_date LIMIT 4", nativeQuery = true)
    List<ProductEntity> findTop4ByCategoryId(@Param("categoryId") Integer categoryId);
}