-- Create the users table
CREATE TABLE user_info (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           username VARCHAR(255) NOT NULL,
                           firstname VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL,
                           role VARCHAR(50) NOT NULL
);

-- Create the product table
CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         code VARCHAR(255),
                         name VARCHAR(255),
                         description VARCHAR(255),
                         image VARCHAR(255),
                         category VARCHAR(255),
                         price DOUBLE,
                         quantity INT,
                         internal_reference VARCHAR(255),
                         shell_id BIGINT,
                         inventory_status VARCHAR(255) CHECK (inventory_status IN ('INSTOCK', 'LOWSTOCK', 'OUTOFSTOCK')),
                         rating INT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the cart_items table with foreign key to users and products
CREATE TABLE cart_item (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT NOT NULL,
                           product_id BIGINT NOT NULL,
                           quantity INT NOT NULL,
                           FOREIGN KEY(user_id) REFERENCES user_info(id) ON DELETE CASCADE,
                           FOREIGN KEY(product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Create the wishlist_items table with foreign key to users and products
CREATE TABLE wishlist_item (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               FOREIGN KEY(user_id) REFERENCES user_info(id) ON DELETE CASCADE,
                               FOREIGN KEY(product_id) REFERENCES product(id) ON DELETE CASCADE
);

-- Inserting sample data into the user_info table
INSERT INTO user_info (username, firstname, email, password, role)
VALUES
    ('adminuser', 'Admin', 'adminuser@admin.com', 'hashedpassword', 'ADMIN'),
    ('john_doe', 'John', 'john.doe@example.com', 'hashedpassword456', 'USER');

-- Inserting sample data into the product table
INSERT INTO product (code, name, description, image, category, price, quantity, internal_reference, shell_id, inventory_status, rating, created_at, updated_at)
VALUES
    ('P001', 'Sample Product 1', 'Description 1', 'image1.png', 'Category1', 99.99, 10, 'REF001', 1, 'INSTOCK', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('P002', 'Sample Product 2', 'Description 2', 'image2.png', 'Category2', 49.99, 5, 'REF002', 2, 'LOWSTOCK', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserting sample data into the cart_item table
INSERT INTO cart_item (user_id, product_id, quantity)
VALUES
    (1, 1, 2),  -- user_id = 1 (adminuser) and product_id = 1 (Sample Product 1)
    (2, 2, 1);  -- user_id = 2 (john_doe) and product_id = 2 (Sample Product 2)

-- Inserting sample data into the wishlist_item table
INSERT INTO wishlist_item (user_id, product_id)
VALUES
    (1, 1),  -- user_id = 1 (adminuser) and product_id = 1 (Sample Product 1)
    (2, 2);  -- user_id = 2 (john_doe) and product_id = 2 (Sample Product 2)
