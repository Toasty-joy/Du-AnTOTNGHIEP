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
    public AccountEntity loginAndReturnAccount(String emailOrUsername, String password) {
        AccountEntity account = accountRepository.findByEmailOrUsername(emailOrUsername);

        // Kiểm tra tài khoản có tồn tại
        if (account == null || !account.getPassword().equals(password)) {
            return null; // Trả về null nếu thông tin không hợp lệ
        }

        // Kiểm tra trạng thái kích hoạt
        if (!account.isActivated()) {
            return null; // Trả về null nếu tài khoản chưa được kích hoạt
        }

        return account; // Trả về đối tượng tài khoản nếu hợp lệ
    }
    public String login(String emailOrUsername, String password) {
        // Tìm tài khoản bằng email hoặc username
        AccountEntity account = accountRepository.findByEmailOrUsername(emailOrUsername);

        // Kiểm tra tài khoản có tồn tại
        if (account == null) {
            return "Tên đăng nhập hoặc mật khẩu không đúng!";
        }

        // Kiểm tra mật khẩu (nên sử dụng mã hóa để đảm bảo an toàn)
        if (!account.getPassword().equals(password)) {
            return "Tên đăng nhập hoặc mật khẩu không đúng!";
        }

        // Kiểm tra tài khoản đã được kích hoạt
        if (!account.isActivated()) {
            return "Tài khoản của bạn chưa được kích hoạt!";
        }

        return "Đăng nhập thành công!";
    }

    public String register(AccountEntity accountEntity, String password, String confirmPassword) {
        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu và xác nhận mật khẩu không khớp!";
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (accountRepository.existsByEmail(accountEntity.getEmail())) {
            return "Email đã được sử dụng!";
        }

        // Mã hóa mật khẩu để đảm bảo an toàn
        accountEntity.setPassword(password); // Thay bằng mã hóa nếu cần (ví dụ: BCrypt)

        // Thiết lập trạng thái mặc định
        accountEntity.setAdmin(false); // Mặc định là người dùng bình thường
        accountEntity.setActivated(true); // Mặc định tài khoản được kích hoạt

        // Lưu tài khoản mới vào cơ sở dữ liệu
        accountRepository.save(accountEntity);
        return "Đăng ký thành công!";
    }


}
