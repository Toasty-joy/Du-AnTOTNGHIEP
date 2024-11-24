package org.example.duan.service;

import org.example.duan.entity.ProductEntity;
import org.example.duan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy top 4 sản phẩm theo category ID
    public List<ProductEntity> getTop4ByCategoryId(int categoryId) {
        return productRepository.findTop4ByCategoryIdOrderByIdAsc(categoryId);
    }

    // Các phương thức cụ thể cho Giày Cao Gót và Sandal
    public List<ProductEntity> getTop4HighHeels() {
        return getTop4ByCategoryId(100);  // Giày Cao Gót Nữ (categoryId = 100)
    }

    public List<ProductEntity> getTop4Sandals() {
        return getTop4ByCategoryId(101);  // Dép & Sandal Nữ (categoryId = 101)
    }
    // Lấy sản phẩm theo ID
    public ProductEntity getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    public void updateProduct(ProductEntity product) {
        productRepository.save(product); // Lưu lại sản phẩm đã cập nhật số lượng
    }
    public List<ProductEntity> searchProducts(String searchTerm) {
        return productRepository.findByNameContainingIgnoreCase(searchTerm);
    }

}
