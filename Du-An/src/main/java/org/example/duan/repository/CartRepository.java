package org.example.duan.repository;

import org.example.duan.entity.CartEntity;
import org.example.duan.entity.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, CartId> {

    // Tìm tất cả các sản phẩm trong giỏ hàng theo username
    List<CartEntity> findByUsername(String username);

    // Tìm một sản phẩm cụ thể trong giỏ hàng theo composite key
    CartEntity findByUsernameAndProductIdAndColorIdAndSizeId(String username, int productId, int colorId, int sizeId);
}
