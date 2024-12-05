package org.example.duan.controller;

import org.example.duan.entity.*;
import org.example.duan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ColorsService colorsService;

    @Autowired
    private ShoesImagesService shoesImagesService;

    @Autowired
    private SizeService sizeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;

    // Hiển thị trang quản trị admin
    @GetMapping
    public String showAdminPage(Model model) {
        // Tổng doanh thu trong ngày
        BigDecimal todayRevenue = orderService.getTodayRevenue();

        // Số đơn hàng trong ngày
        long todayOrdersCount = orderService.getTodayOrdersCount();

        // Tổng sản phẩm còn trong kho
        long totalProductsInStock = productService.getTotalProductsInStock();

        // Số khách hàng mua hàng hôm nay
        long todayCustomersCount = orderService.getTodayCustomersCount();

        // Lấy danh sách đơn hàng đang chờ xác nhận
        List<OrderEntity> pendingOrders = orderService.getPendingOrders();
        model.addAttribute("pendingOrders", pendingOrders);
        // Thêm dữ liệu vào model
        model.addAttribute("todayRevenue", todayRevenue);
        model.addAttribute("todayOrdersCount", todayOrdersCount);
        model.addAttribute("totalProductsInStock", totalProductsInStock);
        model.addAttribute("todayCustomersCount", todayCustomersCount);


        return "admin"; // Tên view cho trang quản trị
    }
    @PostMapping("/{id}/update-statusTangChu")
    public String updateOrderStatusTRangChu(@PathVariable Long id, @RequestParam("status") int status) {
        // Gọi service để cập nhật trạng thái và tổng tiền nếu trạng thái là "Đã hủy"
        orderService.updateOrderStatus(id, status);

        return "redirect:/admin";
    }
    // Hiển thị danh sách sản phẩm
    @GetMapping("/products")
    public String showProducts(Model model) {
        List<ProductEntity> products = productService.getAllProducts(); // Lấy tất cả sản phẩm
        model.addAttribute("products", products);
        return "admin/products"; // Tên view cho danh sách sản phẩm
    }
    // Hiển thị form thêm sản phẩm
    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        ProductEntity product = new ProductEntity();
        List<CategoryEntity> categories = categoryService.getAllCategories();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/add-product"; // Tên file HTML trong thư mục templates/admin
    }

    // Xử lý khi gửi form để tạo sản phẩm mới
    @PostMapping("/products/add")
    public String addProduct(
            @ModelAttribute ProductEntity product,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("productImage") MultipartFile productImage,
            RedirectAttributes redirectAttributes) {

        // Lấy danh mục theo ID và gán cho sản phẩm
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryId);
        product.setCategory(category);

        // Gán ngày tạo với ngày hiện tại
        product.setCreateDate(new java.sql.Date(System.currentTimeMillis()));

        // Xử lý lưu ảnh
        if (!productImage.isEmpty()) {
            String fileName = productService.storeFile(productImage);
            product.setImage(fileName);
        }

        // Lưu sản phẩm mới
        ProductEntity savedProduct = productService.createProduct(product);

        // Thêm thông báo thành công và ID sản phẩm vào RedirectAttributes
        redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");
        redirectAttributes.addFlashAttribute("productId", savedProduct.getId());
        return "redirect:/admin/products/add" ;
    }

    // Hiển thị chi tiết sản phẩm
    @GetMapping("/products/{id}")
    public String showProductDetails(@PathVariable int id, Model model) {
        // Lấy sản phẩm theo ID
        ProductEntity product = productService.getProductById(id);

        if (product == null) {
            model.addAttribute("error", "Sản phẩm không tồn tại");
            return "admin/error"; // View lỗi nếu sản phẩm không tồn tại
        }

        // Lấy danh sách tất cả màu sắc từ bảng Colors
        List<ColorEntity> allColors = colorsService.getAllColors();

        // Lấy danh sách các màu đã có trong sản phẩm từ bảng ShoesImagesEntity
        List<ColorEntity> productColors = product.getShoesImages().stream()
                .map(ShoesImagesEntity::getColor)
                .distinct()
                .collect(Collectors.toList());

        // Lọc ra những màu sắc không có trong sản phẩm
        List<ColorEntity> availableColors = allColors.stream()
                .filter(color -> !productColors.stream().anyMatch(productColor -> productColor.getId() == color.getId()))
                .collect(Collectors.toList());

        // Lấy danh sách tất cả kích thước
        List<SizeEntity> allSizes = sizeService.getAllSizes();

        // Lấy các kích thước đã có trong sản phẩm từ bảng Sizes_Products
        List<SizeEntity> productSizes = sizeService.getSizesByProductId(id);

        // Lọc ra các kích thước chưa có trong sản phẩm
        List<SizeEntity> availableSizes = allSizes.stream()
                .filter(size -> !productSizes.stream().anyMatch(productSize -> productSize.getId() == size.getId()))
                .collect(Collectors.toList());

        // Lấy danh sách ảnh của sản phẩm từ ShoesImagesEntity
        List<ShoesImagesEntity> productImages = shoesImagesService.getTop6ImagesByProductId(id);

        // Phân loại ảnh theo màu sắc
        Map<ColorEntity, List<ShoesImagesEntity>> colorImagesMap = new HashMap<>();
        for (ShoesImagesEntity image : productImages) {
            colorImagesMap.computeIfAbsent(image.getColor(), k -> new ArrayList<>()).add(image);
        }
        // Lấy danh sách tất cả màu sắc từ bảng Colors
        List<ColorEntity> allColors1 = colorsService.getAllColors();

        List<CategoryEntity> categories = categoryService.getAllCategories();

        // Thêm vào model
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("allColors", allColors);
        model.addAttribute("availableColors", availableColors);  // Màu sắc còn lại
        model.addAttribute("productColors", productColors);     // Màu sắc đã có trong sản phẩm
        model.addAttribute("productSizes", productSizes);       // Kích thước đã có trong sản phẩm
        model.addAttribute("availableSizes", availableSizes);   // Kích thước còn lại
        model.addAttribute("colorImagesMap", colorImagesMap);   // Danh sách ảnh phân theo màu sắc

        return "admin/product-details"; // Tên view hiển thị chi tiết sản phẩm
    }




    // Cập nhật thông tin sản phẩm
    @PostMapping("/products/{id}/update")
    public String updateProduct(
            @PathVariable int id,
            @ModelAttribute ProductEntity product,
            @RequestParam("productImage") MultipartFile productImage) {

        // Lấy sản phẩm từ database
        ProductEntity existingProduct = productService.getProductById(id);

        if (existingProduct != null) {
            // Cập nhật tên sản phẩm
            if (product.getName() != null && !product.getName().isEmpty()) {
                existingProduct.setName(product.getName());
            }

            if (product.getPrice() != 0) {
                existingProduct.setPrice(product.getPrice());
            }

            if (product.getQuantity() != 0) {
                existingProduct.setQuantity(product.getQuantity());
            }

            // Cập nhật danh mục sản phẩm (categoryId)
            if (product.getCategory() != null) {
                existingProduct.setCategory(product.getCategory());
            }

            // Nếu có ảnh mới, lưu và cập nhật
            if (!productImage.isEmpty()) {
                String fileName = productService.storeFile(productImage);
                existingProduct.setImage(fileName);
            }

            // Cập nhật sản phẩm
            productService.updateProduct(existingProduct);
        } else {
            throw new IllegalArgumentException("Sản phẩm không tồn tại với ID: " + id);
        }

        // Quay lại trang chi tiết sản phẩm
        return "redirect:/admin/products/" + id;
    }




    @PostMapping("/products/addImage")
    public ResponseEntity<String> addImageToProduct(
            @RequestParam("productId") int productId,
            @RequestParam("colorId") int colorId,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // Gọi service để lưu ảnh và cập nhật database
            shoesImagesService.addImage(productId, colorId, imageFile);
            return ResponseEntity.ok("Thêm ảnh thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thêm ảnh thất bại!");
        }
    }

    // Thêm kích thước vào sản phẩm
    @PostMapping("/products/{id}/add-size")
    public String addSizeToProduct(@PathVariable int id, @RequestParam int sizeId) {
        try {
            // Gọi service để thêm kích thước vào sản phẩm
            sizeService.addSizeToProduct(id, sizeId);

            // Sau khi thêm thành công, chuyển hướng về trang sản phẩm
            return "redirect:/admin/products/" + id;  // Điều hướng lại về trang sản phẩm
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi, quay lại trang sản phẩm và thông báo lỗi
            return "redirect:/admin/products/" + id + "?error=true";
        }
    }


    // Xóa kích thước khỏi sản phẩm
    @PostMapping("/products/{id}/remove-size")
    public String removeSizeFromProduct(@PathVariable int id, @RequestParam int sizeId) {
        try {
            // Gọi service để xóa kích thước khỏi sản phẩm
            sizeService.removeSizeFromProduct(id, sizeId);
            return "redirect:/admin/products/" + id; // Quay lại trang chi tiết sản phẩm
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/products/" + id + "?error=Không thể xóa kích thước"; // Thêm thông báo lỗi
        }
    }

    // Xóa sản phẩm
    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable int id) {
        // Kiểm tra sản phẩm có tồn tại và xử lý xóa đúng cách
        ProductEntity product = productService.getProductById(id);
        if (product != null) {
            // Xử lý xóa liên quan đến ảnh và các phụ thuộc
            shoesImagesService.deleteImage(id);  // Xóa ảnh của sản phẩm
            productService.deleteProduct(id);  // Xóa sản phẩm
        }
        return "redirect:/admin/products"; // Quay lại danh sách sản phẩm
    }
    // xóa ảnh
    @PostMapping("/products/{productId}/deleteImage")
    public String deleteImage(@PathVariable int productId, @RequestParam("imageId") int imageId) {
        try {
            shoesImagesService.deleteImage(imageId);  // Xóa ảnh bằng ID
            return "redirect:/admin/products/" + productId; // Quay lại trang sản phẩm sau khi xóa ảnh
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/products/" + productId + "?error=true"; // Nếu có lỗi, quay lại trang sản phẩm với thông báo lỗi
        }
    }
    // Quản lý màu sắc
    @GetMapping("/colors")
    public String showColors(Model model) {
        List<ColorEntity> colors = colorsService.getAllColors();
        model.addAttribute("colors", colors);
        return "admin/colors"; // Tên view cho danh sách màu sắc
    }

    // Thêm màu sắc mới
    @PostMapping("/colors/add")
    public String addColor(@ModelAttribute ColorEntity color) {
        colorsService.createColor(color);
        return "redirect:/admin/colors"; // Quay lại danh sách màu sắc
    }

    // Xóa màu sắc
    @PostMapping("/colors/{id}/delete")
    public String deleteColor(@PathVariable int id) {
        ColorEntity color = colorsService.getColorById(id);
        if (color != null && color.getShoesImages().isEmpty()) {
            colorsService.deleteColor(id);
        } else {
            return "redirect:/admin/colors?error=Không thể xóa màu sắc khi nó đang được sử dụng";
        }
        return "redirect:/admin/colors"; // Quay lại danh sách màu sắc
    }





}
