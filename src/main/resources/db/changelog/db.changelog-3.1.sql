-- liquibase formatted sql

--changeset shvaiale:1
alter table personal_information alter column street drop default;