package org.example.duan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Accounts")
public class AccountEntity {


        @Id
        @Column(name = "Username", length = 50, nullable = false)
        private String username;

        @Column(name = "Password", length = 50, nullable = false)
        private String password;

        @Column(name = "Fullname", length = 50, nullable = false)
        private String fullname;

        @Column(name = "Email", length = 50, nullable = false)
        private String email;

        @Column(name = "Photo", length = 50, nullable = false)
        private String photo;

        @Column(name = "Activated", nullable = false)
        private boolean activated;

        @Column(name = "Admin", nullable = false)
        private boolean admin;

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

}
