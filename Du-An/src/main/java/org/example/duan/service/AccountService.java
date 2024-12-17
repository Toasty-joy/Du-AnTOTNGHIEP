package org.example.duan.service;

import org.example.duan.entity.AccountEntity;
import org.example.duan.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Đăng nhập và trả về tài khoản
    public AccountEntity loginAndReturnAccount(String emailOrUsername, String password) {
        AccountEntity account = accountRepository.findByEmailOrUsername(emailOrUsername);
        if (account == null || !account.getPassword().equals(password) || !account.isActivated()) {
            return null;
        }
        return account;
    }

    // Xử lý logic đăng nhập
    public String login(String emailOrUsername, String password) {
        AccountEntity account = accountRepository.findByEmailOrUsername(emailOrUsername);
        if (account == null) {
            return "Tên đăng nhập hoặc mật khẩu không đúng!";
        }
        if (!account.getPassword().equals(password)) {
            return "Tên đăng nhập hoặc mật khẩu không đúng!";
        }
        if (!account.isActivated()) {
            return "Tài khoản của bạn chưa được kích hoạt!";
        }
        return "Đăng nhập thành công!";
    }

    // Xử lý logic đăng ký
    public String register(AccountEntity accountEntity, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu và xác nhận mật khẩu không khớp!";
        }
        if (accountRepository.existsByEmail(accountEntity.getEmail())) {
            return "Email đã được sử dụng!";
        }

        accountEntity.setPassword(password); // Thêm mã hóa nếu cần
        accountEntity.setAdmin(false);
        accountEntity.setActivated(true);

        accountRepository.save(accountEntity);
        return "Đăng ký thành công!";
    }

    // Cập nhật tài khoản
    public void updateAccount(AccountEntity account) {
        accountRepository.save(account);
    }

    // Phương thức thay đổi mật khẩu
    public String changePassword(AccountEntity account, String oldPassword, String newPassword, String confirmNewPassword) {
        // Kiểm tra mật khẩu cũ
        if (!account.getPassword().equals(oldPassword)) {
            return "Mật khẩu cũ không chính xác!";
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu mới
        if (!newPassword.equals(confirmNewPassword)) {
            return "Mật khẩu mới và xác nhận mật khẩu không khớp!";
        }

        // Cập nhật mật khẩu mới
        account.setPassword(newPassword);
        accountRepository.save(account);
        return "Mật khẩu đã được thay đổi thành công!";
    }

    // Lấy danh sách tất cả tài khoản
    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Tìm kiếm tài khoản theo từ khóa
    public List<AccountEntity> searchAccounts(String keyword) {
        return accountRepository.searchAccounts(keyword);
    }

    // Kích hoạt hoặc vô hiệu hóa tài khoản
    public void toggleAccountStatus(String username) {
        AccountEntity account = accountRepository.findById(username).orElse(null);
        if (account != null) {
            account.setActivated(!account.isActivated());
            accountRepository.save(account);
        }
    }

    // Xóa tài khoản
    public void deleteAccount(String username) {
        accountRepository.deleteById(username);
    }
    // Phương thức thay đổi quyền admin
    public void changeUserRole(String username) {
        AccountEntity account = accountRepository.findById(username).orElseThrow(() -> new RuntimeException("User not found"));
        account.setAdmin(!account.isAdmin());  // Đảo ngược giá trị của quyền admin
        accountRepository.save(account);  // Lưu lại thay đổi
    }

}