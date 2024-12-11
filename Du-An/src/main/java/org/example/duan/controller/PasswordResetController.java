package org.example.duan.controller;

import jakarta.mail.MessagingException;
import org.example.duan.entity.AccountEntity;
import org.example.duan.repository.AccountRepository;
import org.example.duan.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class PasswordResetController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountRepository accountRepository;

    // Hiển thị trang nhập email
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password"; // Hiển thị form forgot-password.html
    }

    // Xử lý yêu cầu gửi OTP
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        if (!accountRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email không tồn tại!");
            return "forgot-password";
        }

        try {
            String otp = emailService.generateAndStoreOTP(email);
            emailService.sendEmail(email, "Mã xác nhận đặt lại mật khẩu",
                    "Mã OTP của bạn là: <b>" + otp + "</b>. Mã này có hiệu lực trong 5 phút.");

            model.addAttribute("email", email);
            return "verify-otp";
        } catch (MessagingException e) {
            model.addAttribute("error", "Không thể gửi email: " + e.getMessage());
            return "forgot-password";
        }
    }

    // Xác nhận mã OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        if (!emailService.validateOTP(email, otp)) {
            model.addAttribute("error", "Mã OTP không chính xác hoặc đã hết hạn!");
            model.addAttribute("email", email);
            return "verify-otp";
        }

        model.addAttribute("email", email);
        return "reset-password";
    }

    // Đặt lại mật khẩu
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu không khớp!");
            model.addAttribute("email", email);
            return "reset-password";
        }

        AccountEntity account = accountRepository.findByEmail(email);
        account.setPassword(password); // Nên mã hóa mật khẩu tại đây
        accountRepository.save(account);

        return "redirect:/account/login";
    }
}
