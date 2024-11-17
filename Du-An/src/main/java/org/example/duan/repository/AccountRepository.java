package org.example.duan.repository;

import org.example.duan.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    boolean existsByEmail(String email);
}
