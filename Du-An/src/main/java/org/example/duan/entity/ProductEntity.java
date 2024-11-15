package org.example.duan.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Lob
    private String image;

    private double price;

    @Column(nullable = false)
    private java.sql.Date createDate;

    private int quantity;

    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoesImagesEntity> shoesImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SizesProductsEntity> sizesProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetailsEntity> orderDetails;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public java.sql.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.sql.Date createDate) {
        this.createDate = createDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<ShoesImagesEntity> getShoesImages() {
        return shoesImages;
    }

    public void setShoesImages(List<ShoesImagesEntity> shoesImages) {
        this.shoesImages = shoesImages;
    }

    public List<SizesProductsEntity> getSizesProducts() {
        return sizesProducts;
    }

    public void setSizesProducts(List<SizesProductsEntity> sizesProducts) {
        this.sizesProducts = sizesProducts;
    }

    public List<CartEntity> getCarts() {
        return carts;
    }

    public void setCarts(List<CartEntity> carts) {
        this.carts = carts;
    }

    public List<OrderDetailsEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
