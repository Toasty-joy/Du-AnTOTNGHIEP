package org.example.duan.service;

import org.example.duan.entity.ColorEntity;
import org.example.duan.entity.ShoesImagesEntity;
import org.example.duan.repository.ShoesImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoesImagesService {

    @Autowired
    private ShoesImagesRepository shoesImageRepository;

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


    // Lấy 6 ảnh đầu tiên của sản phẩm (có thể sử dụng trong trường hợp cần)
    public List<ShoesImagesEntity> getTop6ImagesByProductId(Integer productId) {
        return shoesImageRepository.findTop6ByProductIdOrderByOrderImageAsc(productId);
    }
}
