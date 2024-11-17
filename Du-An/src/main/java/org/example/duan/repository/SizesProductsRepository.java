package org.example.duan.repository;

import org.example.duan.entity.SizesProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizesProductsRepository extends JpaRepository<SizesProductsEntity, Integer> {
}
