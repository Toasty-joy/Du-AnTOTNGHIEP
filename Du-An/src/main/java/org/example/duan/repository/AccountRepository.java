package org.example.duan.repository;

import org.example.duan.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    // Tìm tài khoản bằng email hoặc username
    @Query("SELECT a FROM AccountEntity a WHERE a.email = :emailOrUsername OR a.username = :emailOrUsername")
    AccountEntity findByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);

    // Kiểm tra email đã tồn tại
    boolean existsByEmail(String email);
}

