create database javachallenge;

use javachallenge;

create table customers ( 
	id int auto_increment,
	name varchar(30),
	lastName varchar(35),
    dni varchar(35),
    phone varchar(35),
    originCity varchar(35),
	constraint pk_customers primary key(id)
);

create table rooms(
	id int auto_increment,
	roomNumber int,
	beds int,
	price float,
	status varchar(35),
    available boolean,
	constraint pk_rooms primary key(id),
	constraint unq_roomNumber UNIQUE(roomNumber)
);

create table checkins(
	id int auto_increment,
	numberOfDays int,
	occupants int,
	roomNumber int,
	customerId int,
	constraint pk_checkins primary key(id),
	constraint fk_customerId foreign key (customerId) references customers(id),
	constraint fk_roomNumber foreign key (roomNumber) references rooms(roomNumber)
);

create table services(
	id int auto_increment,
	name varchar(40),
	roomId int,
	constraint pk_services primary key(id),
	constraint fk_roomId foreign key (roomId) references rooms(id)
);

--we can use another table called "services_x_rooms" in order to reuse the services in other rooms