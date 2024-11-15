package org.example.duan.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(length = 50, nullable = false)
        private String name;

        private boolean isDelete;

        @OneToMany(mappedBy = "category")
        private List<ProductEntity> products;

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

        public boolean isDelete() {
                return isDelete;
        }

        public void setDelete(boolean delete) {
                isDelete = delete;
        }

        public List<ProductEntity> getProducts() {
                return products;
        }

        public void setProducts(List<ProductEntity> products) {
                this.products = products;
        }
}
