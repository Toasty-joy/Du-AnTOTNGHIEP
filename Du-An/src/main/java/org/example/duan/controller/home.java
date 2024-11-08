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

    // Hiển thị 4 sản phẩm Giày Cao Gót Nữ và Dép & Sandal Nữ
    @GetMapping("/")
    public String showTopProductsByCategory(Model model) {
        // Lấy 4 sản phẩm đầu tiên của Giày Cao Gót Nữ (categoryId = 100)
        List<ProductEntity> highHeelsProducts = productService.getTop4HighHeels();

        // Lấy 4 sản phẩm đầu tiên của Dép & Sandal Nữ (categoryId = 101)
        List<ProductEntity> sandalsProducts = productService.getTop4Sandals();

        // Truyền dữ liệu vào model để hiển thị
        model.addAttribute("highHeels", highHeelsProducts);  // Đổi tên từ highHeelsProducts thành highHeels
        model.addAttribute("sandals", sandalsProducts);  // Đổi tên từ sandalsProducts thành sandals

        return "index";  // Hiển thị trên trang index hoặc trang mong muốn
    }

}
