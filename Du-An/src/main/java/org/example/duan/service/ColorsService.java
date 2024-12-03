package org.example.duan.service;

import org.example.duan.entity.ColorEntity;
import org.example.duan.repository.ColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorsService {

    @Autowired
    private ColorsRepository colorsRepository;

    // Lấy tất cả màu sắc
    public List<ColorEntity> getAllColors() {
        return colorsRepository.findAll();  // Lấy tất cả các màu sắc từ cơ sở dữ liệu
    }

    // Phương thức tìm Color theo tên
    public Integer getColorIdByName(String colorName) {
        ColorEntity color = colorsRepository.findByName(colorName);
        if (color != null) {
            return color.getId();
        } else {
            throw new IllegalArgumentException("Color not found");
        }
    }

    // Thêm màu sắc mới
    public ColorEntity createColor(ColorEntity colorEntity) {
        return colorsRepository.save(colorEntity);  // Lưu màu sắc mới
    }

    // Cập nhật màu sắc
    public ColorEntity updateColor(ColorEntity colorEntity) {
        // Kiểm tra xem màu sắc có tồn tại không trước khi cập nhật
        Optional<ColorEntity> existingColor = colorsRepository.findById(colorEntity.getId());
        if (existingColor.isPresent()) {
            return colorsRepository.save(colorEntity);  // Cập nhật màu sắc đã có
        } else {
            throw new IllegalArgumentException("Color with ID " + colorEntity.getId() + " not found");
        }
    }

    // Xóa màu sắc
    public void deleteColor(int colorId) {
        Optional<ColorEntity> colorEntity = colorsRepository.findById(colorId);
        if (colorEntity.isPresent()) {
            colorsRepository.deleteById(colorId);  // Xóa màu sắc theo ID
        } else {
            throw new IllegalArgumentException("Color with ID " + colorId + " not found");
        }
    }

    // Phương thức lấy ColorEntity theo ID
    public ColorEntity getColorById(int id) {
        return colorsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Color with ID " + id + " not found"));
    }
}