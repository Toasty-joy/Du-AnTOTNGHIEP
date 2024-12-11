package org.example.duan.controller;

import org.example.duan.service.OrderDetailsService;
import org.example.duan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/revenue")
public class AdminRevenueController {

    @Autowired
    private OrderService orderService;  // Sử dụng OrderService để tính doanh thu


    /**
     * Hiển thị trang thống kê doanh thu.
     */
    @GetMapping
    public String viewRevenuePage(Model model) {
        int currentYear = LocalDate.now().getYear();  // Lấy năm hiện tại
        model.addAttribute("currentYear", currentYear);
        return "admin/revenue";  // Trỏ tới template `admin/revenue.html`
    }

    /**
     * API trả về dữ liệu doanh thu theo tháng.
     */
    @GetMapping("/monthly")
    @ResponseBody
    public List<Map<String, Object>> getMonthlyRevenue(@RequestParam int year) {
        return orderService.getMonthlyRevenue(year);  // Gọi phương thức từ OrderService
    }

    /**
     * API trả về dữ liệu doanh thu theo năm.
     */
    @GetMapping("/yearly")
    @ResponseBody
    public List<Map<String, Object>> getYearlyRevenue() {
        return orderService.getYearlyRevenue();  // Gọi phương thức từ OrderService
    }

    /**
     * Trang hiển thị sản phẩm bán chạy.
     */
    @GetMapping("/top-selling-products")
    public String showTopSellingProducts(Model model) {
        List<Object[]> topSellingProducts = orderService.getTopSellingProducts();
        model.addAttribute("topSellingProducts", topSellingProducts); // Truyền vào model
        return "admin/top-selling-products";  // Trả về trang top-selling-products.html
    }
}
