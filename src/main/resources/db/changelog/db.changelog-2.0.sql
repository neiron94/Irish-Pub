-- liquibase formatted sql

--changeset shvaiale:1
alter table address drop constraint address_pkey;
alter table address add column id serial primary key;
alter table address add constraint address_id_customer_house_number_street_key UNIQUE (id_customer, house_number, street);

--changeset shvaiale:2
alter table can_replace drop constraint can_replace_pkey;
alter table can_replace add column id serial primary key;
alter table can_replace add constraint can_replace_replacer_replaceable_key UNIQUE (replacer, replaceable);

--changeset shvaiale:3
alter table table_order drop constraint table_order_pkey;
alter table table_order add column id serial primary key;
alter table table_order add constraint table_order_id_customer_time_key UNIQUE (id_customer, time);

--changeset shvaiale:4
alter table food_order_contains_dish drop constraint food_order_contains_dish_id_customer_time_fkey;
alter table food_order_contains_dish drop constraint food_order_contains_dish_pkey;

--changeset shvaiale:5
alter table worker_takes_food_order drop constraint worker_takes_food_order_id_customer_time_fkey;
alter table food_order drop constraint food_order_pkey;
alter table food_order add constraint food_order_id_customer_time_key UNIQUE (id_customer, time);
alter table food_order add column id serial primary key;

--changeset shvaiale:6
alter table worker_takes_food_order drop constraint worker_takes_food_order_pkey;
alter table worker_takes_food_order add column id_food_order int;
alter table worker_takes_food_order drop column id_customer;
alter table worker_takes_food_order drop column time;
alter table Worker_takes_food_order alter column id_food_order set not null;
alter table worker_takes_food_order add constraint worker_takes_food_order_id_food_order_fkey foreign key (id_food_order) references Food_order(id) on update cascade on delete cascade;
alter table worker_takes_food_order add constraint worker_takes_food_order_pkey primary key (id_food_order);

--changeset shvaiale:7
alter table food_order_contains_dish add column id_food_order int;
alter table food_order_contains_dish drop column id_customer;
alter table food_order_contains_dish drop column time;
alter table food_order_contains_dish alter column id_food_order set not null;
alter table food_order_contains_dish add constraint food_order_contains_dish_id_food_order_fkey foreign key (id_food_order) references Food_order(id) on update cascade on delete cascade;
alter table food_order_contains_dish add constraint food_order_contains_dish_id_food_order_id_dish_key UNIQUE (id_food_order, id_dish);
alter table food_order_contains_dish add column id serial primary key;
