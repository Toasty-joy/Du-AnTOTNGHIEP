package org.example.duan.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Categories_Generic_Shoes")
public class CategoryGenericShoeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "generic_shoes_id", nullable = false)
    private GenericShoesEntity genericShoe;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public GenericShoesEntity getGenericShoe() {
        return genericShoe;
    }

    public void setGenericShoe(GenericShoesEntity genericShoe) {
        this.genericShoe = genericShoe;
    }
}
