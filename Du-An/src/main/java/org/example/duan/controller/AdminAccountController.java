package org.example.duan.controller;


import org.example.duan.entity.AccountEntity;
import org.example.duan.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/accounts")
public class AdminAccountController {
    @Autowired
    private AccountService accountService;

    // Hiển thị danh sách tài khoản
    @GetMapping
    public String listAccounts(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<AccountEntity> accounts = (keyword != null) ? accountService.searchAccounts(keyword) : accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("keyword", keyword);
        return "admin/accounts"; // Thymeleaf view: admin/accounts.html
    }

    // Kích hoạt / vô hiệu hóa tài khoản
    @PostMapping("/toggle/{username}")
    public String toggleAccountStatus(@PathVariable String username) {
        accountService.toggleAccountStatus(username);
        return "redirect:/admin/accounts";
    }

    // Xóa tài khoản
    @PostMapping("/delete/{username}")
    public String deleteAccount(@PathVariable String username) {
        accountService.deleteAccount(username);
        return "redirect:/admin/accounts";
    }
    // Chức năng thay đổi quyền admin
    @PostMapping("/role/{username}")
    public String changeUserRole(@PathVariable("username") String username) {
        accountService.changeUserRole(username);  // Gọi service để thay đổi quyền
        return "redirect:/admin/accounts";  // Quay lại trang quản lý người dùng
    }
}
