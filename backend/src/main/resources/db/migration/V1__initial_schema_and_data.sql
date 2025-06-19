-- Drop tables if they exist
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS shops;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create shops table
CREATE TABLE shops (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    owner_id UUID NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    description TEXT,
    open_hours VARCHAR(100),
    latitude DOUBLE,
    longitude DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Create products table
CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    image_url LONGTEXT,
    shop_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shop_id) REFERENCES shops(id)
);

-- Insert sample users (password is 'password123' encrypted with BCrypt)
INSERT INTO users (id, username, email, password, role) VALUES
('11111111-1111-1111-1111-111111111111', 'admin', 'admin@example.com', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'ADMIN'),
('22222222-2222-2222-2222-222222222222', 'shopowner1', 'shop1@example.com', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'SHOP_OWNER'),
('33333333-3333-3333-3333-333333333333', 'shopowner2', 'shop2@example.com', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'SHOP_OWNER');

-- Insert sample shops
INSERT INTO shops (id, name, owner_id, owner_name, email, phone, address, city, state, zip_code, description, open_hours, latitude, longitude) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Tech Gadgets Store', '22222222-2222-2222-2222-222222222222', 'John Doe', 'tech@example.com', '123-456-7890', '123 Tech St', 'San Francisco', 'CA', '94105', 'Your one-stop shop for all tech gadgets', '9:00 AM - 6:00 PM', 37.7749, -122.4194),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Fashion Boutique', '33333333-3333-3333-3333-333333333333', 'Jane Smith', 'fashion@example.com', '987-654-3210', '456 Fashion Ave', 'New York', 'NY', '10001', 'Trendy fashion for all seasons', '10:00 AM - 8:00 PM', 40.7128, -74.0060);

-- Insert sample products
INSERT INTO products (id, name, category, price, stock, image_url, shop_id) VALUES
('11111111-1111-1111-1111-111111111111', 'iPhone 13', 'Electronics', 999.99, 50, 'https://example.com/iphone13.jpg', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
('22222222-2222-2222-2222-222222222222', 'MacBook Pro', 'Electronics', 1299.99, 30, 'https://example.com/macbook.jpg', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
('33333333-3333-3333-3333-333333333333', 'Summer Dress', 'Clothing', 49.99, 100, 'https://example.com/dress.jpg', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
('44444444-4444-4444-4444-444444444444', 'Designer Handbag', 'Accessories', 299.99, 25, 'https://example.com/handbag.jpg', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'); 