package org.example.duan.controller;

import org.example.duan.entity.ProductEntity;
import org.example.duan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Display all products
    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductEntity> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    // Display form to add a new product
    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new ProductEntity());
        return "product-form";
    }

    // Add a new product
    @PostMapping
    public String saveProduct(@ModelAttribute("product") ProductEntity product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Display form to update a product
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable int id, Model model) {
        ProductEntity product = productService.getProductById(id).orElse(null);
        model.addAttribute("product", product);
        return "product-form";
    }

    // Update a product
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") ProductEntity product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    // Retrieve top 4 products in the "Giày Cao Gót Nữ" category
    @GetMapping("/top-high-heels")
    public String getTop4HighHeels(Model model) {
        List<ProductEntity> topHighHeels = productService.getTop4HighHeels();
        model.addAttribute("products", topHighHeels);
        return "product-list";
    }

    // Retrieve top 4 products in the "Dép và Sandal Nữ" category
    @GetMapping("/top-sandals")
    public String getTop4Sandals(Model model) {
        List<ProductEntity> topSandals = productService.getTop4Sandals();
        model.addAttribute("products", topSandals);
        return "product-list";
    }
}
