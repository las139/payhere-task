CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME
);

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    price DECIMAL(20, 2) NOT NULL,
    cost DECIMAL(20, 2) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    barcode VARCHAR(255) NOT NULL UNIQUE,
    expiration_date DATE NOT NULL,
    size VARCHAR(10) NOT NULL,
    owner_id BIGINT NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    FOREIGN KEY (owner_id) REFERENCES user(id)
);
