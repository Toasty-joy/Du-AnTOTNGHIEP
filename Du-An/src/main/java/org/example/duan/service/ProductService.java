package org.example.duan.service;

import org.example.duan.entity.ProductEntity;
import org.example.duan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getTop4ProductsByCategoryId(Integer categoryId) {
        return productRepository.findTop4ByCategoryId(categoryId);
    }
}

