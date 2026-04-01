-- Sample Customers
INSERT INTO customers (name, email, tier, loyalty_points, created_at) VALUES ('Alice Johnson', 'alice@example.com', 'GOLD', 5500, NOW());
INSERT INTO customers (name, email, tier, loyalty_points, created_at) VALUES ('Bob Smith', 'bob@example.com', 'SILVER', 1200, NOW());
INSERT INTO customers (name, email, tier, loyalty_points, created_at) VALUES ('Carol White', 'carol@example.com', 'BRONZE', 300, NOW());
INSERT INTO customers (name, email, tier, loyalty_points, created_at) VALUES ('David Lee', 'david@example.com', 'SILVER', 2800, NOW());
INSERT INTO customers (name, email, tier, loyalty_points, created_at) VALUES ('Eva Martinez', 'eva@example.com', 'GOLD', 7200, NOW());

-- Sample Offers
INSERT INTO offers (title, description, discount_percentage, min_points_required, status, valid_from, valid_to, target_tier)
    VALUES ('Gold Member Exclusive', '20% off on all purchases', 20.0, 5000, 'ACTIVE', CURRENT_DATE, DATEADD('YEAR', 1, CURRENT_DATE), 'GOLD');
INSERT INTO offers (title, description, discount_percentage, min_points_required, status, valid_from, valid_to, target_tier)
    VALUES ('Silver Rewards Boost', '10% off on electronics', 10.0, 1000, 'ACTIVE', CURRENT_DATE, DATEADD('YEAR', 1, CURRENT_DATE), 'SILVER');
INSERT INTO offers (title, description, discount_percentage, min_points_required, status, valid_from, valid_to, target_tier)
    VALUES ('Welcome Bronze Offer', '5% off on first purchase', 5.0, 0, 'ACTIVE', CURRENT_DATE, DATEADD('YEAR', 1, CURRENT_DATE), 'BRONZE');
INSERT INTO offers (title, description, discount_percentage, min_points_required, status, valid_from, valid_to, target_tier)
    VALUES ('Summer Sale Gold', '25% off summer collection', 25.0, 5000, 'ACTIVE', CURRENT_DATE, DATEADD('YEAR', 1, CURRENT_DATE), 'GOLD');
INSERT INTO offers (title, description, discount_percentage, min_points_required, status, valid_from, valid_to, target_tier)
    VALUES ('Expired Deal', '15% off - expired', 15.0, 0, 'EXPIRED', DATEADD('YEAR', -2, CURRENT_DATE), DATEADD('YEAR', -1, CURRENT_DATE), 'BRONZE');

-- Sample Loyalty Accounts
INSERT INTO loyalty_accounts (customer_id, total_points, available_points, tier, last_updated) VALUES (1, 5500, 5500, 'GOLD', NOW());
INSERT INTO loyalty_accounts (customer_id, total_points, available_points, tier, last_updated) VALUES (2, 1200, 1200, 'SILVER', NOW());
INSERT INTO loyalty_accounts (customer_id, total_points, available_points, tier, last_updated) VALUES (3, 300, 300, 'BRONZE', NOW());
INSERT INTO loyalty_accounts (customer_id, total_points, available_points, tier, last_updated) VALUES (4, 2800, 2800, 'SILVER', NOW());
INSERT INTO loyalty_accounts (customer_id, total_points, available_points, tier, last_updated) VALUES (5, 7200, 7200, 'GOLD', NOW());
