create database [DoAnTotNghiep_Database]

USE [DoAnTotNghiep_Database]
GO
/****** Object:  Database [DoAnTotNghiep_Database]******/
CREATE DATABASE [DoAnTotNghiep_Database]
 
ALTER DATABASE [DoAnTotNghiep_Database] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DoAnTotNghiep_Database].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET ARITHABORT OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET  DISABLE_BROKER 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET RECOVERY FULL 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET  MULTI_USER 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'DoAnTotNghiep_Database', N'ON'
GO
ALTER DATABASE [DoAnTotNghiep_Database] SET QUERY_STORE = OFF
GO
USE [DoAnTotNghiep_Database]
GO
/****** Object:  Table [dbo].[Accounts]******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Create Accounts Table
-- Create Accounts Table
CREATE TABLE Accounts (
    Username NVARCHAR(50) NOT NULL,
    Password NVARCHAR(50) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    Photo NVARCHAR(50) NOT NULL,
    Activated BIT NOT NULL,
    Admin BIT NOT NULL,
    CONSTRAINT PK_Accounts PRIMARY KEY CLUSTERED (Username ASC)
) ON [PRIMARY]
GO

-- Create Categories Table
CREATE TABLE Categories (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    is_delete BIT NOT NULL
) ON [PRIMARY]
GO

-- Create Colors Table
CREATE TABLE Colors (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
) ON [PRIMARY]
GO

-- Create Sizes Table
CREATE TABLE Sizes (
    id INT PRIMARY KEY IDENTITY(1,1),
    number VARCHAR(45) NOT NULL,
    details TEXT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
) ON [PRIMARY]
GO

-- Create Generic Shoes Table
CREATE TABLE Generic_Shoes (
    id INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(80) NOT NULL,
    image VARCHAR(50) NULL,
    details TEXT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
) ON [PRIMARY]
GO

-- Create Specific Shoes Table
CREATE TABLE Specific_Shoes (
    id INT PRIMARY KEY IDENTITY(1,1),
    generic_shoes_id INT NOT NULL,
    name VARCHAR(80) NOT NULL,
    price VARCHAR(15) NOT NULL,
    order_type VARCHAR(15) NOT NULL CHECK (order_type IN ('Online', 'In-store')),
    stock TINYINT NOT NULL,
    sort TINYINT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_SpecificShoes_GenericShoes FOREIGN KEY (generic_shoes_id) REFERENCES Generic_Shoes(id)
) ON [PRIMARY]
GO

-- Create Shoes Images Table
CREATE TABLE Shoes_Images (
    id INT PRIMARY KEY IDENTITY(1,1),
    image VARCHAR(50) NULL,
    [order] TINYINT NOT NULL,
    specific_shoes_id INT NOT NULL,
    CONSTRAINT FK_ShoesImages_SpecificShoes FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id)
) ON [PRIMARY]
GO

-- Create Sizes Specific Shoes Table (Join Table)
CREATE TABLE Sizes_Specific_Shoes (
    id INT PRIMARY KEY IDENTITY(1,1),
    size_id INT NOT NULL,
    specific_shoes_id INT NOT NULL,
    CONSTRAINT FK_SizesSpecificShoes_Sizes FOREIGN KEY (size_id) REFERENCES Sizes(id),
    CONSTRAINT FK_SizesSpecificShoes_SpecificShoes FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id)
) ON [PRIMARY]
GO

-- Create Colors Specific Shoes Table (Join Table)
CREATE TABLE Colors_Specific_Shoes (
    id INT PRIMARY KEY IDENTITY(1,1),
    color_id INT NOT NULL,
    specific_shoes_id INT NOT NULL,
    CONSTRAINT FK_ColorsSpecificShoes_Colors FOREIGN KEY (color_id) REFERENCES Colors(id),
    CONSTRAINT FK_ColorsSpecificShoes_SpecificShoes FOREIGN KEY (specific_shoes_id) REFERENCES Specific_Shoes(id)
) ON [PRIMARY]
GO

-- Create Categories Generic Shoes Table (Join Table)
CREATE TABLE Categories_Generic_Shoes (
    id INT PRIMARY KEY IDENTITY(1,1),
    category_id INT NOT NULL,
    generic_shoes_id INT NOT NULL,
    CONSTRAINT FK_CategoriesGenericShoes_Categories FOREIGN KEY (category_id) REFERENCES Categories(id),
    CONSTRAINT FK_CategoriesGenericShoes_GenericShoes FOREIGN KEY (generic_shoes_id) REFERENCES Generic_Shoes(id)
) ON [PRIMARY]
GO

-- Create Products Table
CREATE TABLE Products (
    ID INT PRIMARY KEY,
    Name VARCHAR(255),
    Price DECIMAL(10, 2),
    Sizes VARCHAR(50),
    Colors VARCHAR(50),
    Rating DECIMAL(2, 1),
    Review INT,
    Likes INT,
    Info VARCHAR(255),
    Policy VARCHAR(255),
    Delivery VARCHAR(255),
    Benefit VARCHAR(255),
    ServiceHotline VARCHAR(200),
    CategoryID INT,
    CONSTRAINT PK_Products PRIMARY KEY CLUSTERED (ID ASC),
    CONSTRAINT FK_Products_Categories FOREIGN KEY (CategoryID) REFERENCES Categories(id)
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

-- Create Carts Table
CREATE TABLE Carts (
    username NVARCHAR(50) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NULL,
    CONSTRAINT PK_Carts PRIMARY KEY CLUSTERED (username ASC, product_id ASC),
    CONSTRAINT FK_Carts_Accounts FOREIGN KEY (username) REFERENCES Accounts (Username),
    CONSTRAINT FK_Carts_Products FOREIGN KEY (product_id) REFERENCES Products (ID)
) ON [PRIMARY]
GO

-- Create Order_Details Table
CREATE TABLE Order_Details (
    Id BIGINT IDENTITY(1,1) NOT NULL,
    order_id BIGINT NULL,
    product_id INT NULL,
    Price FLOAT NULL,
    Quantity INT NULL,
    CONSTRAINT PK_OrderDetails PRIMARY KEY CLUSTERED (Id ASC),
    CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (order_id) REFERENCES Orders (Id),
    CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (product_id) REFERENCES Products (ID)
) ON [PRIMARY]
GO

-- Create Orders Table
CREATE TABLE Orders (
    Id BIGINT IDENTITY(1,1) NOT NULL,
    Username NVARCHAR(50) NOT NULL,
    create_date DATE NOT NULL,
    Address NVARCHAR(100) NOT NULL,
    total FLOAT NOT NULL,
    status BIT NOT NULL,
    CONSTRAINT PK_Orders PRIMARY KEY CLUSTERED (Id ASC),
    CONSTRAINT FK_Orders_Accounts FOREIGN KEY (Username) REFERENCES Accounts (Username)
) ON [PRIMARY]
GO


INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Photo], [Activated], [Admin]) VALUES (N'NV001', N'123', N'Lê Xuân Tường', N'tuonglxps20748@fpt.edu.vn', N'tuong.jpg', 1, 1)
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Photo], [Activated], [Admin]) VALUES (N'NV002', N'123', N'Nguyễn Anh Khoa', N'nhattqps12345@fpt.edu.vn', N'khoa.jpg', 1, 1)
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Photo], [Activated], [Admin]) VALUES (N'NV003', N'123', N'Lê Thuận Phát', N'loanvntps54321@fpt.edu.vn', N'phat.jpg', 1, 0)
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Photo], [Activated], [Admin]) VALUES (N'NV004', N'123', N'Nguyễn Ngọc Phát', N'ngathps12312@fpt.edu.vn', N'phat.jpg', 1, 0)
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Photo], [Activated], [Admin]) VALUES (N'NV005', N'123', N'Tài Nguyễn', N'phitmps32112@fpt.edu.vn', N'tai.jpg', 1, 0)
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Photo], [Activated], [Admin]) VALUES (N'NV006', N'123', N'Trung Hiếu', N'thangnccps34567@fpt.edu.vn', N'hieu.jpg', 1, 0)
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Photo], [Activated], [Admin]) VALUES (N'Admin123', N'123456', N'Admin', N'adminadmin123@fpt.edu.vn', N'admin.jpg', 1, 1)
GO

INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (1, 'Giày Cao Gót MWC 4403', 235000, 'N/A', 'N/A', 0, 0, 0, 'Giày Cao Gót Bít Mũi Bít Gót, Cao Gót Nữ Đế Vuông Cao 9 Phân Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (2, 'Giày Cao Gót MWC G014', 235000, 'N/A', 'N/A', 0, 0, 0, 'Cao Gót Quai Mãnh Đính Đá Sang Chảnh, Giày Cao Gót Đế Trụ Cao 7cm Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (3, 'Giày Sandal Cao Gót MWC G021', 235000, 'N/A', 'N/A', 0, 0, 0, 'Sandal Cao Gót Nữ Quai Ngang Siêu Bền Đẹp, Sandal Nữ Đế Vuông Cao 7cm Kiểu Dáng Basic Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (4, 'Giày Cao Gót MWC G085', 235000, 'N/A', 'N/A', 0, 0, 0, 'Giày Cao Gót Nữ Quai Mảnh Đính Đá Sang Chảnh, Cao Gót Đế Trụ Nhỏ Thanh Lịch, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 100),
INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (5, 'Dép Nữ MWC 8444', 195000, 'N/A', 'N/A', 0, 0, 0, 'Dép Sandal Cross Nữ Gắn Sticker Siêu Bền Đẹp, Dép Nữ Đúc Nguyên Khối Năng Động, Trẻ Trung, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 101),
INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (6, 'Giày Sandal Nữ MWC E110', 195000, 'N/A', 'N/A', 0, 0, 0, 'Sandal Nữ Quai Chéo Đính Hình Bươm Bướm Nhỏ Xinh, Sandal Nữ Đế Bánh Mì Siêu Hack Dáng, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 101),
INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (7, 'Giày Sandal Nữ MWC E122', 275000, 'N/A', 'N/A', 0, 0, 0, 'Sandal Nữ Quai Mảnh Chéo Thanh Lịch, Sandal Nữ Đế Bánh Mì Siêu Bền Đẹp Hack Dáng, Thời Trang.', 'N/A', 'N/A', 'N/A', 'N/A', 101),
INSERT INTO [dbo].[Products] ([ID], [Name], [Price], [Sizes], [Colors], [Rating], [Review], [Likes], [Info], [Policy], [Delivery], [Benefit], [ServiceHotline], [CategoryID]) VALUES (8, 'Dép Nữ MWC 8475', 79000, 'N/A', 'N/A', 0, 0, 0, 'Dép Kẹp Nữ Siêu Cute, Dép Nữ Cao Su Dẻo Đúc Nguyên Khối Êm Nhẹ Đi Chơi, Đi Biển Phong Cách Trẻ Trung.', 'N/A', 'N/A', 'N/A', 'N/A', 101);
