package org.example.duan.repository;

import org.example.duan.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorsRepository extends JpaRepository<ColorEntity, Integer> {
    List<ColorEntity> findAll();  // Lấy tất cả màu sắc từ cơ sở dữ liệu
}

