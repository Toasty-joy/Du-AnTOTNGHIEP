package org.example.duan.service;

import org.example.duan.entity.ColorEntity;
import org.example.duan.repository.ColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
