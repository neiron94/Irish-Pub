-- liquibase formatted sql

--changeset shvaiale:1
alter table personal_information add column street varchar(40) NOT NULL default 'Gagarina';
alter table personal_information add column house_number integer NOT NULL default 1;

--changeset shvaiale:2
drop table address;