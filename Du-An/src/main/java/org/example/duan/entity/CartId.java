package org.example.duan.entity;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {
    private String username;
    private int productId;
    private int colorId;
    private int sizeId;

    // Constructor
    public CartId(String username, int productId, int colorId, int sizeId) {
        this.username = username;
        this.productId = productId;
        this.colorId = colorId;
        this.sizeId = sizeId;
    }

    // equals and hashCode methods
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
