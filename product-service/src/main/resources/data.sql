-- Enable UUID generation extension (safe to run multiple times)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO products (id, name, description, price, status, added_at, updated_at) VALUES
(gen_random_uuid(), 'iPhone 14', 'Apple smartphone with A15 chip', 799.99, 'InStock', now(), now()),
(gen_random_uuid(), 'Galaxy S22', 'Samsung flagship phone', 749.99, 'InStock', now(), now()),
(gen_random_uuid(), 'Pixel 7', 'Google’s AI-powered phone', 699.99, 'OutStock', now(), now()),
(gen_random_uuid(), 'AirPods Pro', 'Apple wireless earbuds with noise cancellation', 249.99, 'InStock', now(), now()),
(gen_random_uuid(), 'MacBook Pro', 'Apple M2 MacBook Pro 13"', 1299.00, 'OutStock', now(), now()),
(gen_random_uuid(), 'Dell XPS 13', 'Lightweight Windows ultrabook', 999.00, 'InStock', now(), now()),
(gen_random_uuid(), 'Sony WH-1000XM5', 'Top-tier noise cancelling headphones', 399.00, 'InStock', now(), now()),
(gen_random_uuid(), 'iPad Air', 'Apple’s mid-range tablet', 599.00, 'OutStock', now(), now()),
(gen_random_uuid(), 'Kindle Paperwhite', 'E-reader with backlit screen', 139.99, 'InStock', now(), now()),
(gen_random_uuid(), 'GoPro Hero 11', 'Action camera for adventures', 499.99, 'InStock', now(), now()),
(gen_random_uuid(), 'Logitech MX Master 3', 'Ergonomic productivity mouse', 99.99, 'InStock', now(), now()),
(gen_random_uuid(), 'Razer Blade 16', 'Gaming laptop with RTX 4070', 2199.00, 'OutStock', now(), now()),
(gen_random_uuid(), 'Fitbit Charge 5', 'Fitness tracker with heart rate monitor', 179.00, 'InStock', now(), now()),
(gen_random_uuid(), 'Sony PS5', 'PlayStation 5 Disc Edition', 499.00, 'InStock', now(), now()),
(gen_random_uuid(), 'Xbox Series X', 'Microsoft next-gen console', 499.00, 'OutStock', now(), now()),
(gen_random_uuid(), 'Apple Watch SE', 'Affordable Apple Watch', 279.00, 'InStock', now(), now()),
(gen_random_uuid(), 'Nikon D5600', 'DSLR Camera for beginners', 649.00, 'InStock', now(), now()),
(gen_random_uuid(), 'Google Nest Hub', 'Smart display with Assistant', 99.00, 'InStock', now(), now()),
(gen_random_uuid(), 'Echo Dot 5th Gen', 'Amazon Alexa smart speaker', 49.00, 'OutStock', now(), now()),
(gen_random_uuid(), 'JBL Flip 6', 'Portable waterproof Bluetooth speaker', 129.00, 'InStock', now(), now());