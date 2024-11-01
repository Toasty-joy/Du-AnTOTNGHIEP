package org.example.duan.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Shoes_Images")
public class ShoeImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public SpecificShoesEntity getSpecificShoe() {
        return specificShoe;
    }

    public void setSpecificShoe(SpecificShoesEntity specificShoe) {
        this.specificShoe = specificShoe;
    }
}

