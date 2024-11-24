package org.example.duan.controller;

import org.example.duan.entity.ColorEntity;
import org.example.duan.entity.ProductEntity;
import org.example.duan.entity.ShoesImagesEntity;
import org.example.duan.entity.SizeEntity;
import org.example.duan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class home {
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoesImagesService shoesImageService;
    @Autowired
    private ShoesImagesService shoesImagesService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SizeService sizeService;


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
        try {
            // Lấy thông tin sản phẩm theo ID
            ProductEntity product = productService.getProductById(id);
            if (product == null) {
                model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
                return "error"; // Trả về trang lỗi nếu không tìm thấy sản phẩm
            }

            // Lấy danh sách hình ảnh liên quan đến sản phẩm
            List<ShoesImagesEntity> images = shoesImageService.getTop6ImagesByProductId(id);

            // Lấy danh sách màu sắc (theo ID hoặc tên) liên quan đến sản phẩm
            List<ColorEntity> colors = shoesImageService.getColorEntitiesByProductId(id);

            // Lấy danh sách kích thước liên quan đến sản phẩm
            List<SizeEntity> sizes = sizeService.getSizesByProductId(id);

            // Đưa dữ liệu vào Model để truyền đến view
            model.addAttribute("product", product);
            model.addAttribute("images", images);
            model.addAttribute("colors", colors);
            model.addAttribute("sizes", sizes);

            return "chiTietSanPhan"; // Trả về tên view Thymeleaf
        } catch (Exception e) {
            // Xử lý ngoại lệ và thông báo lỗi
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải thông tin sản phẩm: " + e.getMessage());
            return "error"; // Trả về trang lỗi
        }
    }



    @GetMapping("/category/{id}")
    public String viewCategoryProducts(@PathVariable("id") int categoryId, Model model) {
        List<ProductEntity> products = productService.getTop4ByCategoryId(categoryId);

        // Lấy thông tin tên danh mục
        String categoryName = categoryService.getCategoryNameById(categoryId);

        // Thêm dữ liệu vào model để truyền sang view
        model.addAttribute("products", products);
        model.addAttribute("categoryName", categoryName);

        return "sanPham"; // Tên file HTML để hiển thị danh sách sản phẩm
    }

    @GetMapping("/chitiet")
    public String chitiet(Model model) {
        return "chiTietSanPhan";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<ProductEntity> products = productService.searchProducts(query);
        model.addAttribute("products", products);
        return "index2"; // Trả về view hiển thị kết quả tìm kiếm
    }

}