package org.example.duan.controller;

import org.example.duan.entity.ColorEntity;
import org.example.duan.entity.ProductEntity;
import org.example.duan.entity.ShoesImagesEntity;
import org.example.duan.service.ColorsService;
import org.example.duan.service.ProductService;
import org.example.duan.service.ShoesImagesService;
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
    @Autowired
    private ShoesImagesService shoesImageService;
    @Autowired
    private ShoesImagesService shoesImagesService;


    // Hiển thị 4 sản phẩm Giày Cao Gót Nữ và Dép & Sandal Nữ
    @GetMapping("/")
    public String showTopProductsByCategory(Model model) {
        // Lấy 4 sản phẩm đầu tiên của Giày Cao Gót Nữ (categoryId = 100)
        List<ProductEntity> highHeelsProducts = productService.getTop4HighHeels();

        // Lấy 4 sản phẩm đầu tiên của Dép & Sandal Nữ (categoryId = 101)
        List<ProductEntity> sandalsProducts = productService.getTop4Sandals();

        // Kiểm tra xem dữ liệu có null hay không
        if (highHeelsProducts == null || sandalsProducts == null) {
            System.out.println("Không có sản phẩm nào trong danh mục.");
        } else {
            System.out.println("Sản phẩm đã được truyền vào model.");
        }

        // Truyền dữ liệu vào model để hiển thị
        model.addAttribute("highHeels", highHeelsProducts);
        model.addAttribute("sandals", sandalsProducts);

        return "index";  // Trả về trang index
    }
    // Nếu cần một phương thức để hiển thị chi tiết sản phẩm, thêm vào đây
    @GetMapping("/product/{id}")
    public String viewProductDetails(@PathVariable int id, Model model) {
        ProductEntity product = productService.getProductById(id);
        List<ShoesImagesEntity> images = shoesImageService.getTop6ImagesByProductId(id);
        List<String> colors = shoesImagesService.getColorsByProductId(id);
        System.out.println("Màu sắc của sản phẩm: " + colors);
        model.addAttribute("product", product);
        model.addAttribute("images", images);
        model.addAttribute("colors", colors);
        return "chiTietSanPhan";  // Trang chi tiết sản phẩm
    }


}