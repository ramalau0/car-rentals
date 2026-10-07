INSERT INTO users (full_name, email, phone, password_hash, role)
SELECT 'Admin User', 'admin@rental.com', '0800000001', '$2a$10$Osu5VJFB09JidFaWdWX3hOCiD5765E31cOERcSbqGDBkv6HnIFMIu', 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@rental.com');

INSERT INTO users (full_name, email, phone, password_hash, role)
SELECT 'Thabo Mokoena', 'user@rental.com', '0821234567', '$2a$10$Osu5VJFB09JidFaWdWX3hOCiD5765E31cOERcSbqGDBkv6HnIFMIu', 'USER'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'user@rental.com');

INSERT INTO users (full_name, email, phone, password_hash, role)
SELECT 'Sipho Dlamini', 'user2@rental.com', '0839876543', '$2a$10$Osu5VJFB09JidFaWdWX3hOCiD5765E31cOERcSbqGDBkv6HnIFMIu', 'USER'
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'user2@rental.com');

INSERT INTO vehicle (make, model, manufacture_year, registration_number, daily_rate, status)
SELECT 'Toyota', 'Corolla', 2022, 'CA123456', 450.00, 'AVAILABLE'
    WHERE NOT EXISTS (SELECT 1 FROM vehicle WHERE registration_number = 'CA123456');

INSERT INTO vehicle (make, model, manufacture_year, registration_number, daily_rate, status)
SELECT 'VW', 'Polo', 2021, 'GP654321', 380.00, 'AVAILABLE'
    WHERE NOT EXISTS (SELECT 1 FROM vehicle WHERE registration_number = 'GP654321');