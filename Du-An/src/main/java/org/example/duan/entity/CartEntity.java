package org.example.duan.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Carts")
public class CartEntity {
    @EmbeddedId
    private CartId id;
    private Integer quantity;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    private AccountEntity account;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    // Getters and Setters
    public CartId getId() {
        return id;
    }

    public void setId(CartId id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}

@Embeddable
class CartId implements Serializable {
    private String username;
    private int productId;

    // Getters, Setters, hashCode, equals
}

