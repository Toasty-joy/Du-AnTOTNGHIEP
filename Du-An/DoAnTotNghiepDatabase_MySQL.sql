-- Create Database
CREATE DATABASE IF NOT EXISTS DoAnTotNghiep_Database;
USE DoAnTotNghiep_Database;
-- Create Accounts Table
CREATE TABLE Accounts (
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    Fullname VARCHAR(50) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Photo VARCHAR(50) NOT NULL,
    Activated TINYINT(1) NOT NULL,
    Admin TINYINT(1) NOT NULL,
    PRIMARY KEY (Username)
);

-- Create Categories Table
CREATE TABLE Categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_delete TINYINT(1) NOT NULL
);

-- Create Colors Table
CREATE TABLE Colors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Sizes Table
CREATE TABLE Sizes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(45) NOT NULL,
    details TEXT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Generic Shoes Table
CREATE TABLE Generic_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    image VARCHAR(50) NULL,
    details TEXT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Specific Shoes Table
CREATE TABLE Specific_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    generic_shoes_id INT NOT NULL,
    name VARCHAR(80) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    order_type ENUM('Online', 'In-store') NOT NULL,
    stock TINYINT NOT NULL,
    sort TINYINT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (generic_shoes_id) REFERENCES Generic_Shoes(id)
);

-- Create Shoes Images Table
CREATE TABLE Shoes_Images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    image VARCHAR(50) NULL,
	orderImage TINYINT NOT NULL,
    specific_shoes_id INT NOT NULL,
    FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id)
);

-- Create Sizes Specific Shoes Table (Join Table)
CREATE TABLE Sizes_Specific_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    size_id INT NOT NULL,
    specific_shoes_id INT NOT NULL,
    FOREIGN KEY (size_id) REFERENCES Sizes(id),
    FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id)
);

-- Create Colors Specific Shoes Table (Join Table)
CREATE TABLE Colors_Specific_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    color_id INT NOT NULL,
    specific_shoes_id INT NOT NULL,
    FOREIGN KEY (color_id) REFERENCES Colors(id),
    FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id)
);

-- Create Categories Generic Shoes Table (Join Table)
CREATE TABLE Categories_Generic_Shoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    generic_shoes_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Categories(id),
    FOREIGN KEY (generic_shoes_id) REFERENCES Generic_Shoes(id)
);

-- Create Products Table
CREATE TABLE Products (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    Image TEXT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    create_date DATE NOT NULL,
    Quantity INT NOT NULL,
    category_id INT NOT NULL,
    is_delete TINYINT(1) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Categories(id)
);

-- Create Carts Table
CREATE TABLE Carts (
    username VARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NULL,
    PRIMARY KEY (username, product_id),
    FOREIGN KEY (username) REFERENCES Accounts(Username),
    FOREIGN KEY (product_id) REFERENCES Products(Id)
);

-- Create Orders Table
CREATE TABLE Orders (
    Id BIGINT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) NOT NULL,
    create_date DATE NOT NULL,
    Address VARCHAR(100) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    status TINYINT(1) NOT NULL,
    FOREIGN KEY (Username) REFERENCES Accounts(Username)
);

-- Create Order_Details Table
CREATE TABLE Order_Details (
    Id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NULL,
    product_id INT NULL,
    Price DECIMAL(10 , 2 ) NULL,
    Quantity INT NULL,
    FOREIGN KEY (order_id)
        REFERENCES Orders (Id),
    FOREIGN KEY (product_id)
        REFERENCES Products (Id)
);
ALTER TABLE Carts DROP FOREIGN KEY carts_ibfk_1;

ALTER TABLE Carts MODIFY COLUMN username VARCHAR(255) NOT NULL;

ALTER TABLE Carts
ADD CONSTRAINT carts_ibfk_1 FOREIGN KEY (username) REFERENCES Accounts(Username);

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
    
INSERT INTO Products (Name, Image, Price, create_date, Quantity, category_id, is_delete) VALUES
('Giày Cao Gót MWC 4403', 'caogotnu1.0.jpg', 250000, '2024-01-24', 30, 100, FALSE),
('Giày Cao Gót MWC G014', 'caogotnu2.0.jpg', 9290000, '2024-02-24', 20, 100, FALSE),
('Giày Sandal Cao Gót MWC G021', 'caogotnu3.0.jpg', 750000, '2024-03-24', 15, 100, FALSE),
('Giày Cao Gót MWC G085', 'caogotnu4.0.jpg', 1500000, '2024-03-24', 10, 101, FALSE),
('Dép Nữ MWC 8444', 'depnu1.0.jpg', 195000, '2024-01-15', 30, 100, FALSE),
('Giày Sandal Nữ MWC E110', 'depnu2.0.jpg', 9290000, '2024-02-20', 20, 101, FALSE),
('Giày Sandal Nữ MWC E112', 'depnu3.0.jpg', 750000, '2024-03-05', 15, 101, FALSE),
('Dép Nữ MWC 8475', 'depnu4.0.jpg', 1500000, '2024-03-20', 10, 101, FALSE);