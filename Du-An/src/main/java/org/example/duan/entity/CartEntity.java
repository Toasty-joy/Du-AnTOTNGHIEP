package org.example.duan.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "carts")
@IdClass(CartId.class) // Sử dụng CartId làm khóa phức hợp
public class CartEntity implements Serializable {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Id
    @Column(name = "color_id", nullable = false)
    private int colorId;

    @Id
    @Column(name = "size_id", nullable = false)
    private int sizeId;

    @Column(name = "quantity", nullable = false)
    private int quantity; // Số lượng sản phẩm trong giỏ hàng

    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false) // Liên kết tới AccountEntity
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false) // Liên kết tới ProductEntity
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "color_id", insertable = false, updatable = false) // Liên kết tới ColorEntity
    private ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "size_id", insertable = false, updatable = false) // Liên kết tới SizeEntity
    private SizeEntity size;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
}
