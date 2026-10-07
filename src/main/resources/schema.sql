CREATE TABLE IF NOT EXISTS users (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name     VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    phone         VARCHAR(255),
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL,
    CONSTRAINT uk_users_email UNIQUE (email)
);