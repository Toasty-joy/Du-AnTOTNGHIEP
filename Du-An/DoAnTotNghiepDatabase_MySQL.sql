-- Create Database
CREATE DATABASE IF NOT EXISTS DoAnTotNghiep_Database;
USE DoAnTotNghiep_Database;

-- Drop existing tables if they exist to avoid conflicts
DROP TABLE IF EXISTS Carts, Order_Details, Orders, Shoes_Images, Sizes_Specific_Shoes, Colors_Specific_Shoes, Categories_Generic_Shoes, Products, Categories, Colors, Sizes, Generic_Shoes, Specific_Shoes, Accounts;

-- Create Accounts Table
CREATE TABLE Accounts (
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(255) NOT NULL, -- Consider storing hashed passwords
    Fullname VARCHAR(50) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Photo VARCHAR(50) NOT NULL,
    Activated BIT NOT NULL,
    Admin BIT NOT NULL,
    PRIMARY KEY (Username),
    UNIQUE (Email)
) ENGINE=InnoDB;

-- Create Categories Table
CREATE TABLE Categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_delete BIT NOT NULL
) ENGINE=InnoDB;

-- Create Colors Table
CREATE TABLE Colors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Create Sizes Table
CREATE TABLE Sizes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(45) NOT NULL,
    details TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Create Generic_Shoes Table
CREATE TABLE Generic_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    image VARCHAR(50) NULL,
    details TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Create Specific_Shoes Table
CREATE TABLE Specific_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    generic_shoes_id INT NOT NULL,
    name VARCHAR(80) NOT NULL,
    price DECIMAL(10,2) NOT NULL, -- Updated to DECIMAL for numeric handling
    order_type ENUM('Online', 'In-store') NOT NULL,
    stock TINYINT NOT NULL,
    sort TINYINT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (generic_shoes_id) REFERENCES Generic_Shoes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create Shoes_Images Table
CREATE TABLE Shoes_Images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    image VARCHAR(50) NULL,
    `order` TINYINT NOT NULL,
    specific_shoes_id INT NOT NULL,
    FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create Sizes_Specific_Shoes Table (Join Table)
CREATE TABLE Sizes_Specific_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    size_id INT NOT NULL,
    specific_shoes_id INT NOT NULL,
    FOREIGN KEY (size_id) REFERENCES Sizes(id) ON DELETE CASCADE,
    FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create Colors_Specific_Shoes Table (Join Table)
CREATE TABLE Colors_Specific_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    color_id INT NOT NULL,
    specific_shoes_id INT NOT NULL,
    FOREIGN KEY (color_id) REFERENCES Colors(id) ON DELETE CASCADE,
    FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create Categories_Generic_Shoes Table (Join Table)
CREATE TABLE Categories_Generic_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    generic_shoes_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Categories(id) ON DELETE CASCADE,
    FOREIGN KEY (generic_shoes_id) REFERENCES Generic_Shoes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create Products Table
CREATE TABLE Products (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Sizes VARCHAR(50) DEFAULT 'N/A',
    Colors VARCHAR(50) DEFAULT 'N/A',
    Rating DECIMAL(2, 1) DEFAULT 0.0,
    Review INT DEFAULT 0,
    Likes INT DEFAULT 0,
    Info VARCHAR(255) DEFAULT 'N/A',
    Policy VARCHAR(255) DEFAULT 'N/A',
    Delivery VARCHAR(255) DEFAULT 'N/A',
    Benefit VARCHAR(255) DEFAULT 'N/A',
    ServiceHotline VARCHAR(200) DEFAULT 'N/A',
    CategoryID INT,
    FOREIGN KEY (CategoryID) REFERENCES Categories(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Create Carts Table
CREATE TABLE Carts (
    username VARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    PRIMARY KEY (username, product_id),
    FOREIGN KEY (username) REFERENCES Accounts (Username) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products (ID) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create Orders Table
CREATE TABLE Orders (
    Id BIGINT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NULL,
    create_date DATE NOT NULL,
    Address VARCHAR(100) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    status BIT NOT NULL,
    FOREIGN KEY (Username) REFERENCES Accounts (Username) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Create Order_Details Table
CREATE TABLE Order_Details (
    Id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT,
    product_id INT,
    Price DECIMAL(10,2),
    Quantity INT DEFAULT 1,
    FOREIGN KEY (order_id) REFERENCES Orders(Id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(ID) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Insert sample data into Accounts table
INSERT INTO Accounts (Username, Password, Fullname, Email, Photo, Activated, Admin) VALUES
    ('NV001', '123', 'Lê Xuân Tường', 'tuonglxps20748@fpt.edu.vn', 'tuong.jpg', 1, 1),
    ('NV002', '123', 'Nguyễn Anh Khoa', 'nhattqps12345@fpt.edu.vn', 'khoa.jpg', 1, 1),
    ('NV003', '123', 'Lê Thuận Phát', 'loanvntps54321@fpt.edu.vn', 'phat.jpg', 1, 0),
    ('NV004', '123', 'Nguyễn Ngọc Phát', 'ngathps12312@fpt.edu.vn', 'phat.jpg', 1, 0),
    ('NV005', '123', 'Tài Nguyễn', 'phitmps32112@fpt.edu.vn', 'tai.jpg', 1, 0),
    ('NV006', '123', 'Trung Hiếu', 'thangnccps34567@fpt.edu.vn', 'hieu.jpg', 1, 0),
    ('Admin123', '123456', 'Admin', 'adminadmin123@fpt.edu.vn', 'admin.jpg', 1, 1);
INSERT INTO Categories (id, name, is_delete) VALUES
    (100, 'Category 100', 0),
    (101, 'Category 101', 0);
ALTER TABLE Products
    DROP FOREIGN KEY products_ibfk_1;

ALTER TABLE Products
    ADD CONSTRAINT products_ibfk_1 FOREIGN KEY (CategoryID) REFERENCES Categories(id) ON DELETE SET NULL;

-- Insert sample data into Products table
INSERT INTO Products (Name, Price, Sizes, Colors, Rating, Review, Likes, Info, Policy, Delivery, Benefit, ServiceHotline, CategoryID) VALUES
    ('Giày Cao Gót MWC 4403', 235000, 'N/A', 'N/A', 0, 0, 0, 'Giày Cao Gót Bít Mũi Bít Gót, Cao Gót Nữ Đế Vuông Cao 9 Phân Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
    ('Giày Cao Gót MWC G014', 235000, 'N/A', 'N/A', 0, 0, 0, 'Cao Gót Quai Mãnh Đính Đá Sang Chảnh, Giày Cao Gót Đế Trụ Cao 7cm Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
    ('Giày Sandal Cao Gót MWC G021', 235000, 'N/A', 'N/A', 0, 0, 0, 'Sandal Cao Gót Nữ Quai Ngang Siêu Bền Đẹp, Sandal Nữ Đế Vuông Cao 7cm Kiểu Dáng Basic Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
    ('Giày Cao Gót MWC G085', 235000, 'N/A', 'N/A', 0, 0, 0, 'Giày Cao Gót Nữ Quai Mảnh Đính Đá Sang Chảnh, Cao Gót Đế Trụ Nhỏ Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
    ('Dép Nữ MWC 8444', 195000, 'N/A', 'N/A', 0, 0, 0, 'Dép Sandal Cross Nữ Gắn Sticker Siêu Bền Đẹp, Dép Nữ Đúc Nguyên Khối Năng Động, Trẻ Trung, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 101),
    ('Giày Sandal Nữ MWC E110', 195000, 'N/A', 'N/A', 0, 0, 0, 'Sandal Nữ Quai Chéo Đính Hình Bươm Bướm Nhỏ Xinh, Sandal Nữ Đế Bánh Mì Siêu Hack Dáng, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 101),
    ('Giày Sandal Nữ MWC E122', 275000, 'N/A', 'N/A', 0, 0, 0, 'Sandal Nữ Quai Mảnh Chéo Thanh Lịch, Sandal Nữ Đế Bánh Mì Siêu Bền Đẹp Hack Dáng, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 101),
    ('Dép Nữ MWC 8475', 79000, 'N/A', 'N/A', 0, 0, 0, 'Dép Kẹp Nữ Siêu Cute, Dép Nữ Cao Su Dẻo Đúc Nguyên Khối Êm Nhẹ Đi Chơi, Đi Biển Phong Cách Trẻ Trung.', 'N/A', 'N/A', 'N/A', 'N/A', 101);
