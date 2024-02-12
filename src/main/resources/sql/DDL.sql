CREATE TABLE store_owner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
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
    FOREIGN KEY (owner_id) REFERENCES store_owner(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product_name_initial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seq INT(10) NOT NULL,
    word VARCHAR(50) NOT NULL,
    initial VARCHAR(50) NOT NULL,
    product_id BIGINT NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
