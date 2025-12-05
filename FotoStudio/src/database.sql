CREATE DATABASE IF NOT EXISTS bonas_studio;
USE bonas_studio;

CREATE TABLE studio (
    studio_id INT PRIMARY KEY AUTO_INCREMENT,
    capacity INT NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE package (
    package_id INT PRIMARY KEY AUTO_INCREMENT,
    studio_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    min_person INT NOT NULL,
    max_person INT NOT NULL,
    duration INT NOT NULL,
    FOREIGN KEY (studio_id) REFERENCES studio(studio_id)
);

CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address VARCHAR(255) NOT NULL, 
    full_name VARCHAR(255) NOT NULL,
    role INT NOT NULL DEFAULT 2 -- Admin = 0, Pegawai = 1, User = 2
);

CREATE TABLE reservation (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL, 
    package_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    reservation_time TIME NOT NULL,
    status_payment VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, CONFIRMED, CANCELED
    total_price DECIMAL(10, 2) NOT NULL,
    is_done BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (package_id) REFERENCES package(package_id)
);