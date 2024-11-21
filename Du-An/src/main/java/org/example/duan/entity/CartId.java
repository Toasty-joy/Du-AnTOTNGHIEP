package org.example.duan.entity;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {
    private String username;
    private int productId;
    private int colorId;
    private int sizeId;

    // Constructor không tham số
    public CartId() {
    }

    // Constructor đầy đủ
    public CartId(String username, int productId, int colorId, int sizeId) {
        this.username = username;
        this.productId = productId;
        this.colorId = colorId;
        this.sizeId = sizeId;
    }

    // Getter và Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    // equals và hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return productId == cartId.productId &&
                colorId == cartId.colorId &&
                sizeId == cartId.sizeId &&
                Objects.equals(username, cartId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, productId, colorId, sizeId);
    }
}
