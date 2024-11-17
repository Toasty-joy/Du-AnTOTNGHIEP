package org.example.duan.controller;

import org.example.duan.entity.AccountEntity;
import org.example.duan.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountsController {

    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET request to show the login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Tên view của trang đăng nhập
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        String message = accountService.login(email, password);
        model.addAttribute("notification", message);

        // Redirect to home if login is successful, or back to login page
        return message.equals("Đăng nhập thành công!") ? "redirect:/" : "auth"; // "auth" is the login page view
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           @RequestParam("fullname") String fullname,
                           Model model) {

        // Create AccountEntity object and set values
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(username);
        accountEntity.setEmail(email);
        accountEntity.setPassword(password);  // Remember to hash the password here
        accountEntity.setFullname(fullname);

        // Set default values for admin and activated
        accountEntity.setAdmin(false);  // Default is false for normal users
        accountEntity.setActivated(true);  // Default is true (active)

        // Register the account
        String message = accountService.register(accountEntity, password, confirmPassword);
        model.addAttribute("notification", message);

        // Redirect to login page after registration
        return "login"; // After registration, redirect to login page
    }
}
