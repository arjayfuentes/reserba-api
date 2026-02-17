CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE item (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       price DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
                       image_url TEXT,
                       category VARCHAR(100) NOT NULL,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE items RENAME TO item;



-- Inserting multiple items at once
INSERT INTO item (name, description, price, image_url, category)
VALUES
    ('Gaming Mouse', 'Wireless ergonomic gaming mouse.', 45.50, 'https://example.com/mouse.jpg', 'Electronics'),
    ('USB-C Hub', '7-in-1 adapter for laptops.', 29.99, 'https://example.com/hub.jpg', 'Accessories'),
    ('Monitor Stand', 'Adjustable aluminum riser.', 34.99, 'https://example.com/stand.jpg', 'Office Supplies'),
    ('Summit Peak Tent', '4-season waterproof mountaineering tent.', 299.00, 'https://example.com/tent.jpg', 'Outdoor & Adventure'),
    ('HydroFlow Pack', 'Hydration backpack for long-distance trail running.', 85.00, 'https://example.com/pack.jpg', 'Outdoor & Adventure'),
    ('Bamboo Paper Ream', '500 sheets of eco-friendly recycled A4 paper.', 12.50, 'https://example.com/paper.jpg', 'Office Supplies'),
    ('EV Home Charger', 'Level 2 fast-charging station for electric vehicles.', 450.00, 'https://example.com/charger.jpg', 'Automotive'),
    ('Microfiber Towels', 'Pack of 12 scratch-free detailing cloths.', 15.99, 'https://example.com/towels.jpg', 'Automotive'),
    ('Ergo-Grip Pen Set', '12-pack of smooth-writing retractable gel pens.', 18.00, 'https://example.com/pens.jpg', 'Office Supplies');



-- Appending Food items to your insertion script
INSERT INTO item (name, description, price, image_url, category)
VALUES
    ('Artisanal Sourdough', 'Hand-baked organic loaf with sea salt.', 6.50, 'https://example.com/bread.jpg', 'Food'),
    ('Espresso Beans', '1kg dark roast Arabica from Ethiopia.', 24.99, 'https://example.com/coffee.jpg', 'Food'),
    ('Himalayan Salt', 'Fine pink mineral salt, 500g shaker.', 4.25, 'https://example.com/salt.jpg', 'Food'),
    ('Cold Pressed Olive Oil', 'Extra virgin olive oil from Tuscany.', 18.00, 'https://example.com/oil.jpg', 'Food'),
    ('Maple Syrup', 'Grade A pure Canadian maple syrup, 250ml.', 12.00, 'https://example.com/syrup.jpg', 'Food'),
    ('Quinoa Trio', 'Organic white, red, and black quinoa mix.', 7.95, 'https://example.com/quinoa.jpg', 'Food');