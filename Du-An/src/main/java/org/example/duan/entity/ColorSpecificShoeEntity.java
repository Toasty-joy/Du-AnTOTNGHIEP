package org.example.duan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Colors_Specific_Shoes")
public class ColorSpecificShoeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "specific_shoes_id", nullable = false)
    private SpecificShoesEntity specificShoe;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
    }

    public SpecificShoesEntity getSpecificShoe() {
        return specificShoe;
    }

    public void setSpecificShoe(SpecificShoesEntity specificShoe) {
        this.specificShoe = specificShoe;
    }
}

