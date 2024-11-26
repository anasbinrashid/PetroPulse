create schema PetroPulse;
use PetroPulse;

create table owner (
    ownerid int auto_increment primary key,
    email varchar(255) not null,
    name varchar(255) not null,
    phonenumber varchar(15),
    password varchar(255),
    duepayment double
);
create table station (
    stationid int auto_increment primary key,
    name varchar(255) not null,
    location varchar(255),
    totalcapacity int,
    company varchar(255),
    totalearnings decimal(15, 2)
);
create table owns (
    ownerid int,
    stationid int,
    ownershipdate varchar(50),
    primary key (ownerid, stationid),
    foreign key (ownerid) references owner(ownerid),
    foreign key (stationid) references station(stationid)
);
create table worker (
    workerid int auto_increment primary key,
    name varchar(255) not null,
    password varchar(255),
    email varchar(255),
    phonenumber varchar(15),
    salary decimal(15, 2),
    stationid int,
    foreign key (stationid) references station(stationid),
    foreign key (scheduleid) references schedule(scheduleid)
);
create table schedule (
    scheduleid int auto_increment primary key,
    day varchar(50),
    date varchar(50),
    startingtime varchar(50),
    endingtime varchar(50),
    stationid int,
    workerid int,
    foreign key (stationid) references station(stationid)
);
create table fuelstand (
    fuelstandid int auto_increment primary key,
    capacity int,
    fueltype varchar(255),
    fuelprice decimal(10, 2),
    stationid int,
    workerid int,
    foreign key (stationid) references station(stationid),
    foreign key (workerid) references worker(workerid)
);
create table supplier (
    supplierid int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255),
    phonenumber varchar(15),
    sellingprice decimal(10, 2)
);
create table fuelorder (
    fuelorderid int auto_increment primary key,
    fuelamount int,
    fuelorderdate varchar(50),
    stationid int,
    supplierid int,
    foreign key (stationid) references station(stationid),
    foreign key (supplierid) references supplier(supplierid)
);

create table maintenance (
    maintenanceid int auto_increment primary key,
    description varchar(255),
    stationid int,
    supplierid int,
    item varchar(255),
    ownerid int,
    foreign key (stationid) references station(stationid),
    foreign key (supplierid) references supplier(supplierid)
);

create table orderdetails (
    orderid int auto_increment primary key,
    orderdate varchar(50),
    time varchar(50),
    amountoffuel decimal(10, 2),
    customerid int,
    fuelstandid int,
    foreign key (customerid) references customer(customerid),
    foreign key (fuelstandid) references fuelstand(fuelstandid)
);

create table payment (
    paymentid int auto_increment primary key,
    paymenttype varchar(50),
    amount decimal(10, 2),
    customerid int,
    stationid int,
    foreign key (customerid) references customer(customerid),
    foreign key (stationid) references station(stationid)
);

create table transaction (
    transactionid int auto_increment primary key,
    paymenttype varchar(50),
    amount decimal(10, 2),
    ownerid int,
    supplierid int,
    foreign key (ownerid) references owner(ownerid),
    foreign key (supplierid) references supplier(supplierid)
);

create table customer (
    customerid int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255),
    password varchar(255),
    phonenumber varchar(15),
    loyaltypoints int,
    membershipstatus bool,
    stationid int,
    duepayment double,
    foreign key (stationid) references station(stationid)
);
create table audit (
    auditid int auto_increment primary key,
    entity varchar(255),
    action varchar(255),
    entityid int
);

SHOW CREATE TABLE fuelstand;

ALTER TABLE fuelstand
DROP FOREIGN KEY fk_workerid; -- Replace fk_workerid with your actual constraint name

ALTER TABLE fuelstand
ADD CONSTRAINT fk_workerid FOREIGN KEY (workerid)
REFERENCES worker(workerid)
ON DELETE CASCADE;

ALTER TABLE worker
DROP FOREIGN KEY `worker_ibfk_2`;
ALTER TABLE orderdetails
ADD CONSTRAINT fk_orderdetails_customerid
FOREIGN KEY (customerid) REFERENCES customer(customerid)
ON DELETE CASCADE;
ALTER TABLE payment
ADD CONSTRAINT fk_payment_customerid
FOREIGN KEY (customerid) REFERENCES customer(customerid)
ON DELETE CASCADE;

alter table audit 
add column entityid int;

INSERT INTO Owner (Email, Name, PhoneNumber, Password)
VALUES
    ('hasnain@gmail.com', 'Hasnain', '03350550548', 'Hasnain123'),
    ('anas@gmail.com', 'Anas', '03350550549', 'Anas123'),
    ('adan@gmail.com', 'Adan', '03350550550', 'Adan123');
    
    
INSERT INTO Station (Name, Location, TotalCapacity, Company, TotalEarnings)
VALUES
    ('F-10 Filling Station Islamabad', 'Islamabad', 5000, 'Attock', 100000.50),
    ('Margalla Filling Station', 'Rawalpindi', 4000, 'Shell', 85000.75),
    ('Gulshan Fuel Station Karachi', 'Karachi', 6000, 'Total', 120000.90),
    ('Defence Filling Station Lahore', 'Lahore', 4500, 'Aramco', 95000.00),
    ('City Center Fuel Station Peshawar', 'Peshawar', 5000, 'Caltex', 80000.25),
    ('Blue Area Filling Station Islamabad', 'Islamabad', 4000, 'Attock', 78000.00);
    
    
INSERT INTO Owns (OwnerID, StationID, OwnershipDate)
VALUES
    (1, 1, '2022-01-15'),
    (1, 6, '2022-09-05'),
    (2, 2, '2021-12-10'),
    (2, 5, '2023-03-18'),
    (3, 3, '2023-05-20'),
    (3, 4, '2023-08-01');
    
INSERT INTO Schedule (Day, Date, StartingTime, EndingTime, StationID, WorkerID)
VALUES
    ('Weekdays', '2024-01-01', '08:00', '16:00', 3, 17),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 3, 17),
    ('Weekends', '2024-01-06', '10:00', '18:00', 3, 17),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 3, 18),
    ('Price Change Day', '2024-01-03', '07:00', '13:00', 3, 18),
    ('Weekends', '2024-01-06', '12:00', '20:00', 3, 18),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 3, 19),
    ('Price Change Day', '2024-01-04', '08:00', '14:00', 3, 19),
    ('Weekends', '2024-01-06', '14:00', '22:00', 3, 19),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 3, 20),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 3, 20),
    ('Weekends', '2024-01-06', '10:00', '18:00', 3, 20),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 3, 21),
    ('Price Change Day', '2024-01-03', '07:00', '13:00', 3, 21),
    ('Weekends', '2024-01-06', '12:00', '20:00', 3, 21),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 3, 22),
    ('Price Change Day', '2024-01-04', '08:00', '14:00', 3, 22),
    ('Weekends', '2024-01-06', '14:00', '22:00', 3, 22),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 3, 23),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 3, 23),
    ('Weekends', '2024-01-06', '10:00', '18:00', 3, 23),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 3, 24),
    ('Price Change Day', '2024-01-03', '07:00', '13:00', 3, 24),
    ('Weekends', '2024-01-06', '12:00', '20:00', 3, 24),

-- Schedules for Station 4 (WorkerID 25-32)
    ('Weekdays', '2024-01-01', '07:00', '15:00', 4, 25),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 4, 25),
    ('Weekends', '2024-01-06', '08:00', '16:00', 4, 25),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 4, 26),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 4, 26),
    ('Weekends', '2024-01-06', '12:00', '20:00', 4, 26),
    
    ('Weekdays', '2024-01-01', '10:00', '18:00', 4, 27),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 4, 27),
    ('Weekends', '2024-01-06', '14:00', '22:00', 4, 27),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 4, 28),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 4, 28),
    ('Weekends', '2024-01-06', '10:00', '18:00', 4, 28),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 4, 29),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 4, 29),
    ('Weekends', '2024-01-06', '12:00', '20:00', 4, 29),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 4, 30),
    ('Price Change Day', '2024-01-02', '08:00', '14:00', 4, 30),
    ('Weekends', '2024-01-06', '14:00', '22:00', 4, 30),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 4, 31),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 4, 31),
    ('Weekends', '2024-01-06', '10:00', '18:00', 4, 31),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 4, 32),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 4, 32),
    ('Weekends', '2024-01-06', '12:00', '20:00', 4, 32);


INSERT INTO Schedule (Day, Date, StartingTime, EndingTime, StationID, WorkerID)
VALUES
    -- Schedules for Station 1 (WorkerID 1-8)
    ('Weekdays', '2024-01-01', '08:00', '16:00', 1, 1),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 1, 1),
    ('Weekends', '2024-01-06', '10:00', '18:00', 1, 1),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 1, 2),
    ('Price Change Day', '2024-01-03', '07:00', '13:00', 1, 2),
    ('Weekends', '2024-01-06', '12:00', '20:00', 1, 2),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 1, 3),
    ('Price Change Day', '2024-01-02', '08:00', '14:00', 1, 3),
    ('Weekends', '2024-01-06', '14:00', '22:00', 1, 3),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 1, 4),
    ('Price Change Day', '2024-01-04', '06:00', '12:00', 1, 4),
    ('Weekends', '2024-01-06', '10:00', '18:00', 1, 4),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 1, 5),
    ('Price Change Day', '2024-01-02', '07:00', '13:00', 1, 5),
    ('Weekends', '2024-01-06', '12:00', '20:00', 1, 5),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 1, 6),
    ('Price Change Day', '2024-01-03', '08:00', '14:00', 1, 6),
    ('Weekends', '2024-01-06', '14:00', '22:00', 1, 6),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 1, 7),
    ('Price Change Day', '2024-01-04', '06:00', '12:00', 1, 7),
    ('Weekends', '2024-01-06', '10:00', '18:00', 1, 7),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 1, 8),
    ('Price Change Day', '2024-01-02', '07:00', '13:00', 1, 8),
    ('Weekends', '2024-01-06', '12:00', '20:00', 1, 8),

    -- Schedules for Station 2 (WorkerID 9-16)
    ('Weekdays', '2024-01-01', '07:00', '15:00', 2, 9),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 2, 9),
    ('Weekends', '2024-01-06', '08:00', '16:00', 2, 9),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 2, 10),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 2, 10),
    ('Weekends', '2024-01-06', '12:00', '20:00', 2, 10),
    
    ('Weekdays', '2024-01-01', '10:00', '18:00', 2, 11),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 2, 11),
    ('Weekends', '2024-01-06', '14:00', '22:00', 2, 11),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 2, 12),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 2, 12),
    ('Weekends', '2024-01-06', '10:00', '18:00', 2, 12),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 2, 13),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 2, 13),
    ('Weekends', '2024-01-06', '12:00', '20:00', 2, 13),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 2, 14),
    ('Price Change Day', '2024-01-02', '08:00', '14:00', 2, 14),
    ('Weekends', '2024-01-06', '14:00', '22:00', 2, 14),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 2, 15),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 2, 15),
    ('Weekends', '2024-01-06', '10:00', '18:00', 2, 15),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 2, 16),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 2, 16),
    ('Weekends', '2024-01-06', '12:00', '20:00', 2, 16);


-- Schedules for Station 5 (WorkerID 33-40)
INSERT INTO Schedule (Day, Date, StartingTime, EndingTime, StationID, WorkerID)
VALUES
    ('Weekdays', '2024-01-01', '07:00', '15:00', 5, 33),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 5, 33),
    ('Weekends', '2024-01-06', '08:00', '16:00', 5, 33),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 5, 34),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 5, 34),
    ('Weekends', '2024-01-06', '12:00', '20:00', 5, 34),
    
    ('Weekdays', '2024-01-01', '10:00', '18:00', 5, 35),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 5, 35),
    ('Weekends', '2024-01-06', '14:00', '22:00', 5, 35),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 5, 36),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 5, 36),
    ('Weekends', '2024-01-06', '10:00', '18:00', 5, 36),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 5, 37),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 5, 37),
    ('Weekends', '2024-01-06', '12:00', '20:00', 5, 37),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 5, 38),
    ('Price Change Day', '2024-01-02', '08:00', '14:00', 5, 38),
    ('Weekends', '2024-01-06', '14:00', '22:00', 5, 38),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 5, 39),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 5, 39),
    ('Weekends', '2024-01-06', '10:00', '18:00', 5, 39),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 5, 40),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 5, 40),
    ('Weekends', '2024-01-06', '12:00', '20:00', 5, 40);
-- Schedules for Station 6 (WorkerID 41-48)
INSERT INTO Schedule (Day, Date, StartingTime, EndingTime, StationID, WorkerID)
VALUES
    ('Weekdays', '2024-01-01', '07:00', '15:00', 6, 41),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 6, 41),
    ('Weekends', '2024-01-06', '08:00', '16:00', 6, 41),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 6, 42),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 6, 42),
    ('Weekends', '2024-01-06', '12:00', '20:00', 6, 42),
    
    ('Weekdays', '2024-01-01', '10:00', '18:00', 6, 43),
    ('Price Change Day', '2024-01-02', '06:00', '12:00', 6, 43),
    ('Weekends', '2024-01-06', '14:00', '22:00', 6, 43),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 6, 44),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 6, 44),
    ('Weekends', '2024-01-06', '10:00', '18:00', 6, 44),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 6, 45),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 6, 45),
    ('Weekends', '2024-01-06', '12:00', '20:00', 6, 45),
    
    ('Weekdays', '2024-01-01', '07:00', '15:00', 6, 46),
    ('Price Change Day', '2024-01-02', '08:00', '14:00', 6, 46),
    ('Weekends', '2024-01-06', '14:00', '22:00', 6, 46),
    
    ('Weekdays', '2024-01-01', '08:00', '16:00', 6, 47),
    ('Price Change Day', '2024-01-03', '06:00', '12:00', 6, 47),
    ('Weekends', '2024-01-06', '10:00', '18:00', 6, 47),
    
    ('Weekdays', '2024-01-01', '09:00', '17:00', 6, 48),
    ('Price Change Day', '2024-01-04', '07:00', '13:00', 6, 48),
    ('Weekends', '2024-01-06', '12:00', '20:00', 6, 48);


-- 1st Station
INSERT INTO Worker (Name, Password, Email, PhoneNumber, Salary, StationID, ScheduleID)
VALUES
    ('Ali', 'Ali123', 'ali@gmail.com', '03001234561', 40000, 1, 1),
    ('Bilal', 'Bilal123', 'bilal@gmail.com', '03001234562', 38000, 1, 1),
    ('Ahmed', 'Ahmed123', 'ahmed@gmail.com', '03001234563', 39000, 1, 1),
    ('Saad', 'Saad123', 'saad@gmail.com', '03001234564', 37000, 1, 2),
    ('Usman', 'Usman123', 'usman@gmail.com', '03001234565', 40000, 1, 2),
    ('Hassan', 'Hassan123', 'hassan@gmail.com', '03001234566', 41000, 1, 2),
    ('Hamza', 'Hamza123', 'hamza@gmail.com', '03001234567', 38000, 1, 3),
    ('Zain', 'Zain123', 'zain@gmail.com', '03001234568', 40000, 1, 3);
    
    
-- 2nd station
INSERT INTO Worker (Name, Password, Email, PhoneNumber, Salary, StationID, ScheduleID)
VALUES
    ('Faizan', 'Faizan123', 'faizan@gmail.com', '03001234569', 39000, 2, 4),
    ('Fahad', 'Fahad123', 'fahad@gmail.com', '03001234570', 38000, 2, 4),
    ('Adnan', 'Adnan123', 'adnan@gmail.com', '03001234571', 37000, 2, 4),
    ('Ibrahim', 'Ibrahim123', 'ibrahim@gmail.com', '03001234572', 36000, 2, 5),
    ('Imran', 'Imran123', 'imran@gmail.com', '03001234573', 35000, 2, 5),
    ('Tariq', 'Tariq123', 'tariq@gmail.com', '03001234574', 34000, 2, 5),
    ('Noman', 'Noman123', 'noman@gmail.com', '03001234575', 40000, 2, 6),
    ('Salman', 'Salman123', 'salman@gmail.com', '03001234576', 42000, 2, 6);
    
-- 3rd station
INSERT INTO Worker (Name, Password, Email, PhoneNumber, Salary, StationID, ScheduleID)
VALUES
    ('Ahsan', 'Ahsan123', 'ahsan@gmail.com', '03001234577', 38000, 3, 7),
    ('Danish', 'Danish123', 'danish@gmail.com', '03001234578', 37000, 3, 8),
    ('Faisal', 'Faisal123', 'faisal@gmail.com', '03001234579', 39000, 3, 9),
    ('Muneeb', 'Muneeb123', 'muneeb@gmail.com', '03001234580', 40000, 3, 7),
    ('Shahid', 'Shahid123', 'shahid@gmail.com', '03001234581', 41000, 3, 8),
    ('Kashif', 'Kashif123', 'kashif@gmail.com', '03001234582', 36000, 3, 9),
    ('Arif', 'Arif123', 'arif@gmail.com', '03001234583', 39000, 3, 7),
    ('Irfan', 'Irfan123', 'irfan@gmail.com', '03001234584', 40000, 3, 9);
    
    
-- 4th station    
INSERT INTO Worker (Name, Password, Email, PhoneNumber, Salary, StationID, ScheduleID)
VALUES
    ('Taimoor', 'Taimoor123', 'taimoor@gmail.com', '03001234585', 40000, 4, 10),
    ('Asad', 'Asad123', 'asad@gmail.com', '03001234586', 37000, 4, 11),
    ('Rizwan', 'Rizwan123', 'rizwan@gmail.com', '03001234587', 39000, 4, 12),
    ('Zubair', 'Zubair123', 'zubair@gmail.com', '03001234588', 41000, 4, 12),
    ('Usama', 'Usama123', 'usama@gmail.com', '03001234589', 40000, 4, 11),
    ('Kamran', 'Kamran123', 'kamran@gmail.com', '03001234590', 35000, 4, 10),
    ('Haider', 'Haider123', 'haider@gmail.com', '03001234591', 37000, 4, 10),
    ('Shahbaz', 'Shahbaz123', 'shahbaz@gmail.com', '03001234592', 36000, 4, 11);
    
    
-- 5th station
INSERT INTO Worker (Name, Password, Email, PhoneNumber, Salary, StationID, ScheduleID)
VALUES
    ('Junaid', 'Junaid123', 'junaid@gmail.com', '03001234593', 38000, 5, 13),
    ('Hammad', 'Hammad123', 'hammad@gmail.com', '03001234594', 39000, 5, 13),
    ('Adeel', 'Adeel123', 'adeel@gmail.com', '03001234595', 40000, 5, 15),
    ('Talha', 'Talha123', 'talha@gmail.com', '03001234596', 37000, 5, 15),
    ('Shayan', 'Shayan123', 'shayan@gmail.com', '03001234597', 36000, 5, 15),
    ('Furqan', 'Furqan123', 'furqan@gmail.com', '03001234598', 40000, 5, 15),
    ('Zafar', 'Zafar123', 'zafar@gmail.com', '03001234599', 41000, 5, 14),
    ('Waqar', 'Waqar123', 'waqar@gmail.com', '03001234600', 42000, 5, 14);
    
    
    
-- station 6
INSERT INTO Worker (Name, Password, Email, PhoneNumber, Salary, StationID, ScheduleID)
VALUES
    ('Shoaib', 'Shoaib123', 'shoaib@gmail.com', '03001234601', 40000, 6, 16),
    ('Rashid', 'Rashid123', 'rashid@gmail.com', '03001234602', 38000, 6, 17),
    ('Zeeshan', 'Zeeshan123', 'zeeshan@gmail.com', '03001234603', 39000, 6, 18),
    ('Arsalan', 'Arsalan123', 'arsalan@gmail.com', '03001234604', 41000, 6, 16),
    ('Mujtaba', 'Mujtaba123', 'mujtaba@gmail.com', '03001234605', 42000, 6, 17),
    ('Hafeez', 'Hafeez123', 'hafeez@gmail.com', '03001234606', 43000, 6, 18),
    ('Farhan', 'Farhan123', 'farhan@gmail.com', '03001234607', 44000, 6, 17),
    ('Tahir', 'Tahir123', 'tahir@gmail.com', '03001234608', 45000, 6, 17);
    
INSERT INTO FuelStand (Capacity, FuelType, FuelPrice, StationID, WorkerID)
VALUES
    -- Station 1 (F-10 Filling Station Islamabad) - Workers 1 to 8
    (2000, 'Petrol', 275.50, 1, 1),
    (2000, 'Petrol', 275.50, 1, 2),
    (1800, 'Diesel', 280.00, 1, 3),
    (1800, 'Diesel', 280.00, 1, 4),
    (1500, 'Hi-Octane', 300.00, 1, 5),
    (1500, 'Hi-Octane', 300.00, 1, 6),
    (2000, 'Petrol', 275.50, 1, 7),
    (1800, 'Diesel', 280.00, 1, 8),

    -- Station 2 (Margalla Filling Station) - Workers 9 to 16
    (2200, 'Petrol', 275.50, 2, 9),
    (2200, 'Petrol', 275.50, 2, 10),
    (1900, 'Diesel', 280.00, 2, 11),
    (1900, 'Diesel', 280.00, 2, 12),
    (1600, 'Hi-Octane', 300.00, 2, 13),
    (1600, 'Hi-Octane', 300.00, 2, 14),
    (2200, 'Petrol', 275.50, 2, 15),
    (1900, 'Diesel', 280.00, 2, 16),

    -- Station 3 (Gulshan Fuel Station Karachi) - Workers 17 to 24
    (2300, 'Petrol', 275.50, 3, 17),
    (2300, 'Petrol', 275.50, 3, 18),
    (2000, 'Diesel', 280.00, 3, 19),
    (2000, 'Diesel', 280.00, 3, 20),
    (1700, 'Hi-Octane', 300.00, 3, 21),
    (1700, 'Hi-Octane', 300.00, 3, 22),
    (2300, 'Petrol', 275.50, 3, 23),
    (2000, 'Diesel', 280.00, 3, 24),

    -- Station 4 (Defence Filling Station Lahore) - Workers 25 to 32
    (2500, 'Petrol', 275.50, 4, 25),
    (2500, 'Petrol', 275.50, 4, 26),
    (2200, 'Diesel', 280.00, 4, 27),
    (2200, 'Diesel', 280.00, 4, 28),
    (2000, 'Hi-Octane', 300.00, 4, 29),
    (2000, 'Hi-Octane', 300.00, 4, 30),
    (2500, 'Petrol', 275.50, 4, 31),
    (2200, 'Diesel', 280.00, 4, 32),

    -- Station 5 (City Center Fuel Station Peshawar) - Workers 33 to 40
    (2400, 'Petrol', 275.50, 5, 33),
    (2400, 'Petrol', 275.50, 5, 34),
    (2100, 'Diesel', 280.00, 5, 35),
    (2100, 'Diesel', 280.00, 5, 36),
    (1800, 'Hi-Octane', 300.00, 5, 37),
    (1800, 'Hi-Octane', 300.00, 5, 38),
    (2400, 'Petrol', 275.50, 5, 39),
    (2100, 'Diesel', 280.00, 5, 40),

    -- Station 6 (Blue Area Filling Station Islamabad) - Workers 41 to 48
    (2600, 'Petrol', 275.50, 6, 41),
    (2600, 'Petrol', 275.50, 6, 42),
    (2300, 'Diesel', 280.00, 6, 43),
    (2300, 'Diesel', 280.00, 6, 44),
    (2000, 'Hi-Octane', 300.00, 6, 45),
    (2000, 'Hi-Octane', 300.00, 6, 46),
    (2600, 'Petrol', 275.50, 6, 47),
    (2300, 'Diesel', 280.00, 6, 48);
    
    
    
    
INSERT INTO Customer (Name, Email, Password, PhoneNumber, LoyaltyPoints, MembershipStatus, StationID)
VALUES
-- Station 1
('Majid Hussain', 'majid@gmail.com', 'Majid123', '03001111111', 120, 1, 1),
('Hassan Mujatba', 'hassan.m@gmail.com', 'Hassan123', '03001111112', 90, 0, 1),
('Hammad Majeed', 'hammad@gmail.com', 'Hammad123', '03001111113', 200, 1, 1),
('Kashif Munir', 'kashif@gmail.com', 'Kashif123', '03001111114', 75, 0, 1),
('Hassan Ali', 'hassan.a@gmail.com', 'Hassan123', '03001111115', 150, 1, 1),
('Muhammad Amir', 'muhammad.amir@gmail.com', 'Muhammad123', '03001111116', 50, 0, 1),
('Shams Farooq', 'shams@gmail.com', 'Shams123', '03001111117', 110, 1, 1),
('Ali Raza', 'ali.raza@gmail.com', 'Ali123', '03001111118', 60, 0, 1),
('Usman Tariq', 'usman.t@gmail.com', 'Usman123', '03001111119', 95, 1, 1),
('Faizan Ahmed', 'faizan@gmail.com', 'Faizan123', '03001111120', 130, 0, 1),

-- Station 2
('Zohaib Khan', 'zohaib@gmail.com', 'Zohaib123', '03002222221', 100, 1, 2),
('Ali Abbas', 'ali.abbas@gmail.com', 'Ali123', '03002222222', 70, 0, 2),
('Sami Shah', 'sami@gmail.com', 'Sami123', '03002222223', 200, 1, 2),
('Bilal Ahmed', 'bilal@gmail.com', 'Bilal123', '03002222224', 85, 0, 2),
('Farhan Latif', 'farhan@gmail.com', 'Farhan123', '03002222225', 160, 1, 2),
('Adeel Qureshi', 'adeel@gmail.com', 'Adeel123', '03002222226', 55, 0, 2),
('Noman Riaz', 'noman@gmail.com', 'Noman123', '03002222227', 120, 1, 2),
('Kamran Ali', 'kamran@gmail.com', 'Kamran123', '03002222228', 65, 0, 2),
('Waqas Iqbal', 'waqas@gmail.com', 'Waqas123', '03002222229', 100, 1, 2),
('Tariq Javed', 'tariq@gmail.com', 'Tariq123', '03002222230', 140, 0, 2),

-- Station 3
('Saad Ullah', 'saad@gmail.com', 'Saad123', '03003333331', 130, 1, 3),
('Ahmed Raza', 'ahmed.raza@gmail.com', 'Ahmed123', '03003333332', 95, 0, 3),
('Junaid Khan', 'junaid@gmail.com', 'Junaid123', '03003333333', 180, 1, 3),
('Danish Malik', 'danish@gmail.com', 'Danish123', '03003333334', 70, 0, 3),
('Zohaib Ahmed', 'zohaib.a@gmail.com', 'Zohaib123', '03003333335', 155, 1, 3),
('Asim Shah', 'asim@gmail.com', 'Asim123', '03003333336', 45, 0, 3),
('Waseem Akram', 'waseem@gmail.com', 'Waseem123', '03003333337', 105, 1, 3),
('Faisal Riaz', 'faisal@gmail.com', 'Faisal123', '03003333338', 50, 0, 3),
('Hammad Zafar', 'hammad.z@gmail.com', 'Hammad123', '03003333339', 85, 1, 3),
('Adeel Tariq', 'adeel.t@gmail.com', 'Adeel123', '03003333340', 115, 0, 3),

-- Station 4
('Imran Javed', 'imran@gmail.com', 'Imran123', '03004444441', 110, 1, 4),
('Umar Farooq', 'umar@gmail.com', 'Umar123', '03004444442', 80, 0, 4),
('Haseeb Khan', 'haseeb@gmail.com', 'Haseeb123', '03004444443', 190, 1, 4),
('Tahir Malik', 'tahir@gmail.com', 'Tahir123', '03004444444', 60, 0, 4),
('Zeeshan Ahmed', 'zeeshan@gmail.com', 'Zeeshan123', '03004444445', 140, 1, 4),
('Saif Ullah', 'saif@gmail.com', 'Saif123', '03004444446', 55, 0, 4),
('Haris Latif', 'haris@gmail.com', 'Haris123', '03004444447', 115, 1, 4),
('Kamil Iqbal', 'kamil@gmail.com', 'Kamil123', '03004444448', 60, 0, 4),
('Shahid Riaz', 'shahid@gmail.com', 'Shahid123', '03004444449', 75, 1, 4),
('Fahad Tariq', 'fahad@gmail.com', 'Fahad123', '03004444450', 135, 0, 4);


    
-- Station 5
INSERT INTO Customer (Name, Email, Password, PhoneNumber, LoyaltyPoints, MembershipStatus, StationID)
VALUES
('Irfan Khan', 'irfan@gmail.com', 'Irfan123', '03005555551', 100, 1, 5),
('Shoaib Akhtar', 'shoaib@gmail.com', 'Shoaib123', '03005555552', 85, 0, 5),
('Rizwan Ahmed', 'rizwan@gmail.com', 'Rizwan123', '03005555553', 175, 1, 5),
('Azhar Malik', 'azhar@gmail.com', 'Azhar123', '03005555554', 70, 0, 5),
('Waleed Farooq', 'waleed@gmail.com', 'Waleed123', '03005555555', 150, 1, 5),
('Rameez Khan', 'rameez@gmail.com', 'Rameez123', '03005555556', 55, 0, 5),
('Tayyab Riaz', 'tayyab@gmail.com', 'Tayyab123', '03005555557', 110, 1, 5),
('Samiullah Khan', 'samiullah@gmail.com', 'Samiullah123', '03005555558', 65, 0, 5),
('Anwar Ali', 'anwar@gmail.com', 'Anwar123', '03005555559', 90, 1, 5),
('Aqib Javed', 'aqib@gmail.com', 'Aqib123', '03005555560', 140, 0, 5);

-- Station 6
INSERT INTO Customer (Name, Email, Password, PhoneNumber, LoyaltyPoints, MembershipStatus, StationID)
VALUES
('Fahim Ahmed', 'fahim@gmail.com', 'Fahim123', '03006666661', 120, 1, 6),
('Waqar Younis', 'waqar@gmail.com', 'Waqar123', '03006666662', 95, 0, 6),
('Jibran Malik', 'jibran@gmail.com', 'Jibran123', '03006666663', 190, 1, 6),
('Adnan Ali', 'adnan@gmail.com', 'Adnan123', '03006666664', 75, 0, 6),
('Sikandar Raza', 'sikandar@gmail.com', 'Sikandar123', '03006666665', 160, 1, 6),
('Naeem Khan', 'naeem@gmail.com', 'Naeem123', '03006666666', 45, 0, 6),
('Waqas Latif', 'waqas.l@gmail.com', 'Waqas123', '03006666667', 115, 1, 6),
('Feroz Khan', 'feroz@gmail.com', 'Feroz123', '03006666668', 55, 0, 6),
('Salman Tariq', 'salman@gmail.com', 'Salman123', '03006666669', 105, 1, 6),
('Noman Farooq', 'noman.f@gmail.com', 'Noman123', '03006666670', 135, 0, 6);


-- Insert Payment records for Station 1 customers
INSERT INTO Payment (PaymentType, Amount, CustomerID, StationID)
VALUES
    ('Cash', 13775.00, 1, 1),
    ('Card', 11000.00, 2, 1),
    ('Cash', 12500.00, 3, 1),
    ('Card', 14000.00, 4, 1),
    ('Cash', 14500.00, 5, 1),
    ('Card', 15000.00, 6, 1),
    ('Cash', 13500.00, 7, 1),
    ('Card', 12000.00, 8, 1),
    ('Cash', 14250.00, 9, 1),
    ('Card', 13000.00, 10, 1);

-- Insert Payment records for Station 2 customers
INSERT INTO Payment (PaymentType, Amount, CustomerID, StationID)
VALUES
    ('Cash', 14500.00, 11, 2),
    ('Card', 15500.00, 12, 2),
    ('Cash', 16000.00, 13, 2),
    ('Card', 12000.00, 14, 2),
    ('Cash', 13500.00, 15, 2),
    ('Card', 14250.00, 16, 2),
    ('Cash', 14750.00, 17, 2),
    ('Card', 13000.00, 18, 2),
    ('Cash', 15000.00, 19, 2),
    ('Card', 15500.00, 20, 2);

-- Insert Payment records for Station 3 customers
INSERT INTO Payment (PaymentType, Amount, CustomerID, StationID)
VALUES
    ('Cash', 13750.00, 21, 3),
    ('Card', 14500.00, 22, 3),
    ('Cash', 12500.00, 23, 3),
    ('Card', 14000.00, 24, 3),
    ('Cash', 15000.00, 25, 3),
    ('Card', 13500.00, 26, 3),
    ('Cash', 14750.00, 27, 3),
    ('Card', 15500.00, 28, 3),
    ('Cash', 13250.00, 29, 3),
    ('Card', 14500.00, 30, 3);

-- Insert Payment records for Station 4 customers
INSERT INTO Payment (PaymentType, Amount, CustomerID, StationID)
VALUES
    ('Cash', 14750.00, 31, 4),
    ('Card', 13500.00, 32, 4),
    ('Cash', 12500.00, 33, 4),
    ('Card', 15500.00, 34, 4),
    ('Cash', 14500.00, 35, 4),
    ('Card', 16000.00, 36, 4),
    ('Cash', 13000.00, 37, 4),
    ('Card', 14250.00, 38, 4),
    ('Cash', 14000.00, 39, 4),
    ('Card', 13750.00, 40, 4);

-- Insert Payment records for Station 5 customers
INSERT INTO Payment (PaymentType, Amount, CustomerID, StationID)
VALUES
    ('Cash', 15500.00, 41, 5),
    ('Card', 14500.00, 42, 5),
    ('Cash', 13500.00, 43, 5),
    ('Card', 14750.00, 44, 5),
    ('Cash', 16000.00, 45, 5),
    ('Card', 13000.00, 46, 5),
    ('Cash', 12500.00, 47, 5),
    ('Card', 14250.00, 48, 5),
    ('Cash', 14000.00, 49, 5),
    ('Card', 15000.00, 50, 5);

-- Insert Payment records for Station 6 customers
INSERT INTO Payment (PaymentType, Amount, CustomerID, StationID)
VALUES
    ('Cash', 13750.00, 51, 6),
    ('Card', 14500.00, 52, 6),
    ('Cash', 15000.00, 53, 6),
    ('Card', 13500.00, 54, 6),
    ('Cash', 14750.00, 55, 6),
    ('Card', 13250.00, 56, 6),
    ('Cash', 14250.00, 57, 6),
    ('Card', 14000.00, 58, 6),
    ('Cash', 12500.00, 59, 6),
    ('Card', 15500.00, 60, 6);




INSERT INTO Supplier (Name, Email, PhoneNumber, SellingPrice)
VALUES
    ('Attock', 'supplier@attock.com', '03001230001', 270.00),
    ('Shell', 'supplier@shell.com', '03001230002', 275.00),
    ('Total', 'supplier@total.com', '03001230003', 265.00),
    ('Aramco', 'supplier@aramco.com', '03001230004', 260.00),
    ('Caltex', 'supplier@caltex.com', '03001230005', 280.00);




-- FuelOrder Insertions with Correct SupplierID
INSERT INTO FuelOrder (FuelAmount, FuelOrderDate, StationID, SupplierID)
VALUES
    (1000, '2024-01-01', 1, 1), -- F-10 Filling Station (Attock)
    (800, '2024-01-02', 2, 2), -- Margalla Filling Station (Shell)
    (1200, '2024-01-03', 3, 3), -- Gulshan Fuel Station (Total)
    (1500, '2024-01-04', 4, 4), -- Defence Filling Station (Aramco)
    (1100, '2024-01-05', 5, 5), -- City Center Fuel Station (Caltex),
    (900, '2024-01-06', 6, 1);  -- Blue Area Filling Station (Attock)
    
    
    
    INSERT INTO Maintenance (Description, StationID, SupplierID)
VALUES
    ('Repaired fuel pump', 1, 1),
    ('CNG tank maintenance', 2, 2),
    ('Replaced oil dispenser', 3, 3),
    ('Updated software system', 4, 4),
    ('Checked storage tanks', 5, 5),
     ('Checked Oil tanks', 6, 1);
     
     
     

-- OrderDetails Insertions for Station 1 Customers
INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid)
VALUES
    ('2024-01-06', '08:15', 35, 1, 1),
    ('2024-01-06', '09:30', 40, 2, 1),
    ('2024-01-06', '11:45', 50, 3, 1),
    ('2024-01-06', '13:00', 45, 4, 1),
    ('2024-01-06', '14:30', 55, 5, 1),
    ('2024-01-06', '16:10', 60, 6, 1),
    ('2024-01-06', '17:25', 50, 7, 1),
    ('2024-01-06', '18:40', 40, 8, 1),
    ('2024-01-06', '19:55', 35, 9, 1),
    ('2024-01-06', '20:15', 60, 10, 1);

-- OrderDetails Insertions for Station 2 Customers
INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid)
VALUES
    ('2024-01-07', '08:20', 30, 11, 2),
    ('2024-01-07', '09:40', 45, 12, 2),
    ('2024-01-07', '10:50', 50, 13, 2),
    ('2024-01-07', '12:15', 60, 14, 2),
    ('2024-01-07', '14:00', 55, 15, 2),
    ('2024-01-07', '15:45', 35, 16, 2),
    ('2024-01-07', '16:30', 45, 17, 2),
    ('2024-01-07', '18:00', 40, 18, 2),
    ('2024-01-07', '19:10', 50, 19, 2),
    ('2024-01-07', '20:20', 30, 20, 2);

-- OrderDetails Insertions for Station 3 Customers
INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid)
VALUES
    ('2024-01-08', '07:45', 35, 21, 3),
    ('2024-01-08', '09:10', 40, 22, 3),
    ('2024-01-08', '10:30', 50, 23, 3),
    ('2024-01-08', '12:00', 60, 24, 3),
    ('2024-01-08', '14:15', 45, 25, 3),
    ('2024-01-08', '15:25', 50, 26, 3),
    ('2024-01-08', '17:40', 60, 27, 3),
    ('2024-01-08', '18:50', 35, 28, 3),
    ('2024-01-08', '19:55', 45, 29, 3),
    ('2024-01-08', '21:00', 50, 30, 3);

-- OrderDetails Insertions for Station 4 Customers
INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid)
VALUES
    ('2024-01-09', '08:30', 40, 31, 4),
    ('2024-01-09', '09:50', 50, 32, 4),
    ('2024-01-09', '11:10', 60, 33, 4),
    ('2024-01-09', '12:45', 55, 34, 4),
    ('2024-01-09', '14:20', 45, 35, 4),
    ('2024-01-09', '16:05', 50, 36, 4),
    ('2024-01-09', '17:30', 40, 37, 4),
    ('2024-01-09', '18:45', 35, 38, 4),
    ('2024-01-09', '19:55', 45, 39, 4),
    ('2024-01-09', '21:15', 50, 40, 4);

-- OrderDetails Insertions for Station 5 Customers
INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid)
VALUES
    ('2024-01-10', '08:10', 60, 41, 5),
    ('2024-01-10', '09:20', 50, 42, 5),
    ('2024-01-10', '11:30', 55, 43, 5),
    ('2024-01-10', '13:40', 45, 44, 5),
    ('2024-01-10', '15:25', 35, 45, 5),
    ('2024-01-10', '16:50', 40, 46, 5),
    ('2024-01-10', '18:05', 50, 47, 5),
    ('2024-01-10', '19:20', 60, 48, 5),
    ('2024-01-10', '20:45', 45, 49, 5),
    ('2024-01-10', '21:55', 55, 50, 5);

-- OrderDetails Insertions for Station 6 Customers
INSERT INTO OrderDetails (OrderDate, Time, AmountOfFuel, CustomerID, fuelstandid)
VALUES
    ('2024-01-11', '08:15', 50, 51, 6),
    ('2024-01-11', '09:45', 40, 52, 6),
    ('2024-01-11', '10:55', 60, 53, 6),
    ('2024-01-11', '12:30', 55, 54, 6),
    ('2024-01-11', '13:45', 35, 55, 6),
    ('2024-01-11', '15:20', 45, 56, 6),
    ('2024-01-11', '16:50', 50, 57, 6),
    ('2024-01-11', '18:00', 40, 58, 6),
    ('2024-01-11', '19:25', 60, 59, 6),
    ('2024-01-11', '20:30', 55, 60, 6);
    
ALTER TABLE owner
ADD COLUMN duepayment DOUBLE;
alter table maintenance add column ownerid int;
alter table worker drop column scheduleid ;
alter table schedule add column workerid int;
alter table schedule add foreign key (workerid) references worker(workerid);

