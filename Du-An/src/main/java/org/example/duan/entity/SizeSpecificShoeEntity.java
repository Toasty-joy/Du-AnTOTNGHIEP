package org.example.duan.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "Sizes_Specific_Shoes")
public class SizeSpecificShoeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private SizeEntity size;

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

    public SizeEntity getSize() {
        return size;
    }

    public void setSize(SizeEntity size) {
        this.size = size;
    }

    public SpecificShoesEntity getSpecificShoe() {
        return specificShoe;
    }

    public void setSpecificShoe(SpecificShoesEntity specificShoe) {
        this.specificShoe = specificShoe;
    }
}
