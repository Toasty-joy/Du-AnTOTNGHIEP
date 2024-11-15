package org.example.duan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class CartEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private AccountEntity account;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private SizeEntity size;

    private int quantity;

    // Getters and Setters
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

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
    }

    public SizeEntity getSize() {
        return size;
    }

    public void setSize(SizeEntity size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
