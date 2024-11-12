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

    // Get all products
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a single product by ID
    public Optional<ProductEntity> getProductById(int id) {
        return productRepository.findById(id);
    }
    public ProductEntity saveProduct(ProductEntity product) {
        // If the product ID exists, this will act as an update. If not, it creates a new product.
        return productRepository.save(product);
    }
    // Create a new product
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    // Update an existing product
    public ProductEntity updateProduct(int id, ProductEntity updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setImage(updatedProduct.getImage());
                    product.setPrice(updatedProduct.getPrice());
                    product.setCreateDate(updatedProduct.getCreateDate());
                    product.setQuantity(updatedProduct.getQuantity());
                    product.setDelete(updatedProduct.isDelete());
                    product.setCategory(updatedProduct.getCategory());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    // Delete a product by ID
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    // Get top 4 products by category ID
    public List<ProductEntity> getTop4ByCategoryId(int categoryId) {
        return productRepository.findTop4ByCategoryIdOrderByIdAsc(categoryId);
    }

    // Specific methods for high heels and sandals (if needed)
    public List<ProductEntity> getTop4HighHeels() {
        return getTop4ByCategoryId(100);
    }

    public List<ProductEntity> getTop4Sandals() {
        return getTop4ByCategoryId(101);
    }
}
