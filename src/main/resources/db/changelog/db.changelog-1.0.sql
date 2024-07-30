-- liquibase formatted sql

--changeset shvaiale:1
CREATE TABLE Person
(
    id_person  SERIAL PRIMARY KEY,
    birth_date DATE        NOT NULL CHECK (birth_date BETWEEN '1900-01-01' AND CURRENT_DATE),
    name       VARCHAR(40) NOT NULL,
    surname    VARCHAR(40) NOT NULL,
    UNIQUE (birth_date, name, surname)
);

--changeset shvaiale:2
CREATE TABLE Worker
(
    id_worker            INT PRIMARY KEY REFERENCES Person ON UPDATE CASCADE ON DELETE CASCADE,
    working_phone_number VARCHAR(20) UNIQUE NOT NULL,
    email                VARCHAR(40) UNIQUE NOT NULL CHECK (email LIKE '%@%.%'),
    salary               INT                NOT NULL CHECK (salary > 0)
);

--changeset shvaiale:3
CREATE TABLE Can_replace
(
    replacer    INT NOT NULL REFERENCES Worker ON UPDATE CASCADE ON DELETE CASCADE,
    replaceable INT NOT NULL REFERENCES Worker ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (replacer, replaceable)
);

--changeset shvaiale:4
CREATE TABLE Customer
(
    id_customer          INT PRIMARY KEY REFERENCES Person ON UPDATE CASCADE ON DELETE CASCADE,
    discount_card_number BIGINT UNIQUE CHECK (discount_card_number > 0)
);

--changeset shvaiale:5
CREATE TABLE Personal_information
(
    id_customer  INT PRIMARY KEY REFERENCES Customer ON UPDATE CASCADE ON DELETE CASCADE,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    email        VARCHAR(40) UNIQUE NOT NULL CHECK (email LIKE '%@%.%')
);

--changeset shvaiale:6
CREATE TABLE Address
(
    id_customer  INT         NOT NULL REFERENCES Personal_information ON UPDATE CASCADE ON DELETE CASCADE,
    house_number INT         NOT NULL CHECK (house_number > 0),
    street       VARCHAR(40) NOT NULL,
    PRIMARY KEY (id_customer, house_number, street)
);

--changeset shvaiale:7
CREATE TABLE Table_order
(
    id_customer  INT       NOT NULL REFERENCES Customer ON UPDATE CASCADE ON DELETE CASCADE,
    time         TIMESTAMP NOT NULL,
    table_number SMALLINT  NOT NULL CHECK (table_number >= 0),
    id_worker    INT       NOT NULL REFERENCES Worker ON UPDATE CASCADE ON DELETE NO ACTION,
    PRIMARY KEY (id_customer, time)
);

--changeset shvaiale:8
CREATE TABLE Dish
(
    id_dish SERIAL PRIMARY KEY,
    name    VARCHAR(50) UNIQUE NOT NULL,
    price   DECIMAL(7, 2)      NOT NULL CHECK (price >= 0)
);

--changeset shvaiale:9
CREATE TABLE Alcohol
(
    id_alcohol INT PRIMARY KEY REFERENCES Dish ON UPDATE CASCADE ON DELETE CASCADE,
    strength   SMALLINT CHECK (strength BETWEEN 0 AND 100),
    type       VARCHAR(30),
    quantity   SMALLINT CHECK (quantity > 0)
);

--changeset shvaiale:10
CREATE TABLE Food
(
    id_food INT PRIMARY KEY REFERENCES Dish ON UPDATE CASCADE ON DELETE CASCADE,
    weight  INT CHECK (weight > 0)
);

--changeset shvaiale:11
CREATE TABLE Food_order
(
    id_customer INT       NOT NULL REFERENCES Customer ON UPDATE CASCADE ON DELETE CASCADE,
    time        TIMESTAMP NOT NULL,
    PRIMARY KEY (id_customer, time)
);

--changeset shvaiale:12
CREATE TABLE Worker_takes_food_order
(
    id_customer INT       NOT NULL,
    time        TIMESTAMP NOT NULL,
    id_worker   INT       NOT NULL REFERENCES Worker ON UPDATE CASCADE ON DELETE NO ACTION,
    FOREIGN KEY (id_customer, time) REFERENCES Food_order ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (id_customer, time)
);

--changeset shvaiale:13
CREATE TABLE Food_order_contains_dish
(
    id_customer INT       NOT NULL,
    time        TIMESTAMP NOT NULL,
    id_dish     INT       NOT NULL REFERENCES Dish ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_customer, time) REFERENCES Food_order ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (id_customer, time, id_dish)
);
