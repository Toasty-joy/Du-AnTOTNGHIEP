package org.example.duan.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "colors")
public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "color")
    private List<ShoesImagesEntity> shoesImages;

    @OneToMany(mappedBy = "color")
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "color")
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

    public List<ShoesImagesEntity> getShoesImages() {
        return shoesImages;
    }

    public void setShoesImages(List<ShoesImagesEntity> shoesImages) {
        this.shoesImages = shoesImages;
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
