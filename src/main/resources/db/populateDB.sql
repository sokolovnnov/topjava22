DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-01-15 10:00', 'Breakfast' , 500, 100000),
       ('2020-01-15 12:00', 'Dinner'    , 500, 100000),
       ('2020-01-15 16:00', 'Lunch'     , 500, 100000),
       ('2020-01-15 20:00', 'Supper'    , 400, 100000),
       ('2020-01-17 11:00', 'Breakfast' , 700, 100001),
       ('2020-01-17 15:00', 'Lunch'     , 800, 100001),
       ('2020-01-17 21:00', 'Supper'    , 900, 100001);
