package org.example.duan.controller;

import org.example.duan.entity.ProductEntity;
import org.example.duan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class home {
    @Autowired
    private ProductService productService;

    @GetMapping("/category/{categoryId}/top4-products")
    public String getTop4ProductsByCategory(@PathVariable Integer categoryId, Model model) {
        List<ProductEntity> topProducts = productService.getTop4ProductsByCategoryId(categoryId);
        model.addAttribute("topProducts", topProducts);
        return "index";  // Hiển thị trên trang index hoặc trang mong muốn
    }
}
