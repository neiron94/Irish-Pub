insert into person (id_person, birth_date, name, surname)
values (1, '1995-04-22', 'Michael', 'O''Connor'),
       (2, '1992-09-15', 'Sarah', 'McCarthy'),
       (3, '1993-11-30', 'David', 'O''Neill'),
       (4, '1991-06-18', 'Laura', 'Kelly');
select setval('person_id_person_seq', (select max(id_person) from person));

insert into customer (id_customer, discount_card_number)
values (1, 228544603),
       (2, null);

insert into personal_information (id_customer, phone_number, email, street, house_number)
values (1, '+353876543212', 'michael.oconnor@example.com', 'Gagarina', 11);

insert into worker (id_worker, working_phone_number, email, salary)
values (3, '+353876543210', 'david.oneill@example.com', 2500),
       (4, '+353870543216', 'laura.kelly@example.com', 3000);

insert into can_replace (replacer, replaceable)
values (3, 4);

insert into table_order (id, id_customer, time, table_number, id_worker)
values (1, 1, '2023-09-23 01:50:00.000000', 11, 3),
       (2, 1, '2023-09-25 11:45:00.000000', 10, 4),
       (3, 2, '2023-09-25 09:10:00.000000', 5, 4);
select setval('table_order_id_seq', (select max(id) from table_order));

insert into dish (id_dish, name, price)
values (1, 'Baileys Almande', 21.99),
       (2, 'Pinnacle Vodka' ,9.99),
       (3, 'Garlic croutons', 6.70),
       (4, 'Chicken fillet with fries', 12.50);
select setval('dish_id_dish_seq', (select max(id_dish) from dish));

insert into alcohol (id_alcohol, strength, type, quantity)
values (1, 15, 'Liqueur', 750),
       (2, 40, 'Vodka', 750);

insert into food (id_food, weight)
values (3, 210),
       (4, 330);

insert into food_order (id, id_customer, time)
values (1, 1, '2019-12-20 04:45:00.000000'),
       (2, 1, '2021-03-22 08:52:00.000000'),
       (3, 1, '2017-05-02 11:04:00.000000'),
       (4, 2, '2023-02-18 06:13:00.000000');
select setval('food_order_id_seq', (select max(id) from food_order));

insert into worker_takes_food_order (id_worker, id_food_order)
values (3, 1),
       (3, 2),
       (4, 3);

insert into food_order_contains_dish (id_dish, id_food_order)
values (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (2, 3),
       (4, 4);
