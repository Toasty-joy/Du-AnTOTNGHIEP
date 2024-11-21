package org.example.duan.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class productDTO {
    @NotEmpty(message = "the name is required")
    private String name ;

    public @NotEmpty(message = "the name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "the name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "The category is required") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "The category is required") String category) {
        this.category = category;
    }

    @Min(0)
    public int getPrice() {
        return price;
    }

    public void setPrice(@Min(0) int price) {
        this.price = price;
    }

    @NotEmpty(message = "The category is required")
    private String category ;

    @Min(0)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(0) int quantity) {
        this.quantity = quantity;
    }
    @Min(0)
    private int quantity ;
    @Min(0)
    private int price ;
    @Setter
    @Getter
    private MultipartFile imageFile;

}
