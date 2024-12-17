package org.example.duan.repository;

import org.example.duan.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    // Tìm tài khoản bằng email hoặc username
    @Query("SELECT a FROM AccountEntity a WHERE a.email = :emailOrUsername OR a.username = :emailOrUsername")
    AccountEntity findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);

    // Kiểm tra email đã tồn tại
    boolean existsByEmail(String email);

    AccountEntity findByEmail(String email);


    // Tìm tất cả tài khoản đã kích hoạt
    List<AccountEntity> findAllByActivatedTrue();

    // Tìm tất cả tài khoản chưa kích hoạt
    List<AccountEntity> findAllByActivatedFalse();

    // Tìm kiếm tài khoản theo từ khóa (email, username hoặc fullname)
    @Query("SELECT a FROM AccountEntity a " +
            "WHERE LOWER(a.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.fullname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AccountEntity> searchAccounts(@Param("keyword") String keyword);
}

