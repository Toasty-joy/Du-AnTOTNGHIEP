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


    // Lấy 4 sản phẩm đầu tiên của danh mục "Giày Cao Gót Nữ" (categoryId = 100)
    public List<ProductEntity> getTop4HighHeels() {
        return productRepository.findTop4ByCategoryIdOrderByIdAsc(100); // categoryId = 100
    }

    // Lấy 4 sản phẩm đầu tiên của danh mục "Dép và Sandal Nữ" (categoryId = 101)
    public List<ProductEntity> getTop4Sandals() {
        return productRepository.findTop4ByCategoryIdOrderByIdAsc(101); // categoryId = 101
    }
}

