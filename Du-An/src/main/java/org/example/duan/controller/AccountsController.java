package org.example.duan.controller;

import jakarta.servlet.http.HttpSession;
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

    // Hiển thị form đăng nhập/đăng ký
    @GetMapping("/login")
    public String showAuthPage(Model model) {
        model.addAttribute("notification", ""); // Xóa thông báo khi tải trang
        return "login"; // Tên view hiển thị giao diện đăng nhập/đăng ký
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam("emailOrUsername") String emailOrUsername,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {
        AccountEntity account = accountService.loginAndReturnAccount(emailOrUsername, password);

        if (account == null) {
            model.addAttribute("notification", "Tên đăng nhập hoặc mật khẩu không chính xác!");
            return "login";
        }

        // Lưu thông tin người dùng vào session
        session.setAttribute("loggedInUser", account);

        return "redirect:/"; // Chuyển về trang chủ
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           @RequestParam("fullname") String fullname,
                           Model model) {

        // Tạo đối tượng AccountEntity
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(username);
        accountEntity.setEmail(email);
        accountEntity.setPassword(password); // Lưu ý: Mã hóa mật khẩu nếu cần
        accountEntity.setFullname(fullname);
        accountEntity.setAdmin(false); // Mặc định là người dùng bình thường
        accountEntity.setActivated(true); // Mặc định tài khoản được kích hoạt

        // Xử lý đăng ký
        String message = accountService.register(accountEntity, password, confirmPassword);
        model.addAttribute("notification", message);

        // Nếu đăng ký thành công, chuyển hướng về trang đăng nhập
        if (message.equals("Đăng ký thành công!")) {
            return "redirect:/account/login";
        }

        // Nếu thất bại, quay lại trang đăng ký với thông báo lỗi
        return "login";
    }

    // Xử lý đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Xóa session, người dùng sẽ bị đăng xuất
        return "redirect:/";   // Quay về trang chủ sau khi đăng xuất
    }

    // Route để hiển thị hồ sơ người dùng
    @GetMapping("/profile")
    public String showProfilePage(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/account/login"; // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
        }

// Thêm thông tin người dùng vào model
        model.addAttribute("username", loggedInUser.getUsername()); // Hiển thị username
        model.addAttribute("fullname", loggedInUser.getFullname()); // Hiển thị fullname nếu cần

        model.addAttribute("email", loggedInUser.getEmail());
        return "HoSoNguoiDung"; // Tên của view hồ sơ người dùng
    }
}
