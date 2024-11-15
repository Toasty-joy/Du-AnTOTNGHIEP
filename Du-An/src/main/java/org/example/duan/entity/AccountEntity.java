package org.example.duan.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "accounts")
public class AccountEntity {
        @Id
        @Column(length = 50)
        private String username;

        @Column(length = 50, nullable = false)
        private String password;

        @Column(length = 50, nullable = false)
        private String fullname;

        @Column(length = 50, nullable = false)
        private String email;

        @Column(length = 50, nullable = false)
        private String photo;

        private boolean activated;
        private boolean admin;

        @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CartEntity> carts;

        @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OrderEntity> orders;

        // Getters and Setters
        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getFullname() {
                return fullname;
        }

        public void setFullname(String fullname) {
                this.fullname = fullname;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhoto() {
                return photo;
        }

        public void setPhoto(String photo) {
                this.photo = photo;
        }

        public boolean isActivated() {
                return activated;
        }

        public void setActivated(boolean activated) {
                this.activated = activated;
        }

        public boolean isAdmin() {
                return admin;
        }

        public void setAdmin(boolean admin) {
                this.admin = admin;
        }

        public List<CartEntity> getCarts() {
                return carts;
        }

        public void setCarts(List<CartEntity> carts) {
                this.carts = carts;
        }

        public List<OrderEntity> getOrders() {
                return orders;
        }

        public void setOrders(List<OrderEntity> orders) {
                this.orders = orders;
        }
}
