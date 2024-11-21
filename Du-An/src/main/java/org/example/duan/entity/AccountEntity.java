package org.example.duan.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "accounts")
public class AccountEntity {

        @Id
        @Column(length = 50)
        private String username;  // Tên đăng nhập

        @Column(length = 50, nullable = false)
        private String password;  // Mật khẩu

        @Column(length = 50, nullable = true)
        private String fullname;  // Họ và tên đầy đủ

        @Column(length = 50, nullable = false)
        private String email;  // Email

        @Column(length = 50, nullable = true)
        private String photo;  // Ảnh đại diện

        private boolean activated;  // Trạng thái kích hoạt tài khoản
        private boolean admin;  // Vai trò (Quản trị viên hay Người dùng)

        @Column(name = "birthdate", nullable = true)
        private LocalDate birthdate;  // Ngày tháng năm sinh

        @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<CartEntity> carts;  // Danh sách giỏ hàng

        @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<OrderEntity> orders;  // Danh sách đơn hàng

        // Getters và Setters
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

        public LocalDate getBirthdate() {
                return birthdate;
        }

        public void setBirthdate(LocalDate birthdate) {
                this.birthdate = birthdate;
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
