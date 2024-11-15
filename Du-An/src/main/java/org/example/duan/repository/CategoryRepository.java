package org.example.duan.repository;

import org.example.duan.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query("SELECT c.name FROM CategoryEntity c WHERE c.id = :categoryId")
    String findCategoryNameById(@Param("categoryId") int categoryId);
}

