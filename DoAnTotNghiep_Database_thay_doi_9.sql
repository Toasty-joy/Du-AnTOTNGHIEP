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
    number VARCHAR(45) NOT NULL,  -- Size (ex: 36, 37, 38)
    details TEXT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    ('NV001', '123', 'Lê Xuân Tường', 'tuonglxps20748@fpt.edu.vn', 'tuong.jpg', 1, 1),
    ('NV002', '123', 'Nguyễn Anh Khoa', 'nhattqps12345@fpt.edu.vn', 'khoa.jpg', 1, 1),
    ('NV003', '123', 'Lê Thuận Phát', 'loanvntps54321@fpt.edu.vn', 'phat.jpg', 1, 0),
    ('NV004', '123', 'Nguyễn Ngọc Phát', 'ngathps12312@fpt.edu.vn', 'phat.jpg', 1, 0),
    ('NV005', '123', 'Tài Nguyễn', 'phitmps32112@fpt.edu.vn', 'tai.jpg', 1, 0),
    ('NV006', '123', 'Trung Hiếu', 'thangnccps34567@fpt.edu.vn', 'hieu.jpg', 1, 0),
    ('Admin123', '123456', 'Admin', 'adminadmin123@fpt.edu.vn', 'admin.jpg', 1, 1);
INSERT INTO Categories (id, name, is_delete) VALUES
    (100, 'Giày Cao Gót Nữ', 0),
    (101, 'Dép & Sandal Nữ', 0);
INSERT INTO Products (Name, Image, Price, create_date, Quantity, category_id, is_delete) VALUES
('Giày Cao Gót MWC 4403', 'caogotnu1.0.jpg', 250000, '2024-01-24', 30, 100, FALSE),
('Giày Cao Gót MWC G014', 'caogotnu2.0.jpg', 9290000, '2024-02-24', 20, 100, FALSE),
('Giày Sandal Cao Gót MWC G021', 'caogotnu3.0.jpg', 750000, '2024-03-24', 15, 100, FALSE),
('Giày Cao Gót MWC G085', 'caogotnu4.0.jpg', 1500000, '2024-03-24', 10, 101, FALSE),
('Dép Nữ MWC 8444', 'depnu1.0.jpg', 195000, '2024-01-15', 30, 100, FALSE),
('Giày Sandal Nữ MWC E110', 'depnu2.0.jpg', 9290000, '2024-02-20', 20, 101, FALSE),
('Giày Sandal Nữ MWC E112', 'depnu3.0.jpg', 750000, '2024-03-05', 15, 101, FALSE),
('Dép Nữ MWC 8475', 'depnu4.0.jpg', 1500000, '2024-03-20', 10, 101, FALSE);

INSERT INTO Sizes (number, details) VALUES 
    ('35', 'Size nhỏ cho nữ'), 
    ('36', 'Size trung bình nhỏ'), 
    ('37', 'Size trung bình'), 
    ('38', 'Size trung bình lớn'), 
    ('39', 'Size lớn cho nữ');
    INSERT INTO Sizes_Products (size_id, product_id) VALUES 
    (1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
    (1, 2), (2, 2), (3, 2), (4, 2), (5, 2);
    INSERT INTO Colors (name) VALUES 
('den'), 
('trang'), 
('nau'), 
('xam'), 
('da_nguoi');
INSERT INTO Shoes_Images (image, order_image, product_id, color_id)
VALUES
  ('caogotnu1.0.jpg', 1, 1, 1),
  ('caogotnu1.1.jpg', 2, 1, 1),
  ('caogotnu1.2.jpg', 3, 1, 1),
  ('caogotnu1.3.jpg', 4, 1, 2),
  ('caogotnu1.4.jpg', 5, 1, 2),
  ('caogotnu1.5.jpg', 6, 1, 2),
  ('caogotnu2.0.jpg', 1, 2, 1),
  ('caogotnu2.1.jpg', 2, 2, 1),
  ('caogotnu2.2.jpg', 3, 2, 1),
  ('caogotnu2.3.jpg', 4, 2, 3),
  ('caogotnu2.4.jpg', 5, 2, 3),
  ('caogotnu2.5.jpg', 6, 2, 3);



