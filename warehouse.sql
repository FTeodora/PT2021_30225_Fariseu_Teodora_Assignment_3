drop database if exists warehouse;
create database warehouse;
use warehouse;
-- tabele
create table Clients(ID bigint auto_increment primary key,
					firstName varchar(50),
                    lastName varchar(50),
                    dateOfBirth date,
                    eMail varchar(50));
create table Orders(ID bigint auto_increment primary key,
					clientID bigint NOT NULL,
					foreign key(clientID) references clients(ID) ON DELETE CASCADE,
					state enum('CONFIRMED','SHIPPED','DELIVERED'),
                    orderDate date,
                    courier varchar(50),
                    deliveryAddress varchar(100),
                    totalPrice decimal(15,2));
create table Product(ID bigint auto_increment primary key,
					productName varchar(50),
					price decimal(15,2),
					category varchar(50),
                    brand varchar(50),
                    quantity int);
create table OrderItem(
					productID bigint,
					foreign key(productID) references Product(ID) ON DELETE CASCADE,
					orderID bigint,
					foreign key(orderID) references Orders(ID) ON DELETE CASCADE,
					requestedQuantity int);
-- triggere
delimiter ,,
create trigger complete_order before INSERT ON Orders FOR EACH ROW
BEGIN
	SET NEW.orderDate=curdate();
    SET NEW.state='CONFIRMED';
END
,,

delimiter ;;
create trigger remove_from_stock AFTER INSERT ON OrderItem FOR EACH ROW
BEGIN
	UPDATE product SET Product.quantity=Product.quantity-new.requestedQuantity WHERE product.ID=NEW.productID AND product.ID=NEW.productID;
END
;;

delimiter \\
create trigger remove_from_order BEFORE DELETE ON OrderItem FOR EACH ROW
BEGIN
	UPDATE product SET Product.quantity=Product.quantity+OLD.requestedQuantity WHERE product.ID=OLD.productID AND product.ID=OLD.productID;
END 
\\

-- populari
insert into Clients(firstName,lastName,dateOfBirth,eMail) values('Grigore','Grigorescu','1969-04-20','gri@go.re'),
('Giorno','Giovanna','1985-04-16','gio.gio@gmail.com'),
('Melissa','Hernandez','1983-07-22','hernlissa@yahoo.com'),
('Amanda','Mooney','1999-03-14','manda@gmail.com'),
('Stephanie','Bradshaw','1983-03-14','manda@gmail.com'),
('Donald','Hendrix','1970-02-09','donald.hendrix@gmail.com'),
('Francois','DeGenereaux','2000-09-24','mtc@s3.rl'),
('Hasan','Bright','1993-10-11','hasan@yahoo.com'),
('Luke','Hammond','1986-05-17','lukehammond@yahoo.com'),
('Erica','Kane','1975-12-10','erikane@yahoo.com'),
('Yoshikage','Kira','1966-01-30','kira.quin@gmail.com'),
('Yung','Spinach','2006-04-03','subway@yahoo.com'),
('Jean-Pierre','Polnareff','1965-12-04','pierre@yahoo.com');

insert into Product(productName,price,category,brand,quantity) values('Caiet A4 matematica',4.20,'papetarie si birotica','pigna',1000),
('Caiet A4 dictando',4.20,'papetarie si birotica','pigna',1000),
('Coperta caiet A4',2.10,'papetarie si birotica','herlitz',3000),
('Dosar cu sina A4',5.49,'papetarie si birotica','herlitz',5000),
('Stilou',15.00,'papetarie si birotica','pelican',3000),
('Nurofen forte',30.10,'medicamente','Nurofen',110),
('Alprazolam',39.99,'medicamente','Xanax',420),
('Bupropion',65.00,'medicamente','Wellbutrin',670),
('Fluoxetine',79.99,'medicamente','Prozac',500),
('Sertraline',84.99,'medicamente','Zoloft',1000),
('Geanta',3000.49,'accesorii vestimentare','Gucci',50),
('Pantofi barbati',2999.99,'incaltaminte si haine','Gucci',30),
('Sacou barbati',4999.49,'incaltaminte si haine','Valentino',15),
('Cravata',499.99,'incaltaminte si haine','Valentino',15),
('Ochelari de soare',50.00,'accesorii vestimentare','Converse',50),
('Franghie de iuta 10 mm, 50m',50.00,'utilitati','N\A',1000),
('Scaun bucatarie',100.00,'mobilier','Radumob',1000),
('Diazepam',79.99,'medicamente','Valium',400);
insert into Orders(clientID,courier,deliveryAddress,totalPrice) values(11,"FedEx","Heion'na seikatsu St No. 14, Morioh-Cho, Japan",12543.93);
insert into OrderItem(orderID,productID,requestedQuantity) values(1,13,2),(1,14,5),(1,5,3);