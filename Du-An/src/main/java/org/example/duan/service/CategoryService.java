package org.example.duan.service;

import org.example.duan.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public String getCategoryNameById(int categoryId) {
        return categoryRepository.findCategoryNameById(categoryId);
    }
}
