package org.example.duan.service;

import org.example.duan.entity.AccountEntity;
import org.example.duan.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String login(String email, String password) {
        // Logic đăng nhập (ví dụ: kiểm tra tài khoản và mật khẩu)
        return "Đăng nhập thành công!"; // Chỉ trả về thông báo giả lập
    }

    public String register(AccountEntity accountEntity, String password, String confirmPassword) {
        // Kiểm tra mật khẩu và xác nhận
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu không khớp!";
        }

        // Kiểm tra xem email có tồn tại trong hệ thống không
        if (accountRepository.existsByEmail(accountEntity.getEmail())) {
            return "Email đã tồn tại!";
        }

        // Lưu tài khoản mới vào cơ sở dữ liệu
        accountRepository.save(accountEntity);
        return "Đăng ký thành công!";
    }
}
