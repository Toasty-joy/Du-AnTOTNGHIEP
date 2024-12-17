package org.example.duan.service;

import org.example.duan.entity.*;
import org.example.duan.repository.ProductRepository;
import org.example.duan.repository.ShoesImagesRepository;
import org.example.duan.repository.SizeRepository;
import org.example.duan.repository.SizesProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoesImagesRepository shoesImagesRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private SizesProductsRepository sizesProductsRepository;

    // Lấy top 4 sản phẩm theo category ID
    public List<ProductEntity> getTop4ByCategoryId(int categoryId) {
        return productRepository.findTop4ByCategoryIdOrderByIdAsc(categoryId);
    }
    public List<ProductEntity> getProductsByCategoryId(int categoryId) {
        return productRepository.findByCategoryIdOrderByIdAsc(categoryId);
    }
    // Các phương thức cụ thể cho Giày Cao Gót và Sandal
    public List<ProductEntity> getTop4HighHeels() {
        return getTop4ByCategoryId(100);  // Giày Cao Gót Nữ (categoryId = 100)
    }

    public List<ProductEntity> getTop4Sandals() {
        return getTop4ByCategoryId(101);  // Dép & Sandal Nữ (categoryId = 101)
    }

    public List<ProductEntity> getTop4MenShoes() {
        return getTop4ByCategoryId(102);  // Giày Nam (categoryId = 102)
    }

    public List<ProductEntity> getTop4MenSandals() {
        return getTop4ByCategoryId(103);  // Dép Nam (categoryId = 103)
    }

    public List<ProductEntity> getTop4SportsShoes() {
        return getTop4ByCategoryId(104);  // Giày Thể Thao (categoryId = 104)
    }

    public List<ProductEntity> getProductsByCategoryIdSortedByPriceAsc(int categoryId) {
        return productRepository.findByCategoryIdOrderByPriceAsc(categoryId);
    }

    public List<ProductEntity> getProductsByCategoryIdSortedByPriceDesc(int categoryId) {
        return productRepository.findByCategoryIdOrderByPriceDesc(categoryId);
    }

    public List<ProductEntity> getProductsByCategoryIdSortedByNameAsc(int categoryId) {
        return productRepository.findByCategoryIdOrderByNameAsc(categoryId);
    }

    public List<ProductEntity> getProductsByCategoryIdSortedByNameDesc(int categoryId) {
        return productRepository.findByCategoryIdOrderByNameDesc(categoryId);
    }


    // Lấy sản phẩm theo ID
    public ProductEntity getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // Tạo mới một sản phẩm
    @Transactional
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product); // Lưu sản phẩm mới vào cơ sở dữ liệu
    }

    // Cập nhật thông tin sản phẩm
    public void updateProduct(ProductEntity product) {
        productRepository.save(product); // Lưu lại sản phẩm đã cập nhật
    }

    // Xóa sản phẩm
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);  // Xóa sản phẩm theo ID
    }

    // Tìm kiếm sản phẩm theo tên
    public List<ProductEntity> searchProducts(String searchTerm) {
        return productRepository.findByNameContainingIgnoreCase(searchTerm);
    }

    // Lấy tất cả sản phẩm
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();  // Trả về tất cả các sản phẩm
    }

    // Tìm kiếm sản phẩm theo màu sắc, kích thước, hoặc các thuộc tính khác

    public List<ProductEntity> getProductsBySizeId(int sizeId) {
        return productRepository.findBySizeId(sizeId);
    }

    // Lấy sản phẩm theo category ID
    public List<ProductEntity> getTop4ProductsByCategoryId(Integer categoryId) {
        return productRepository.findTop4ByCategoryIdOrderByIdAsc(categoryId);
    }

    // Tìm sản phẩm theo tên
    public List<ProductEntity> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Tìm sản phẩm theo size
    public List<ProductEntity> getProductsBySize(int sizeId) {
        return productRepository.findBySizeId(sizeId);
    }

    // Tìm sản phẩm theo màu
    public List<ProductEntity> getProductsByColor(ColorEntity color) {
        return productRepository.findByShoesImages_Color(color);
    }

    // Lấy tất cả ảnh của sản phẩm
    public List<ShoesImagesEntity> getImagesByProductId(Integer productId) {
        return shoesImagesRepository.findByProductId(productId);
    }

    // Lấy các kích thước của sản phẩm
    public List<SizeEntity> getSizesByProductId(Integer productId) {
        return sizeRepository.findByProductId(productId);
    }

    // Lưu kích thước của sản phẩm
    public SizesProductsEntity addSizeToProduct(SizesProductsEntity sizesProductsEntity) {
        return sizesProductsRepository.save(sizesProductsEntity);
    }

    // Tìm tất cả các kích thước
    public List<SizeEntity> getAllSizes() {
        return sizeRepository.findAll();
    }
    private static final String IMAGE_UPLOAD_PATH = "C:/Users/ASUS/IdeaProjects/Du-AnTOTNGHIEP/Du-An/src/main/resources/static/img/";

    public String storeFile(MultipartFile file) {
        try {
            // Tạo thư mục nếu chưa tồn tại
            Path uploadPath = Paths.get(IMAGE_UPLOAD_PATH);


            String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Lưu file
            Files.copy(file.getInputStream(), filePath);

            // Chỉ trả về tên file
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu file: " + file.getOriginalFilename(), e);
        }
    }
    // Lấy tổng số sản phẩm còn trong kho
    public long getTotalProductsInStock() {
        return productRepository.sumQuantityInStock();
    }
}
