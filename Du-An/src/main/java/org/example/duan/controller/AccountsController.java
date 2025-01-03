package org.example.duan.controller;

import org.example.duan.entity.AccountEntity;
import org.example.duan.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Controller
@RequestMapping("/account")
public class AccountsController {

    private final AccountService accountService;

    // Đường dẫn thư mục upload ảnh
    private static final String UPLOAD_DIR = "C:/Users/ASUS/IdeaProjects/Du-AnTOTNGHIEP/Du-An/src/main/resources/static/img/";

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("notification", ""); // Xóa thông báo cũ
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("emailOrUsername") String emailOrUsername,
                              @RequestParam("password") String password,
                              Model model,
                              HttpSession session) {
        String loginMessage = accountService.login(emailOrUsername, password);
        if (!"Đăng nhập thành công!".equals(loginMessage)) {
            model.addAttribute("notification", loginMessage);
            return "login";
        }

        // Lấy thông tin tài khoản và lưu vào session
        AccountEntity account = accountService.loginAndReturnAccount(emailOrUsername, password);
        if (account == null) {
            model.addAttribute("notification", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "login";
        }

        session.setAttribute("loggedInUser", account);

        // Kiểm tra nếu là admin, chuyển hướng đến trang admin
        if (account.isAdmin()) {
            return "redirect:/admin"; // Chuyển hướng đến trang admin
        }

        return "redirect:/"; // Chuyển hướng đến trang chủ cho người dùng thông thường
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidate the session to log the user out
        return "redirect:/";  // Redirect to the home page or login page
    }
    // Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("notification", "");
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String handleRegister(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam("fullname") String fullname,
                                 Model model) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(username);
        accountEntity.setEmail(email);
        accountEntity.setFullname(fullname);

        String registrationMessage = accountService.register(accountEntity, password, confirmPassword);
        model.addAttribute("notification", registrationMessage);

        if ("Đăng ký thành công!".equals(registrationMessage)) {
            return "redirect:/account/login";
        }

        return "register";
    }

    // Hiển thị trang hồ sơ
    @GetMapping("/profile")
    public String showProfilePage(Model model, HttpSession session) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/account/login";
        }

        model.addAttribute("account", loggedInUser);
        return "HoSoNguoiDung";
    }

    // Cập nhật hồ sơ
    @PostMapping("/profile")
    public String updateProfile(@RequestParam("fullname") String fullname,
                                @RequestParam("email") String email,
                                @RequestParam("phone") String phone,
                                @RequestParam("address") String address,
                                @RequestParam(value = "photo", required = false) MultipartFile photo,
                                @RequestParam(value = "gender", required = false) String gender,
                                @RequestParam(value = "birthdate", required = false) String birthdate,
                                HttpSession session,
                                Model model) throws IOException {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/account/login";
        }

        try {
            // Cập nhật thông tin tài khoản
            loggedInUser.setFullname(fullname);
            loggedInUser.setEmail(email);
            loggedInUser.setPhone(phone);
            loggedInUser.setAddress(address);

            // Xử lý tải lên ảnh đại diện
            if (photo != null && !photo.isEmpty()) {
                // Tạo tên file duy nhất bằng timestamp
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);

                // Lưu file ảnh vào thư mục
                Files.copy(photo.getInputStream(), path);

                // Cập nhật đường dẫn ảnh vào database
                loggedInUser.setPhoto(fileName); // Lưu đường dẫn tương đối
            }


            // Cập nhật giới tính nếu có
            if (gender != null) {
                loggedInUser.setGender("Female".equalsIgnoreCase(gender));
            }

            // Cập nhật ngày sinh nếu có
            if (birthdate != null && !birthdate.isEmpty()) {
                loggedInUser.setBirthdate(LocalDate.parse(birthdate));
            }

            // Cập nhật thông tin tài khoản vào cơ sở dữ liệu
            accountService.updateAccount(loggedInUser);

            // Thông báo thành công
            model.addAttribute("successMessage", "Cập nhật hồ sơ thành công!");
        } catch (Exception e) {
            // Xử lý lỗi và thông báo thất bại
            model.addAttribute("errorMessage", "Cập nhật hồ sơ thất bại. Vui lòng thử lại.");
        }

        // Trả lại model với thông tin tài khoản đã cập nhật
        model.addAttribute("successMessage", "Cập nhật hồ sơ thành công!");
        model.addAttribute("account", loggedInUser);
        return "HoSoNguoiDung";
    }


    // Xử lý thay đổi mật khẩu
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 HttpSession session, Model model) {
        AccountEntity loggedInUser = (AccountEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/account/login"; // Nếu không có tài khoản đang đăng nhập, chuyển đến trang đăng nhập
        }

        // Gọi service để thay đổi mật khẩu
        String message = accountService.changePassword(loggedInUser, oldPassword, newPassword, confirmNewPassword);

        // Thêm thông báo vào model
        model.addAttribute("passwordChangeMessage", message);

        // Trả về trang hồ sơ với thông báo kết quả
        return "password"; // Đây là trang "password.html"
    }
    @GetMapping("/password")
    public String showChangePasswordForm() {
        return "password"; // The view with the form for changing password
    }


}
