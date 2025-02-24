-- Insert SSO Providers
INSERT INTO `Sso_Providers` (`sso_provider`) VALUES
                                                 ('Google'),
                                                 ('Facebook'),
                                                 ('Apple');

-- Insert Business Accounts
INSERT INTO `Bussiness_Accounts` (`company_ABN`, `company_name`, `logo_url`, `is_active`) VALUES
                                                                                              ('12345678901', 'Tech Corp', 'https://example.com/logos/techcorp.png', TRUE),
                                                                                              ('98765432109', 'Food Delivery Co', 'https://example.com/logos/foodco.png', TRUE),
                                                                                              ('45678901234', 'Logistics Ltd', 'https://example.com/logos/logistics.png', FALSE);

-- Insert Users (including riders and admins)
INSERT INTO `Users`
(`username`, `first_name`, `last_name`, `date_of_birth`, `gender`, `email`, `email_verified`,
 `phone`, `phone_verified`, `password_hash`, `account_type`, `account_status`) VALUES
-- Admin Users
('admin1', 'John', 'Admin', '1990-01-15', 'MALE', 'admin1@example.com', TRUE, '+61412345678', TRUE, 'hashed_password_1', 'STANDARD', 'ACTIVE'),
('admin2', 'Sarah', 'Manager', '1988-03-20', 'FEMALE', 'admin2@example.com', TRUE, '+61412345679', TRUE, 'hashed_password_2', 'STANDARD', 'ACTIVE'),

-- Regular Users
('customer1', 'Mike', 'Brown', '1992-05-10', 'MALE', 'customer1@example.com', TRUE, '+61412345680', TRUE, 'hashed_password_3', 'STANDARD', 'ACTIVE'),
('customer2', 'Lisa', 'White', '1995-07-22', 'FEMALE', 'customer2@example.com', TRUE, '+61412345681', TRUE, 'hashed_password_4', 'STANDARD', 'ACTIVE'),
('customer3', 'Tom', 'Green', '1991-12-03', 'MALE', 'customer3@example.com', FALSE, '+61412345682', FALSE, 'hashed_password_5', 'STANDARD', 'PENDING'),

-- Rider Users
('rider1', 'James', 'Wilson', '1993-08-15', 'MALE', 'rider1@example.com', TRUE, '+61412345683', TRUE, 'hashed_password_6', 'STANDARD', 'ACTIVE'),
('rider2', 'Emma', 'Davis', '1994-09-20', 'FEMALE', 'rider2@example.com', TRUE, '+61412345684', TRUE, 'hashed_password_7', 'STANDARD', 'ACTIVE'),
('rider3', 'Alex', 'Smith', '1990-11-25', 'OTHER', 'rider3@example.com', TRUE, '+61412345685', TRUE, 'hashed_password_8', 'STANDARD', 'ACTIVE');

-- Insert States
INSERT INTO `States` (`name`, `code`) VALUES
                                          ('New South Wales', 'NSW'),
                                          ('Victoria', 'VIC'),
                                          ('Queensland', 'QLD'),
                                          ('Western Australia', 'WA');

-- Insert Service Areas
INSERT INTO `Service_Areas` (`name`, `code`, `is_active`, `state_id`) VALUES
                                                                          ('Sydney CBD', 'SYD-CBD', TRUE, 1),
                                                                          ('Melbourne Central', 'MEL-CEN', TRUE, 2),
                                                                          ('Brisbane City', 'BRI-CITY', TRUE, 3),
                                                                          ('Perth Metro', 'PER-MET', FALSE, 4);

-- Insert Riders
INSERT INTO `Riders`
(`user_id`, `latitude`, `longitude`, `is_online`, `status`, `emergency_contact_first_name`,
 `emergency_contact_last_name`, `emergency_contact_phone_number`, `bank_name`, `bsb_number`,
 `account_number`, `signature`, `profile_completed`, `passed_quiz`) VALUES
                                                                        (6, -33.8688, 151.2093, TRUE, 'ACTIVE', 'Jane', 'Wilson', '+61487654321', 'CommBank', '062-001', '12345678', 'signature_1', TRUE, TRUE),
                                                                        (7, -37.8136, 144.9631, FALSE, 'ACTIVE', 'Mark', 'Davis', '+61487654322', 'ANZ', '063-002', '23456789', 'signature_2', TRUE, TRUE),
                                                                        (8, -27.4705, 153.0260, TRUE, 'ACTIVE', 'Sarah', 'Smith', '+61487654323', 'Westpac', '064-003', '34567890', 'signature_3', TRUE, TRUE);

-- Insert Vehicles
INSERT INTO `Vehicles`
(`rider_id`, `is_current_vehicle`, `vehicle_type`, `model_year`, `manufacturer`, `transport_photo`) VALUES
                                                                                                        (1, TRUE, 'BICYCLE', '2022', 'Giant', 'bike1.jpg'),
                                                                                                        (2, TRUE, 'MOTORBIKE', '2021', 'Honda', 'motorbike1.jpg'),
                                                                                                        (3, TRUE, 'CAR', '2020', 'Toyota', 'car1.jpg');

-- Insert Orders
INSERT INTO `Orders`
(`order_number`, `customer_id`, `rider_id`, `total_price`, `total_distance`, `vehicle_type`,
 `order_status`, `payment_status`, `customer_full_name`, `customer_phone_number`) VALUES
                                                                                      ('ORD-2024-001', 3, 1, 25.50, 3.2, 'BICYCLE', 'DELIVERED', 'COMPLETED', 'Mike Brown', '+61412345680'),
                                                                                      ('ORD-2024-002', 4, 2, 35.75, 4.5, 'MOTORBIKE', 'DELIVERING', 'COMPLETED', 'Lisa White', '+61412345681'),
                                                                                      ('ORD-2024-003', 5, 3, 45.90, 5.8, 'CAR', 'MATCHED', 'PENDING', 'Tom Green', '+61412345682'),
                                                                                      ('ORD-2024-004', 3, 1, 28.50, 3.5, 'BICYCLE', 'STANDBY', 'PENDING', 'Mike Brown', '+61412345680');

-- Insert Destinations
INSERT INTO `Destinations`
(`destination_latitude`, `destination_longitude`, `destination_address_text`, `sequence`,
 `recipient_phone_number`, `price`, `estimated_time`, `order_id`, `status`) VALUES
                                                                                (-33.8688, 151.2093, '123 George St, Sydney', 1, '+61412345690', 15.50, 20, 1, 'COMPLETED'),
                                                                                (-33.8714, 151.2044, '456 Pitt St, Sydney', 2, '+61412345691', 10.00, 15, 1, 'COMPLETED'),
                                                                                (-37.8136, 144.9631, '789 Collins St, Melbourne', 1, '+61412345692', 35.75, 30, 2, 'PENDING'),
                                                                                (-27.4705, 153.0260, '321 Queen St, Brisbane', 1, '+61412345693', 45.90, 40, 3, 'PENDING');

-- Insert Reviews
INSERT INTO `Reviews` (`user_id`, `rider_id`, `order_id`, `review`, `rate`) VALUES
                                                                                (3, 1, 1, 'Great service, very prompt delivery!', 5),
                                                                                (4, 2, 2, 'Professional and courteous rider', 4);

-- Insert Size And Weight Descriptions
INSERT INTO `Size_And_Weight_Descriptions`
(`size`, `size_description`, `weight`, `is_latest`) VALUES
                                                        ('SMALL', 'Fits in a small bag', '0-5kg', TRUE),
                                                        ('MEDIUM', 'Fits in a medium box', '5-15kg', TRUE),
                                                        ('LARGE', 'Requires large vehicle space', '15-25kg', TRUE);

-- Insert Items
INSERT INTO `Items`
(`name`, `size_weight_description_id`, `item_classification`, `photo_urls`, `destination_id`) VALUES
                                                                                                  ('Document Package', 1, '["FRAGILE"]', '["doc1.jpg"]', 1),
                                                                                                  ('Food Delivery', 1, '["LOW_TEMPERATURE"]', '["food1.jpg"]', 2),
                                                                                                  ('Electronics Package', 2, '["FRAGILE", "HEAVY_ITEM"]', '["electronics1.jpg"]', 3),
                                                                                                  ('Furniture Item', 3, '["HEAVY_ITEM"]', '["furniture1.jpg"]', 4);

-- Insert Transport Basic Prices
INSERT INTO `Transport_Basic_Prices`
(`vehicle_type`, `basic_price`, `price_per_minute`, `pickuptime_asap_price`,
 `pickuptime_2hours_price`, `pickuptime_today_price`, `pickuptime_otherday_price`, `is_latest`) VALUES
                                                                                                    ('BICYCLE', 10.00, 0.50, 5.00, 3.00, 2.00, 1.00, true),
                                                                                                    ('MOTORBIKE', 15.00, 0.75, 7.00, 5.00, 3.00, 2.00, true),
                                                                                                    ('CAR', 20.00, 1.00, 10.00, 7.00, 5.00, 3.00, true);

-- Insert Peak Time Rates
INSERT INTO `Peak_Time_Rates`
(`is_weekend`, `start_time`, `end_time`, `rate`, `is_latest`) VALUES
                                                                  (FALSE, '07:00', '09:00', 1.5, TRUE),
                                                                  (FALSE, '17:00', '19:00', 1.5, TRUE),
                                                                  (TRUE, '12:00', '15:00', 1.3, TRUE);

-- Insert Groups
INSERT INTO `Groups` (`name`, `description`, `group_type`) VALUES
                                                               ('Administrators', 'System administrators with full access', 'PERMISSION'),
                                                               ('Riders', 'Delivery personnel group', 'PERMISSION'),
                                                               ('Customers', 'Regular customers', 'PERMISSION'),
                                                               ('Special Events', 'Special promotion events group', 'EVENT');

-- Insert Group Members
INSERT INTO `Group_Members` (`group_id`, `user_id`) VALUES
                                                        (1, 1), -- Admin1 in Administrators group
                                                        (1, 2), -- Admin2 in Administrators group
                                                        (2, 6), -- Rider1 in Riders group
                                                        (2, 7), -- Rider2 in Riders group
                                                        (2, 8), -- Rider3 in Riders group
                                                        (3, 3), -- Customer1 in Customers group
                                                        (3, 4), -- Customer2 in Customers group
                                                        (3, 5); -- Customer3 in Customers group