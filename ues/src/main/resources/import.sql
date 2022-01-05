insert into user (dtype, id, firstname, lastname, username, password, blocked, address, email, operates_since, store_name) values ('Seller', 1, 'Elena', 'Krunic' , 'Lele', '222', false, 'Gavrila Principa 4', 'elenakrunic@gmail.com', '2021-11-23', 'ElenaByz')
insert into user (dtype, id, firstname, lastname, username, password, blocked, address, email, operates_since, store_name) values ('Buyer', 2, 'Selena', 'Tutic' , 'Sele', '333', false, 'Gavrila Principa 6', 'selenatutic@gmail.com', '2008-12-01', 'SelenaByz')

INSERT INTO role (id, _name) values (1, 'SELLER');
INSERT INTO role (id, _name) values (2, 'BUYER');
INSERT INTO role (id, _name) values (3, 'ADMIN');

insert into role_has_user (user_id, role_id) values (1, 1)
insert into role_has_user (user_id, role_id) values (2, 2)

insert into article (id, description, name, path, price, seller_id) values (1, 'MacBook Pro has a tenth-generation quad-core Intel processor with Turbo Boost up to 3.8GHz. A brilliant and colorful Retina display with True Tone technology for a more true-to-life viewing experience. A backlit Magic Keyboard and Touch ID', 'MacBook Pro 13" Display with Touch Bar', 'http://media.bizwebmedia.net//sites/72783/data/images/2016/2/4713895macbook_pro_retina.png',  1000, 1)
insert into article (id, description, name, path, price, seller_id) values (2, 'Lenovo Pro 13.3 inches Thinkpad MF841LL/A Model 2015 Option Ram Care 12/2016', 'Lenovo Thinkpad 13.3" MF841LL/A', 'http://media.bizwebmedia.net//sites/72783/data/images/2015/11/3220113retina13.jpg', 1200, 1)
insert into article (id, description, name, path, price, seller_id) values (3, '3.0GHz Dual-core Haswell Intel Core i5 Turbo Boost up to 3.2 GHz, 3MB L3 cache 8GB (two 4GB SO-DIMMs) of 1600MHz DDR3 SDRAM', 'Macbook Pro 15.4 inches Retina MC975LL/A Model 2012', 'http://media.bizwebmedia.net//sites/72783/data/images/2015/7/2913337mf841_13_inch_2_9ghz_with_retina_display_early_2015.png', 1800,1)
insert into article (id, description, name, path, price, seller_id) values (4, 'Expand your view of everything on MacBook Pro thanks to a larger 16" Retina display with sharper pixel resolution and support for millions of colors. Harness the power of 8-core processors and AMD Radeon Pro 5000M series graphics with 4GB of GDDR6 memory', 'Apple MacBook Pro 16" Display with Touch Bar', 'http://media.bizwebmedia.net//sites/72783/data/images/2016/2/4713895macbook_pro_retina.png',  1000, 1)

insert into errand (id, anonymous_comment, archived_comment, comment, grade, is_delivered, ordered_at_date, buyer_id) values (1, false, true, 'Komentar koji je arhiviran', 4, true, '2021-11-29', 2)
insert into errand (id, anonymous_comment, archived_comment, comment, grade, is_delivered, ordered_at_date, buyer_id) values (2, false, true, 'Drugi omentar koji je arhiviran', 4, true, '2021-11-29', 2)
