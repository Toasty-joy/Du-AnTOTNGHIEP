-- Create Database
CREATE DATABASE IF NOT EXISTS DoAnTotNghiep_Database;
USE DoAnTotNghiep_Database;

-- Create Accounts Table
CREATE TABLE Accounts (
    Username VARCHAR(50) NOT NULL,          -- Tên đăng nhập
    Password VARCHAR(50) NOT NULL,          -- Mật khẩu
    Fullname VARCHAR(50) NOT NULL,          -- Họ và tên đầy đủ
    Email VARCHAR(50) NOT NULL,             -- Email
    Photo VARCHAR(50) NULL,                 -- Ảnh đại diện
    Phone VARCHAR(20) NULL,                 -- Số điện thoại
    Address VARCHAR(100) NULL,              -- Địa chỉ
    Gender TINYINT(1) NULL,                 -- Giới tính: 0 (Nam), 1 (Nữ)
    Birthdate DATE NULL,                    -- Ngày tháng năm sinh
    Activated BOOLEAN NOT NULL,             -- Trạng thái kích hoạt tài khoản
    Admin BOOLEAN NOT NULL,                 -- Vai trò: 1 (Quản trị viên), 0 (Người dùng)
    PRIMARY KEY (Username)                  -- Khóa chính
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
    number VARCHAR(45) NOT NULL,  -- Size (ex: 36, 37, 38)
    details TEXT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- Create Categories Table
CREATE TABLE Categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_delete TINYINT(1) NOT NULL
);

-- Create Products Table
CREATE TABLE Products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    image TEXT NULL,
    price DECIMAL(10, 2) NOT NULL,
    create_date DATE NOT NULL,
    quantity INT NOT NULL,
    category_id INT NOT NULL,
    is_delete TINYINT(1) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Categories(id)
);

-- Create Sizes_Products Table (Join Table between Sizes and Products)
CREATE TABLE Sizes_Products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    size_id INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (size_id) REFERENCES Sizes(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- Create Carts Table (with size and color)
CREATE TABLE Carts (
    username VARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,      -- Số lượng sản phẩm
    color_id INT NOT NULL,      -- Màu sắc của sản phẩm
    size_id INT NOT NULL,       -- Kích cỡ của sản phẩm
    PRIMARY KEY (username, product_id, color_id, size_id),  -- Khóa chính bao gồm username, product_id, color_id và size_id
    FOREIGN KEY (username) REFERENCES Accounts(Username),
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (color_id) REFERENCES Colors(id),
    FOREIGN KEY (size_id) REFERENCES Sizes(id)  -- Ràng buộc với bảng Sizes
);

-- Create Orders Table
CREATE TABLE Orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    create_date TIMESTAMP NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,  -- Thêm cột số điện thoại
    total DECIMAL(10, 2) NOT NULL,
    status INT NOT NULL,  -- Bỏ ràng buộc CHECK
    payment_method TINYINT(1) NOT NULL,  -- Chỉ có 2 giá trị: 0 hoặc 1
    FOREIGN KEY (username) REFERENCES Accounts(Username)
);



-- Create Order_Details Table
CREATE TABLE Order_Details (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    color_id INT NOT NULL,      -- Màu sắc sản phẩm trong đơn hàng
    size_id INT NOT NULL,       -- Kích cỡ sản phẩm trong đơn hàng
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (color_id) REFERENCES Colors(id),
    FOREIGN KEY (size_id) REFERENCES Sizes(id)
);

-- Create Shoes Images Table
CREATE TABLE Shoes_Images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    image VARCHAR(50) NULL,
    order_image TINYINT(1) NOT NULL,  -- 0 hoặc 1 xác định ảnh hiển thị
    product_id INT NOT NULL,          -- Thêm khóa ngoại vào Products
    color_id INT NOT NULL,            -- Màu sắc
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (color_id) REFERENCES Colors(id)
);

INSERT INTO Accounts (Username, Password, Fullname, Email, Photo, Activated, Admin) VALUES
    ('NV001', '123', 'Lê Xuân Tường', 'tuonglxps20748@fpt.edu.vn', 'user.png', 1, 1),
    ('NV002', '123', 'Nguyễn Anh Khoa', 'nhattqps12345@fpt.edu.vn', 'user.png', 1, 1),
    ('NV003', '123', 'Lê Thuận Phát', 'loanvntps54321@fpt.edu.vn', 'user.png', 1, 0),
    ('NV004', '123', 'Nguyễn Ngọc Phát', 'ngathps12312@fpt.edu.vn', 'user.png', 1, 0),
    ('NV005', '123', 'Tài Nguyễn', 'phitmps32112@fpt.edu.vn', 'user.png', 1, 0),
    ('NV006', '123', 'Trung Hiếu', 'thangnccps34567@fpt.edu.vn', 'user.png', 1, 0),
    ('Admin123', '123456', 'Admin', 'adminadmin123@fpt.edu.vn', 'user.png', 1, 1);
INSERT INTO Categories (id, name, is_delete) VALUES
    (100, 'Giày Cao Gót Nữ', 0), -- 1 8
    (101, 'Dép & Sandal Nữ', 0),-- 9 16
      (102, 'Giày nam', 0),-- 17 24
      (103, 'Dép nam', 0), -- 25 32
      (104, 'Giày thể thao', 0);-- 33 40
INSERT INTO Products (Name, Image, Price, create_date, Quantity, category_id, is_delete) VALUES
-- Giày Cao Gót Nữ
('Giày Cao Gót MWC 4403', 'caogotnu1.0.jpg', 250000, '2024-01-24', 30, 100, FALSE),
('Giày Cao Gót MWC G014', 'caogotnu2.0.jpg', 129000, '2024-02-24', 20, 100, FALSE),
('Giày Sandal Cao Gót MWC G021', 'caogotnu3.0.jpg', 75000, '2024-03-24', 15, 100, FALSE),
('Giày Cao Gót MWC G085', 'caogotnu4.0.jpg', 150000, '2024-03-24', 10, 100, FALSE),

('Giày Cao Gót MWC G150', 'caogotnu5.0.jpg', 275000, '2024-03-24', 10, 100, FALSE),
('Giày Cao Gót MWC G130', 'caogotnu6.0.jpg', 150000, '2024-03-24', 10, 100, FALSE),
('Giày Cao Gót MWC G116', 'caogotnu7.0.jpg', 295000, '2024-03-24', 10, 100, FALSE),
('Giày Cao Gót MWC G147', 'caogotnu8.0.jpg',  295000, '2024-03-24', 10, 100, FALSE),
-- Dép & Sandal Nữ
('Dép Nữ MWC 8495', 'depnu1.0.jpg', 99000 , '2024-03-20', 10, 101, FALSE),
('Dép Nữ MWC 8505', 'depnu2.0.jpg', 250000 , '2024-03-20', 10, 101, FALSE),
('Dép Nữ MWC 8497', 'depnu3.0.jpg', 79000 , '2024-03-20', 10, 101, FALSE),
('Dép Nữ MWC 8379', 'depnu4.0.jpg', 79000 , '2024-03-20', 10, 101, FALSE),
('Dép Nữ MWC 8501', 'depnu5.0.jpg', 125000 , '2024-03-20', 10, 101, FALSE),
('Dép Nữ MWC 8499', 'depnu6.0.jpg', 79000 , '2024-03-20', 10, 101, FALSE),
('Dép Nữ MWC 8496', 'depnu7.0.jpg', 99000 , '2024-03-20', 10, 101, FALSE),
('Dép Nữ MWC 8488', 'depnu8.0.jpg', 150000 , '2024-03-20', 10, 101, FALSE),
-- Giày nam
('Giày Thể Thao Nam MWC 5723', 'giaynam1.0.jpg', 195000, '2024-01-24', 30, 102, FALSE),-- da ng, trang
('Giày Thể Thao Nam MWC 5706', 'giaynam2.0.jpg', 295000, '2024-02-24', 20, 102, FALSE),-- trang,da ng
('Giày Thể Thao Nam MWC 5735', 'giaynam3.0.jpg', 275000, '2024-03-24', 15, 102, FALSE),-- nau,trang
('Giày Thể Thao Nam MWC 5739', 'giaynam4.0.jpg', 250000, '2024-03-24', 10, 102, FALSE),-- xam
('Giày Thể Thao Nam MWC 5741', 'giaynam5.0.jpg', 250000, '2024-01-15', 30, 102, FALSE),-- den
('Giày Thể Thao Nam MWC 5740', 'giaynam6.0.jpg', 250000, '2024-02-20', 20, 102, FALSE),-- xam
('Giày Thể Thao Nam MWC 5733', 'giaynam7.0.jpg', 275000, '2024-03-05', 15, 102, FALSE),-- trang
('Giày Thể Thao Nam MWC 5734', 'giaynam8.0.jpg', 375000, '2024-03-20', 10, 102, FALSE),-- nau-den
-- Dép nam
('Dép Nam MWC 7899', 'depnam1.0.jpg', 250000 , '2024-03-20', 10, 103, FALSE),
('Dép Nam MWC 7898', 'depnam2.0.jpg', 250000 , '2024-03-20', 10, 103, FALSE),
('Dép Nam MWC 7897', 'depnam3.0.jpg', 250000 , '2024-03-20', 10, 103, FALSE),
('Dép Nam MWC 7901', 'depnam4.0.jpg', 195000 , '2024-03-20', 10, 103, FALSE),
('Dép Nam MWC 7907', 'depnam5.0.jpg', 215000 , '2024-03-20', 10, 103, FALSE),
('Dép Nam MWC 7908', 'depnam6.0.jpg', 235000 , '2024-03-20', 10, 103, FALSE),
('Dép Nam MWC 7914', 'depnam7.0.jpg', 250000 , '2024-03-20', 10, 103, FALSE),
('Dép Nam MWC 7911', 'depnam8.0.jpg', 235000 , '2024-03-20', 10, 103, FALSE),
-- Giày thể thao
('Giày Thể Thao Nữ MWC A224', 'GiaythethaoNu1.0.jpg', 345000 , '2024-03-20', 10, 104, FALSE),
('Giày Thể Thao Nữ MWC A220', 'GiaythethaoNu2.0.jpg', 295000  , '2024-03-20', 10, 104, FALSE),
('Giày Thể Thao Nữ MWC A221', 'GiaythethaoNu3.0.jpg', 295000  , '2024-03-20', 10, 104, FALSE),
('Giày Thể Thao Nữ MWC A159', 'GiaythethaoNu4.0.jpg', 295000  , '2024-03-20', 10, 104, FALSE),
('Giày Thể Thao Nữ MWC A198', 'GiaythethaoNu5.0.jpg', 275000  , '2024-03-20', 10, 104, FALSE),
('Giày Thể Thao Nữ MWC A219', 'GiaythethaoNu6.0.jpg', 295000  , '2024-03-20', 10, 104, FALSE),
('Giày Thể Thao Nữ MWC A217', 'GiaythethaoNu7.0.jpg', 345000  , '2024-03-20', 10, 104, FALSE),
('Giày Thể Thao Nữ MWC A218', 'GiaythethaoNu8.0.jpg', 345000  , '2024-03-20', 10, 104, FALSE);

INSERT INTO Sizes (number, details) VALUES 
    ('34', 'Size nhỏ nhất cho nữ'), 
    ('35', 'Size nhỏ cho nữ'), 
    ('36', 'Size trung bình nhỏ'), 
    ('37', 'Size trung bình'), 
    ('38', 'Size trung bình lớn'), 
    ('39', 'Size lớn cho nữ'), 
    ('40', 'Size nhỏ cho nam'), 
    ('41', 'Size trung bình nhỏ cho nam'), 
    ('42', 'Size trung bình cho nam'), 
    ('43', 'Size trung bình lớn cho nam'), 
    ('44', 'Size lớn cho nam'), 
    ('45', 'Size rất lớn cho nam'), 
    ('46', 'Size cực lớn cho nam'), 
    ('47', 'Size khổng lồ cho nam'), 
    ('48', 'Size siêu khổng lồ cho nam'), 
    ('49', 'Size khổng lồ nhất cho nam'), 
    ('50', 'Size lớn nhất cho nam');


   -- Sản phẩm 1 đến 16: Liên kết với size nữ (size_id từ 1 đến 5)
INSERT INTO Sizes_Products (size_id, product_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
(1, 2), (2, 2), (3, 2), (4, 2), (5, 2),
(1, 3), (2, 3), (3, 3), (4, 3), (5, 3),
(1, 4), (2, 4), (3, 4), (4, 4), (5, 4),
(1, 5), (2, 5), (3, 5), (4, 5), (5, 5),
(1, 6), (2, 6), (3, 6), (4, 6), (5, 6),
(1, 7), (2, 7), (3, 7), (4, 7), (5, 7),
(1, 8), (2, 8), (3, 8), (4, 8), (5, 8),
(1, 9), (2, 9), (3, 9), (4, 9), (5, 9),
(1, 10), (2, 10), (3, 10), (4, 10), (5, 10),
(1, 11), (2, 11), (3, 11), (4, 11), (5, 11),
(1, 12), (2, 12), (3, 12), (4, 12), (5, 12),
(1, 13), (2, 13), (3, 13), (4, 13), (5, 13),
(1, 14), (2, 14), (3, 14), (4, 14), (5, 14),
(1, 15), (2, 15), (3, 15), (4, 15), (5, 15),
(1, 16), (2, 16), (3, 16), (4, 16), (5, 16),

-- Sản phẩm 17 đến 32: Liên kết với size nam (size_id từ 7 đến 11)
(7, 17), (8, 17), (9, 17), (10, 17), (11, 17),
(7, 18), (8, 18), (9, 18), (10, 18), (11, 18),
(7, 19), (8, 19), (9, 19), (10, 19), (11, 19),
(7, 20), (8, 20), (9, 20), (10, 20), (11, 20),
(7, 21), (8, 21), (9, 21), (10, 21), (11, 21),
(7, 22), (8, 22), (9, 22), (10, 22), (11, 22),
(7, 23), (8, 23), (9, 23), (10, 23), (11, 23),
(7, 24), (8, 24), (9, 24), (10, 24), (11, 24),
(7, 25), (8, 25), (9, 25), (10, 25), (11, 25),
(7, 26), (8, 26), (9, 26), (10, 26), (11, 26),
(7, 27), (8, 27), (9, 27), (10, 27), (11, 27),
(7, 28), (8, 28), (9, 28), (10, 28), (11, 28),
(7, 29), (8, 29), (9, 29), (10, 29), (11, 29),
(7, 30), (8, 30), (9, 30), (10, 30), (11, 30),
(7, 31), (8, 31), (9, 31), (10, 31), (11, 31),
(7, 32), (8, 32), (9, 32), (10, 32), (11, 32),

-- Sản phẩm 33 đến 40: Liên kết với size nữ (size_id từ 1 đến 5)
(1, 33), (2, 33), (3, 33), (4, 33), (5, 33),
(1, 34), (2, 34), (3, 34), (4, 34), (5, 34),
(1, 35), (2, 35), (3, 35), (4, 35), (5, 35),
(1, 36), (2, 36), (3, 36), (4, 36), (5, 36),
(1, 37), (2, 37), (3, 37), (4, 37), (5, 37),
(1, 38), (2, 38), (3, 38), (4, 38), (5, 38),
(1, 39), (2, 39), (3, 39), (4, 39), (5, 39),
(1, 40), (2, 40), (3, 40), (4, 40), (5, 40);

    
    INSERT INTO Colors (name) VALUES 
('den'), -- 1
('trang'), -- 2
('nau'), -- 3
('xam'), -- 4
('da_nguoi'), -- 5
('kemxanh'), -- 6
('kemnau'),-- 7
('vang_den') -- 8
;
INSERT INTO Shoes_Images (image, order_image, product_id, color_id)
VALUES
  -- Sản phẩm 1, Màu 2 trang
  ('caogotnu1.0.jpg', 1, 1, 2),
  ('caogotnu1.1.jpg', 2, 1, 2),
  ('caogotnu1.2.jpg', 3, 1, 2),

  -- Sản phẩm 1, Màu 1
  ('caogotnu1.3.jpg', 1, 1, 1),
  ('caogotnu1.4.jpg', 2, 1, 1),
  ('caogotnu1.5.jpg', 3, 1, 1),

  -- Sản phẩm 2, Màu 1
  ('caogotnu2.0.jpg', 1, 2, 1),
  ('caogotnu2.1.jpg', 2, 2, 1),
  ('caogotnu2.2.jpg', 3, 2, 1),

  -- Sản phẩm 2, Màu 3
  ('caogotnu2.3.jpg', 1, 2, 3),
  ('caogotnu2.4.jpg', 2, 2, 3),
  ('caogotnu2.5.jpg', 3, 2, 3),
  
 -- Sản phẩm 3, Màu 3
('caogotnu3.0.jpg', 1, 3, 3),
  ('caogotnu3.1.jpg', 2, 3, 3),
  ('caogotnu3.2.jpg', 3, 3, 3),
 -- Sản phẩm 3, Màu 1
 ('caogotnu3.3.jpg', 1, 3, 1),
  ('caogotnu3.4.jpg', 2, 3, 1),
  ('caogotnu3.5.jpg', 3, 3, 1),
 
  -- Sản phẩm 4, Màu 4
('caogotnu4.0.jpg', 1, 4, 4),
('caogotnu4.1.jpg', 2, 4, 4),
('caogotnu4.2.jpg', 3, 4, 4),
 -- Sản phẩm 4, Màu 1
('caogotnu4.3.jpg', 1, 4, 1),
('caogotnu4.4.jpg', 2, 4, 1),
('caogotnu4.5.jpg', 3, 4, 1),

  -- Sản phẩm 5, Màu 3
('caogotnu5.0.jpg', 1, 5, 3),
('caogotnu5.1.jpg', 2, 5, 3),
('caogotnu5.2.jpg', 3, 5, 3),
 -- Sản phẩm 5, Màu 1
('caogotnu5.3.jpg', 1, 5, 1),
('caogotnu5.4.jpg', 2, 5, 1),
('caogotnu5.5.jpg', 3, 5, 1),

 -- Sản phẩm 6, Màu 4
('caogotnu6.0.jpg', 1, 6, 4),
('caogotnu6.1.jpg', 2, 6, 4),
('caogotnu6.2.jpg', 3, 6, 4),
('caogotnu6.3.jpg', 1, 6, 4),
('caogotnu6.4.jpg', 2, 6, 4),
('caogotnu6.5.jpg', 3, 6, 4),

-- Sản phẩm 7, Màu 1
('caogotnu7.0.jpg', 1, 7, 1),
('caogotnu7.1.jpg', 2, 7, 1),
('caogotnu7.2.jpg', 3, 7, 1),
('caogotnu7.3.jpg', 1, 7, 1),
('caogotnu7.4.jpg', 2, 7, 1),
('caogotnu7.5.jpg', 3, 7, 1),
 
-- Sản phẩm 8 , Màu 3
('caogotnu8.0.jpg', 1, 8, 3),
('caogotnu8.1.jpg', 2, 8, 3),
('caogotnu8.2.jpg', 3, 8, 3),
 -- Sản phẩm 8, Màu 1
('caogotnu8.3.jpg', 1, 8, 1),
('caogotnu8.4.jpg', 2, 8, 1),
('caogotnu8.5.jpg', 3, 8, 1),

-- Sản phẩm 9, màu 1
('depnu1.0.jpg', 1, 9, 5),
('depnu1.1.jpg', 2, 9, 5),
('depnu1.2.jpg', 3, 9, 5),
-- Sản phẩm 9, màu 2
('depnu1.3.jpg', 1, 9, 1),
('depnu1.4.jpg', 2, 9, 1),
('depnu1.5.jpg', 3, 9, 1),
-- Sản phẩm 10, màu 1
('depnu2.0.jpg', 1, 10, 7),
('depnu2.1.jpg', 2, 10, 7),
('depnu2.2.jpg', 3, 10, 7),
-- Sản phẩm 10, màu 2
('depnu2.3.jpg', 1, 10, 5),
('depnu2.4jpg', 2, 10, 5),
('depnu2.5.jpg', 3, 10, 5),
-- Sản phẩm 11, màu 1
('depnu3.0.jpg', 1, 11, 3),
('depnu3.1.jpg', 2, 11, 3),
('depnu3.2.jpg', 3, 11, 3),
-- Sản phẩm 11, màu 2
('depnu3.3.jpg', 1, 11, 1),
('depnu3.4.jpg', 2, 11, 1),
('depnu3.5.jpg', 3, 11, 1),
-- Sản phẩm 12, màu 1
('depnu4.0.jpg', 1, 12, 5),
('depnu4.1.jpg', 2, 12, 5),
('depnu4.2.jpg', 3, 12, 5),
-- Sản phẩm 12, màu 2
('depnu4.3.jpg', 1, 12, 1),
('depnu4.4jpg', 2, 12, 1),
('depnu4.5.jpg', 3, 12, 1),
-- Sản phẩm 13, màu 1
('depnu5.0.jpg', 1, 13, 1),
('depnu5.1.jpg', 2, 13, 1),
('depnu5.2.jpg', 3, 13, 1),
-- Sản phẩm 13, màu 2
('depnu5.3.jpg', 1, 13, 5),
('depnu5.4jpg', 2, 13, 5),
('depnu5.5.jpg', 3, 13, 5), 
 -- Sản phẩm 14, màu 1
('depnu6.0.jpg', 1, 14, 5),
('depnu6.1.jpg', 2, 14, 5),
('depnu6.2.jpg', 3, 14, 5),
-- Sản phẩm 14, màu 2
('depnu6.3.jpg', 1, 14, 1),
('depnu6.4jpg', 2, 14, 1),
('depnu6.5.jpg', 3, 14, 1),
-- Sản phẩm 15, màu 1
('depnu7.0.jpg', 1, 15, 5),
('depnu7.1.jpg', 2, 15, 5),
('depnu7.2.jpg', 3, 15, 5),
-- Sản phẩm 15, màu 2
('depnu7.3.jpg', 1, 15, 1),
('depnu7.4jpg', 2, 15, 1),
('depnu7.5.jpg', 3, 15, 1),
-- Sản phẩm 16, màu 1
('depnu8.0.jpg', 1, 16, 4),
('depnu8.1.jpg', 2, 16, 4),
('depnu8.2.jpg', 3, 16, 4),
-- Sản phẩm 16, màu 2
('depnu8.3.jpg', 1, 16, 3),
('depnu8.4jpg', 2, 16, 3),
('depnu8.5.jpg', 3, 16, 3),
-- Sản phẩm 17, Màu 1
  ('giaynam1.0.jpg', 1, 17, 5),
  ('giaynam1.1.jpg', 2, 17, 5),
  ('giaynam1.2.jpg', 3, 17, 5),

  -- Sản phẩm 17, Màu 2
  ('giaynam1.3.jpg', 1, 17, 2),
  ('giaynam1.4.jpg', 2, 17, 2),
  ('giaynam1.5.jpg', 3, 17, 2),

  -- Sản phẩm 18, Màu 1
  ('giaynam2.0.jpg', 1, 18, 2),
  ('giaynam2.1.jpg', 2, 18, 2),
  ('giaynam2.2.jpg', 3, 18, 2),

  -- Sản phẩm 18, Màu 2
  ('giaynam2.3.jpg', 1, 18, 5),
  ('giaynam2.4.jpg', 2, 18, 5),
  ('giaynam2.5.jpg', 3, 18, 5),

 -- Sản phẩm 19, Màu 2
  ('giaynam3.0.jpg', 1, 19, 3),
  ('giaynam3.1.jpg', 2, 19, 3),
  ('giaynam3.2.jpg', 3, 19, 3),

   -- Sản phẩm 19, Màu 2
  ('giaynam3.3.jpg', 1, 19, 2),
  ('giaynam3.4.jpg', 2, 19, 2),
  ('giaynam3.5.jpg', 3, 19, 2),

   -- Sản phẩm 20, Màu 1
  ('giaynam4.0.jpg', 1, 20, 4),
  ('giaynam4.1.jpg', 2, 20, 4),
  ('giaynam4.2.jpg', 3, 20, 4),
  ('giaynam4.3.jpg', 4, 20, 4),
  ('giaynam4.4.jpg', 5, 20, 4),
  ('giaynam4.5.jpg', 6, 20, 4),

   -- Sản phẩm 21, Màu 1
  ('giaynam5.0.jpg', 1, 21, 1),
  ('giaynam5.1.jpg', 2, 21, 1),
  ('giaynam5.2.jpg', 3, 21, 1),
  ('giaynam5.3.jpg', 4, 21, 1),
  ('giaynam5.4.jpg', 5, 21, 1),
  ('giaynam5.5.jpg', 6, 21, 1),

     -- Sản phẩm 22, Màu 1
  ('giaynam6.0.jpg', 1, 22, 4),
  ('giaynam6.1.jpg', 2, 22, 4),
  ('giaynam6.2.jpg', 3, 22, 4),
  ('giaynam6.3.jpg', 4, 22, 4),
  ('giaynam6.4.jpg', 5, 22, 4),
  ('giaynam6.5.jpg', 6, 22, 4),

     -- Sản phẩm 23, Màu 1
  ('giaynam7.0.jpg', 1, 23, 2),
  ('giaynam7.1.jpg', 2, 23, 2),
  ('giaynam7.2.jpg', 3, 23, 2),
  ('giaynam7.3.jpg', 4, 23, 2),
  ('giaynam7.4.jpg', 5, 23, 2),
  ('giaynam7.5.jpg', 6, 23, 2),

 -- Sản phẩm 24, Màu 1
  ('giaynam8.0.jpg', 1, 24, 3),
  ('giaynam8.1.jpg', 2, 24, 3),
  ('giaynam8.2.jpg', 3, 24, 3),

  -- Sản phẩm 24, Màu 2
  ('giaynam8.3.jpg', 1, 24, 1),
  ('giaynam8.4.jpg', 2, 24, 1),
  ('giaynam8.5.jpg', 3, 24, 1),

-- Sản phẩm 25, màu 1
('depnam1.0.jpg', 1, 25, 1),
('depnam1.1.jpg', 2, 25, 1),
('depnam1.2.jpg', 3, 25, 1),
-- Sản phẩm 25, màu 2
('depnam1.3.jpg', 1, 25, 4),
('depnam1.4.jpg', 2, 25, 4),
('depnam1.5.jpg', 3, 25, 4),
-- Sản phẩm 26, màu 1
('depnam2.0.jpg', 1, 26, 1),
('depnam2.1.jpg', 2, 26, 1),
('depnam2.2.jpg', 3, 26, 1),
-- Sản phẩm 26, màu 2
('depnam2.3.jpg', 1, 26, 4),
('depnam2.4.jpg', 2, 26, 4),
('depnam2.5.jpg', 3, 26, 4),
-- Sản phẩm 27, màu 1
('depnam3.0.jpg', 1, 27, 1),
('depnam3.1.jpg', 2, 27, 1),
('depnam3.2.jpg', 3, 27, 1),
('depnam3.3.jpg', 4, 27, 1),
('depnam3.4.jpg', 5, 27, 1),
('depnam3.5.jpg', 6, 27, 1),
-- Sản phẩm 28, màu 1
('depnam4.0.jpg', 1, 28, 1),
('depnam4.1.jpg', 2, 28, 1),
('depnam4.2.jpg', 3, 28, 1),
-- Sản phẩm 28, màu 2
('depnam4.3.jpg', 1, 28, 3),
('depnam4.4.jpg', 2, 28, 3),
('depnam4.5.jpg', 3, 28, 3),
-- Sản phẩm 29, màu 1
('depnam5.0.jpg', 1, 29, 1),
('depnam5.1.jpg', 2, 29, 1),
('depnam5.2.jpg', 3, 29, 1),
('depnam5.3.jpg', 4, 29, 1),
('depnam5.4.jpg', 5, 29, 1),
('depnam5.5.jpg', 6, 29, 1),
-- Sản phẩm 30, màu 1
('depnam6.0.jpg', 1, 30, 1),
('depnam6.1.jpg', 2, 30, 1),
('depnam6.2.jpg', 3, 30, 1),
-- Sản phẩm 30, màu 2
('depnam6.3.jpg', 1, 30, 4),
('depnam6.4.jpg', 2, 30, 4),
('depnam6.5.jpg', 3, 30, 4),
-- Sản phẩm 31, màu 1
('depnam7.0.jpg', 1, 31, 1),
('depnam7.1.jpg', 2, 31, 1),
('depnam7.2.jpg', 3, 31, 1),
('depnam7.3.jpg', 4, 31, 1),
('depnam7.4.jpg', 5, 31, 1),
('depnam7.5.jpg', 6, 31, 1),
-- Sản phẩm 32, màu 1
('depnam8.0.jpg', 1, 32, 1),
('depnam8.1.jpg', 2, 32, 1),
('depnam8.2.jpg', 3, 32, 1),
('depnam8.3.jpg', 4, 32, 1),
('depnam8.4.jpg', 5, 32, 1),
('depnam8.5.jpg', 6, 32, 1),
-- Sản phẩm 33, màu 1
('GiaythethaoNu1.0.jpg', 1, 33, 7),
('GiaythethaoNu1.1.jpg', 2, 33, 7),
('GiaythethaoNu1.2.jpg', 3, 33, 7),
-- Sản phẩm 33, màu 2
('GiaythethaoNu1.3.jpg', 1, 33, 6),
('GiaythethaoNu1.4.jpg', 2, 33, 6),
('GiaythethaoNu1.5.jpg', 3, 33, 6),
-- Sản phẩm 34, màu 1
('GiaythethaoNu2.0.jpg', 1, 34, 8),
('GiaythethaoNu2.1.jpg', 2, 34, 8),
('GiaythethaoNu2.2.jpg', 3, 34, 8),
-- Sản phẩm 34, màu 2
('GiaythethaoNu2.3.jpg', 1, 34, 6),
('GiaythethaoNu2.4.jpg', 1, 34, 6),
('GiaythethaoNu2.5.jpg', 1, 34, 6),
-- Sản phẩm 35, màu 1
('GiaythethaoNu3.0.jpg', 1, 35, 8),
('GiaythethaoNu3.1.jpg', 2, 35, 8),
('GiaythethaoNu3.2.jpg', 3, 35, 8),
-- Sản phẩm 35, màu 2
('GiaythethaoNu3.3.jpg', 1, 35, 6),
('GiaythethaoNu3.4.jpg', 1, 35, 6),
('GiaythethaoNu3.5.jpg', 1, 35, 6),
-- Sản phẩm 35, màu 1
('GiaythethaoNu3.0.jpg', 1, 35, 8),
('GiaythethaoNu3.1.jpg', 2, 35, 8),
('GiaythethaoNu3.2.jpg', 3, 35, 8),
-- Sản phẩm 35, màu 2
('GiaythethaoNu3.3.jpg', 1, 35, 6),
('GiaythethaoNu3.4.jpg', 1, 35, 6),
('GiaythethaoNu3.5.jpg', 1, 35, 6),
-- Sản phẩm 36, màu 1
('GiaythethaoNu4.0.jpg', 1, 36, 5),
('GiaythethaoNu4.1.jpg', 2, 36, 5),
('GiaythethaoNu4.2.jpg', 3, 36, 5),
-- Sản phẩm 36, màu 2
('GiaythethaoNu4.3.jpg', 1, 36, 4),
('GiaythethaoNu4.4.jpg', 1, 36, 4),
('GiaythethaoNu4.5.jpg', 1, 36, 4),
-- Sản phẩm 37, màu 1
('GiaythethaoNu5.0.jpg', 1, 37, 1),
('GiaythethaoNu5.1.jpg', 1, 37, 1),
('GiaythethaoNu5.2.jpg', 1, 37, 1),
-- Sản phẩm 38, màu 1
('GiaythethaoNu6.0.jpg', 1, 38, 2),
('GiaythethaoNu6.1.jpg', 1, 38, 2),
('GiaythethaoNu6.2.jpg', 1, 38, 2),
-- Sản phẩm 39, màu 1
('GiaythethaoNu7.0.jpg', 1, 39, 7),
('GiaythethaoNu7.1.jpg', 2, 39, 7),
('GiaythethaoNu7.2.jpg', 3, 39, 7),
-- Sản phẩm 39, màu 2
('GiaythethaoNu7.3.jpg', 1, 39, 8),
('GiaythethaoNu7.4.jpg', 1, 39, 8),
('GiaythethaoNu7.5.jpg', 1, 39, 8),
-- Sản phẩm 40, màu 1
('GiaythethaoNu8.0.jpg', 1, 40, 5),
('GiaythethaoNu8.1.jpg', 2, 40, 5),
('GiaythethaoNu8.2.jpg', 3, 40, 5),
-- Sản phẩm 40, màu 2
('GiaythethaoNu8.3.jpg', 1, 40, 7),
('GiaythethaoNu8.4.jpg', 1, 40, 7),
('GiaythethaoNu8.5.jpg', 1, 40, 7);
 
 
drop database DoAnTotNghiep_Database;
