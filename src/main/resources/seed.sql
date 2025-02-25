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
                                                                  (FALSE, '17:00', '19:00', 1.5, FALSE),
                                                                  (TRUE, '12:00', '15:00', 1.3, FALSE);

-- Insert Groups
INSERT INTO `Ma_Groups` (`name`, `description`, `group_type`) VALUES
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

-- Insert Permissions
INSERT INTO `Permissions` (`permission_name`, `description`) VALUES
                                                                 ('manage_users', 'Can create, update, and delete user accounts'),
                                                                 ('manage_riders', 'Can manage rider accounts and assignments'),
                                                                 ('manage_orders', 'Can view and manage all orders'),
                                                                 ('view_reports', 'Can access and download system reports'),
                                                                 ('manage_pricing', 'Can update pricing and commission rates'),
                                                                 ('manage_content', 'Can manage FAQs and announcements');

-- Insert Group Permissions
INSERT INTO `Group_Permissions` (`group_id`, `permission_id`) VALUES
-- Administrators get all permissions
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
-- Riders get specific permissions
(2, 3),
-- Customers get basic permissions
(3, 3);

-- Insert Billing Addresses
INSERT INTO `Billing_Address`
(`billing_email`, `billing_street_address`, `billing_state_id`, `billing_postcode`,
 `billing_suburb`, `user_id`) VALUES
                                  ('customer1@example.com', '123 Customer St', 1, '2000', 'Sydney', 3),
                                  ('customer2@example.com', '456 Client Rd', 2, '3000', 'Melbourne', 4),
                                  ('customer3@example.com', '789 User Ave', 3, '4000', 'Brisbane', 5);

-- Insert Delivery Details
INSERT INTO `Delivery_Details`
(`pickup_latitude`, `pickup_longitude`, `pickup_address_text`, `estimated_time`,
 `pickup_time`, `pickup_date_time`, `recipient_phone_number`, `recipient_name`,
 `pickup_photo_urls`, `order_id`) VALUES
                                      (-33.8650, 151.2094, '100 Pickup St, Sydney', 30, 'MORNING', '2024-02-24 09:00:00',
                                       '+61412345670', 'John Recipient', '["pickup1.jpg"]', 1),
                                      (-37.8140, 144.9633, '200 Collect Rd, Melbourne', 45, 'AFTERNOON', '2024-02-24 14:00:00',
                                       '+61412345671', 'Mary Receiver', '["pickup2.jpg"]', 2),
                                      (-27.4698, 153.0251, '300 Dispatch Ave, Brisbane', 35, 'EVENING', '2024-02-24 18:00:00',
                                       '+61412345672', 'Peter Collector', '["pickup3.jpg"]', 3);

-- Insert Note Delivery Details
INSERT INTO `Note_Delivery_Details`
(`note`, `photo_urls`, `delivery_detail_id`) VALUES
                                                 ('Please handle with care', '["note1.jpg"]', 1),
                                                 ('Ring doorbell twice', '["note2.jpg"]', 2),
                                                 ('Leave at reception if no answer', '["note3.jpg"]', 3);

-- Insert Note Destinations
INSERT INTO `Note_Destinations`
(`note`, `photo_urls`, `destination_id`) VALUES
                                             ('Place in safe spot if nobody home', '["dest_note1.jpg"]', 1),
                                             ('Signature required', '["dest_note2.jpg"]', 2),
                                             ('Call upon arrival', '["dest_note3.jpg"]', 3);

-- Insert Questions for Rider Quiz
INSERT INTO `Questions` (`question_text`, `description`) VALUES
                                                             ('What is the correct procedure for handling fragile items?', 'Safety procedures test'),
                                                             ('How do you handle customer complaints?', 'Customer service assessment'),
                                                             ('What should you do if the customer is not available?', 'Delivery protocol test');

-- Insert Question Options
INSERT INTO `Question_Options`
(`question_id`, `question_option`, `is_correct`) VALUES
                                                     (1, 'Handle roughly and quickly', FALSE),
                                                     (1, 'Handle with care and secure properly', TRUE),
                                                     (1, 'Leave items unattended', FALSE),
                                                     (2, 'Ignore the customer', FALSE),
                                                     (2, 'Listen and escalate to support if needed', TRUE),
                                                     (2, 'Argue with the customer', FALSE),
                                                     (3, 'Leave items unprotected', FALSE),
                                                     (3, 'Follow delivery instructions or contact support', TRUE),
                                                     (3, 'Return items immediately', FALSE);

-- Insert Rider Answers
INSERT INTO `Rider_Answers`
(`rider_id`, `option_id`, `quiz_key`, `is_correct`) VALUES
                                                        (1, 2, 'INITIAL_QUIZ', TRUE),
                                                        (1, 5, 'INITIAL_QUIZ', TRUE),
                                                        (1, 8, 'INITIAL_QUIZ', TRUE),
                                                        (2, 2, 'INITIAL_QUIZ', TRUE),
                                                        (2, 5, 'INITIAL_QUIZ', TRUE),
                                                        (2, 8, 'INITIAL_QUIZ', TRUE);

-- Insert Coupons
INSERT INTO `Coupons`
(`discount_type`, `discount_amount`, `discount_percentage`, `maximum_discount_amount`,
 `minimum_purchase_price`, `start_date`, `end_date`, `issued_to`, `how_user_was_added`,
 `code`, `created_by`) VALUES
                           ('FLATRATE', 10.00, NULL, 10.00, 50.00, '2024-02-24', '2024-03-24', 'GENERAL', 'MANUAL', 'WELCOME10', 1),
                           ('PERCENTAGE', NULL, 15.00, 20.00, 30.00, '2024-02-24', '2024-04-24', 'MEMBER', 'MANUAL', 'MEMBER15', 1);

-- Insert User Coupons
INSERT INTO `User_Coupons` (`coupon_id`, `user_id`) VALUES
                                                        (1, 3),
                                                        (1, 4),
                                                        (2, 5);

-- Insert FAQ
INSERT INTO `Faq` (`question`, `answer`, `is_for_rider`) VALUES
                                                             ('How do I track my delivery?', 'You can track your delivery through the app using your order number.', FALSE),
                                                             ('What happens if I am not home?', 'The rider will follow your delivery instructions or contact you.', FALSE),
                                                             ('How do I report an issue with my delivery?', 'Contact customer support through the app or website.', FALSE),
                                                             ('How do I update my availability?', 'Use the rider app to update your status and working hours.', TRUE),
                                                             ('What should I do if I cannot complete a delivery?', 'Contact support immediately for assistance.', TRUE);

-- Insert Advertisements
INSERT INTO `Advertisements` (`title`, `content`, `image_url`) VALUES
                                                                   ('Special Weekend Offer', 'Get 20% off on all deliveries this weekend!', 'ad1.jpg'),
                                                                   ('New Rider Promotion', 'Join our team and earn a $200 bonus!', 'ad2.jpg'),
                                                                   ('Holiday Season Deals', 'Special rates for holiday deliveries', 'ad3.jpg');

-- Insert User Favorite Address
INSERT INTO `User_Favorite_Address`
(`user_id`, `long_address`, `short_address`, `custom_address`, `nick_name`,
 `latitude`, `longitude`, `address_type`) VALUES
                                              (3, '123 Home Street, Sydney NSW 2000', '123 Home St', 'Home Sweet Home', 'My Place',
                                               -33.8688, 151.2093, 'HOME'),
                                              (3, '456 Work Road, Sydney NSW 2000', '456 Work Rd', 'Office Building', 'Work',
                                               -33.8650, 151.2094, 'WORK'),
                                              (4, '789 Home Avenue, Melbourne VIC 3000', '789 Home Ave', 'Apartment 5B', 'Home',
                                               -37.8136, 144.9631, 'HOME');

-- Insert Rider Payments
INSERT INTO `Rider_Payments`
(`rider_id`, `distance`, `price`, `payment_cycle`, `is_paid`) VALUES
                                                                  (1, 15.5, 45.75, '2024-02-24 00:00:00', FALSE),
                                                                  (2, 12.3, 36.90, '2024-02-24 00:00:00', FALSE),
                                                                  (3, 18.7, 56.10, '2024-02-24 00:00:00', FALSE);

-- Insert Driver Guides
INSERT INTO `Driver_Guides` (`file_url`, `description`, `is_important`) VALUES
                                                                            ('guide1.pdf', 'Safety Guidelines for Riders', TRUE),
                                                                            ('guide2.pdf', 'Customer Service Best Practices', TRUE),
                                                                            ('guide3.pdf', 'App Usage Tutorial', FALSE);

-- Insert Announcements
INSERT INTO `Announcements` (`title`, `content`, `image_url`) VALUES
                                                                  ('System Maintenance', 'Scheduled maintenance on March 1st, 2024', 'maintenance.jpg'),
                                                                  ('New Feature Release', 'Updated tracking system now available', 'feature.jpg'),
                                                                  ('Holiday Schedule', 'Modified operating hours during upcoming holidays', 'holiday.jpg');

-- Insert App Versions
INSERT INTO `App_Versions` (`app_name`, `update_type`, `version`) VALUES
                                                                      ('RIDER', TRUE, '2.1.0'),
                                                                      ('CUSTOMER', FALSE, '2.0.5'),
                                                                      ('RIDER', FALSE, '2.0.9'),
                                                                      ('CUSTOMER', TRUE, '2.1.1');

-- Insert References (Payment References)
INSERT INTO `References`
(`order_ids`, `amount`, `currency`, `psp_reference`, `payment_method`, `result_json`) VALUES
                                                                                          ('[1]', 25.50, 'AUD', 'PSP123456', 'CREDIT_CARD', '{"status": "success", "transaction_id": "TX123"}'),
                                                                                          ('[2]', 35.75, 'AUD', 'PSP123457', 'CREDIT_CARD', '{"status": "success", "transaction_id": "TX124"}'),
                                                                                          ('[3]', 45.90, 'AUD', 'PSP123458', 'PAYPAL', '{"status": "pending", "transaction_id": "TX125"}');

-- Insert Payment Webhook Payload
INSERT INTO `Payment_Webhook_Payload`
(`pspReference`, `merchantReference`, `eventCode`, `paymentMethod`, `amount`, `success`, `payload`) VALUES
                                                                                                        ('PSP123456', 'ORD-2024-001', 'AUTHORISATION', 'visa', '{"currency":"AUD","value":2550}', TRUE,
                                                                                                         '{"event":"payment_success","timestamp":"2024-02-24T10:00:00Z"}'),
                                                                                                        ('PSP123457', 'ORD-2024-002', 'AUTHORISATION', 'mastercard', '{"currency":"AUD","value":3575}', TRUE,
                                                                                                         '{"event":"payment_success","timestamp":"2024-02-24T11:00:00Z"}'),
                                                                                                        ('PSP123458', 'ORD-2024-003', 'AUTHORISATION', 'paypal', '{"currency":"AUD","value":4590}', FALSE,
                                                                                                         '{"event":"payment_pending","timestamp":"2024-02-24T12:00:00Z"}');
