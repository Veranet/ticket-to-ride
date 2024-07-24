TRUNCATE route RESTART IDENTITY;
TRUNCATE traveller RESTART IDENTITY CASCADE;

INSERT INTO traveller (name, email, created_date, deleted_date)
    VALUES ('Alex', 'nalex@dot.com', '2024-5-5', null),
           ('Bob', 'nob@dot.com', '2024-6-6', null),
           ('Jack', 'nlack@dot.com', '2024-7-7', '2024-6-5');

INSERT INTO account (traveller_id, balance)
VALUES (1, 25.0),
       (2, 0.0),
       (3, 15.0);

INSERT INTO ticket (traveller_id, from_town, to_town, price, created_date, expired_date)
VALUES (1, 'A', 'B', 2.0, '2024-5-5', '2024-6-5');

INSERT INTO route (from_town, to_town, segments_amount)
VALUES ('A', 'C', 5),
       ('A', 'B', 2);