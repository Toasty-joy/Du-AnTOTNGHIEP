package org.example.duan.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "shoes_images")
public class ShoesImagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String image;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1") // Mặc định là true trong DB
    private boolean orderImage = true;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isOrderImage() {
        return orderImage;
    }

    public void setOrderImage(boolean orderImage) {
        this.orderImage = orderImage;
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
}
