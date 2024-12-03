package org.example.duan.service;

import org.example.duan.entity.ColorEntity;
import org.example.duan.entity.ProductEntity;
import org.example.duan.entity.ShoesImagesEntity;
import org.example.duan.repository.ColorsRepository;
import org.example.duan.repository.ProductRepository;
import org.example.duan.repository.ShoesImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShoesImagesService {

    @Autowired
    private ShoesImagesRepository shoesImageRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ColorsRepository colorsRepository;
    @Autowired
    private ShoesImagesRepository shoesImagesRepository;

    // Lấy danh sách các màu sắc của sản phẩm
    public List<ColorEntity> getColorEntitiesByProductId(Integer productId) {
        // Lấy danh sách hình ảnh từ sản phẩm
        List<ShoesImagesEntity> images = shoesImageRepository.findByProductId(productId);
        List<ColorEntity> colors = new ArrayList<>();

        for (ShoesImagesEntity image : images) {
            // Kiểm tra nếu `color` không null
            if (image.getColor() != null) {
                // Kiểm tra xem danh sách `colors` đã chứa màu với ID tương ứng chưa
                boolean alreadyExists = colors.stream()
                        .anyMatch(c -> c.getId() == image.getColor().getId());

                if (!alreadyExists) {
                    colors.add(image.getColor());
                }
            }
        }

        return colors;
    }

    // Lấy 6 ảnh đầu tiên của sản phẩm
    public List<ShoesImagesEntity> getTop6ImagesByProductId(Integer productId) {
        return shoesImageRepository.findTop6ByProductIdOrderByOrderImageAsc(productId);
    }

    // Thêm ảnh cho sản phẩm
    public ShoesImagesEntity createImage(ShoesImagesEntity imageEntity) {
        return shoesImageRepository.save(imageEntity);  // Lưu ảnh mới
    }

    // Cập nhật ảnh cho sản phẩm
    public ShoesImagesEntity updateImage(ShoesImagesEntity imageEntity) {
        return shoesImageRepository.save(imageEntity);  // Cập nhật ảnh
    }

    // Xóa ảnh của sản phẩm
    public void deleteImage(int imageId) {
        shoesImageRepository.deleteById(imageId);  // Xóa ảnh theo ID
    }

    // Lấy tất cả ảnh sản phẩm
    public List<ShoesImagesEntity> getAllImages() {
        return shoesImageRepository.findAll();
    }

    private static final String IMAGE_UPLOAD_PATH = "C:/Users/ASUS/IdeaProjects/Du-AnTOTNGHIEP/Du-An/src/main/resources/static/img/";

    public void addImage(int productId, int colorId, MultipartFile imageFile) throws IOException {
        // Kiểm tra sản phẩm
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));

        // Kiểm tra màu sắc
        ColorEntity color = colorsRepository.findById(colorId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy màu với ID: " + colorId));

        // Tạo tên file duy nhất và lưu vào thư mục
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(IMAGE_UPLOAD_PATH + fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Lưu thông tin ảnh vào cơ sở dữ liệu
        ShoesImagesEntity imageEntity = new ShoesImagesEntity();
        imageEntity.setProduct(product); // Liên kết với sản phẩm
        imageEntity.setColor(color);    // Liên kết với màu sắc
        imageEntity.setImage(fileName); // Lưu tên file ảnh vào database
        shoesImagesRepository.save(imageEntity);
    }
    public void deleteImage2(int imageId) throws IOException {
        // Tìm ảnh theo ID
        ShoesImagesEntity imageEntity = shoesImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy ảnh với ID: " + imageId));

        // Lấy tên file ảnh từ entity
        String fileName = imageEntity.getImage();

        // Tạo đường dẫn đến ảnh
        Path imagePath = Paths.get(IMAGE_UPLOAD_PATH + fileName);

        // Xóa file ảnh trên hệ thống
        Files.deleteIfExists(imagePath);

        // Xóa ảnh khỏi cơ sở dữ liệu
        shoesImageRepository.deleteById(imageId);
    }
}
