insert into user (dtype, id, firstname, lastname, username, password, blocked, address, email, operates_since, store_name) values ('Seller', 1, 'Elena', 'Krunic' , 'Lele', '222', false, 'Gavrila Principa 4', 'elenakrunic@gmail.com', '2021-11-23', 'ElenaByz')
insert into user (dtype, id, firstname, lastname, username, password, blocked, address, email, operates_since, store_name) values ('Buyer', 2, 'Selena', 'Tutic' , 'Sele', '333', false, 'Gavrila Principa 6', 'selenatutic@gmail.com', '2008-12-01', 'SelenaByz')

INSERT INTO role (id, _name) values (1, 'SELLER');
INSERT INTO role (id, _name) values (2, 'BUYER');
INSERT INTO role (id, _name) values (3, 'ADMIN');

insert into role_has_user (user_id, role_id) values (1, 1)
insert into role_has_user (user_id, role_id) values (2, 2)


insert into article (id, description, name, path, price, seller_id) values (1, 'Black based colorway of the retro basketball shoe worn by former Boston Celtics guard Dee Brown during the 91 NBA Slam Dunk Contest', 'Pump Omni Zone || Dee Brown ', 'C:/Users/lenovo/Desktop/UES_Project/DeliveryApp/elastic.search/src/main/resources/templates/ThePump.jpg',  123, 1)
insert into article (id, description, name, path, price, seller_id) values (2, 'This shoe is collaboration between the food-related YouTube series and Reebok on a scorchin hot colorway of Allen Iversons first signature shoe.', 'Question Mid Hot Ones ', 'C:/Users/lenovo/Desktop/UES_Project/DeliveryApp/elastic.search/src/main/resources/templates/AlenIversonShoe.jpg',  105, 1)
insert into article (id, description, name, path, price, seller_id) values (3, 'Dobra patika', 'Patika', '',  222, 1)
insert into article (id, description, name, path, price, seller_id) values (4, 'Dobra patika dva', 'PatikaDva', '',  333, 1)