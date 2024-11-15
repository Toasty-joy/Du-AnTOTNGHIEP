package org.example.duan.service;

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
    public List<String> getColorsByProductId(Integer productId) {
        // Lấy tất cả các ảnh của sản phẩm
        List<ShoesImagesEntity> images = shoesImageRepository.findByProductId(productId);
        List<String> colors = new ArrayList<>();

        // Duyệt qua danh sách ảnh và lấy tên màu sắc
        for (ShoesImagesEntity image : images) {
            // Kiểm tra nếu có màu và màu đó chưa có trong danh sách
            if (image.getColor() != null && image.getColor().getName() != null && !colors.contains(image.getColor().getName())) {
                colors.add(image.getColor().getName());
            }
        }

        return colors;
    }

    // Lấy 6 ảnh đầu tiên của sản phẩm (có thể sử dụng trong trường hợp cần)
    public List<ShoesImagesEntity> getTop6ImagesByProductId(Integer productId) {
        return shoesImageRepository.findTop6ByProductIdOrderByOrderImageAsc(productId);
    }
}
