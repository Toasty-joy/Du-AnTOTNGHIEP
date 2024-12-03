package org.example.duan.service;

import org.example.duan.entity.CategoryEntity;
import org.example.duan.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public String getCategoryNameById(int categoryId) {
        return categoryRepository.findCategoryNameById(categoryId);
    }
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll(); // Lấy tất cả danh mục
    }
}
