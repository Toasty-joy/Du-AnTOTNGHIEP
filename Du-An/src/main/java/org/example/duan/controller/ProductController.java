package org.example.duan.controller;

import jakarta.validation.Valid;
import org.example.duan.entity.ProductEntity;
import org.example.duan.entity.productDTO;
import org.example.duan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productrepo;
    @GetMapping("/ListSanPham")
    public String showProductList(Model model){
        List<ProductEntity> products = productrepo.findAll();
        model.addAttribute("products", products);
        return "ListSanPham";
    }
    @GetMapping("/create")
    public String CreateForm(Model model){
        List<ProductEntity> products = productrepo.findAll();
        model.addAttribute("products", products);
        productDTO productdto = new productDTO();
        model.addAttribute("productDTO",  productdto);
        return "CreateForm";
    }
    @PostMapping("/create")
    public String CreateProduct(@Valid @ModelAttribute productDTO productdto, BindingResult result, Model model) {
        if (productdto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productdto", "imageFile", "Image file is empty"));
        }

        if (result.hasErrors()) {
            return "CreateForm"; // Return to the form if there are validation errors
        }

        MultipartFile image = productdto.getImageFile();
        Date createDate = new Date();
        String storageFileName = createDate.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "src/main/resources/static/img/"; // Relative path
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Create directory if it doesn't exist
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, uploadPath.resolve(storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to upload image: " + ex.getMessage());
        }

        // Save the product entity
        ProductEntity product = new ProductEntity();
        product.setName(productdto.getName());
        product.setPrice(productdto.getPrice());
        product.setQuantity(productdto.getQuantity());
//        product.setCreateDate(new java.sql.Date(createDate.getTime()));
        product.setImage(storageFileName);
        productrepo.save(product);

        return "redirect:/products/ListSanPham"; // Redirect after saving
    }

}
