USE movie_theatre;

INSERT INTO user (login, password, role, email)
VALUES ('admin', 'admin', 'ADMIN', 'admin@gmail.com'),
       ('reni', 'reni', 'USER', 'reni@gmail.com'),
       ('oleg', 'oleg', 'USER', 'oleg@gmail.com'),
       ('anonim', 'anonim', 'USER', 'anonim@gmail.com');

INSERT INTO hall (number)
VALUES (1);

INSERT INTO seat (seat_row, place, hall_id)
VALUES (1, 1, 1), (1, 2, 1), (1, 3, 1), (1, 4, 1), (1, 5, 1), (1, 6, 1), (1, 7, 1), (1, 8, 1), (1, 9, 1), (1, 10, 1),
       (2, 1, 1), (2, 2, 1), (2, 3, 1), (2, 4, 1), (2, 5, 1), (2, 6, 1), (2, 7, 1), (2, 8, 1), (2, 9, 1), (2, 10, 1),
       (3, 1, 1), (3, 2, 1), (3, 3, 1), (3, 4, 1), (3, 5, 1), (3, 6, 1), (3, 7, 1), (3, 8, 1), (3, 9, 1), (3, 10, 1),
       (4, 1, 1), (4, 2, 1), (4, 3, 1), (4, 4, 1), (4, 5, 1), (4, 6, 1), (4, 7, 1), (4, 8, 1), (4, 9, 1), (4, 10, 1),
       (5, 1, 1), (5, 2, 1), (5, 3, 1), (5, 4, 1), (5, 5, 1), (5, 6, 1), (5, 7, 1), (5, 8, 1), (5, 9, 1), (5, 10, 1),
       (6, 1, 1), (6, 2, 1), (6, 3, 1), (6, 4, 1), (6, 5, 1), (6, 6, 1), (6, 7, 1), (6, 8, 1), (6, 9, 1), (6, 10, 1),
       (7, 1, 1), (7, 2, 1), (7, 3, 1), (7, 4, 1), (7, 5, 1), (7, 6, 1), (7, 7, 1), (7, 8, 1), (7, 9, 1), (7, 10, 1),
       (8, 1, 1), (8, 2, 1), (8, 3, 1), (8, 4, 1), (8, 5, 1), (8, 6, 1), (8, 7, 1), (8, 8, 1), (8, 9, 1), (8, 10, 1),
       (9, 1, 1), (9, 2, 1), (9, 3, 1), (9, 4, 1), (9, 5, 1), (9, 6, 1), (9, 7, 1), (9, 8, 1), (9, 9, 1), (9, 10, 1);

INSERT INTO movie (title, directed_by, duration_minutes, trailer_url)
VALUES ('Eternal Sunshine of the Spotless Mind', 'Michel Gondry', 108, 'https://www.youtube.com/watch?v=07-QBnEkgXU'), /*1*/
       ('Lobster', 'Yorgos Lanthimos', 116, 'https://www.youtube.com/watch?v=vU29VfayDMw'), /*2*/
       ('The Green Mile', 'Frank Darabont', 189, 'https://www.youtube.com/watch?v=Ki4haFrqSrw'), /*3*/
       ('The Killing of a Sacred Deer', 'Yorgos Lanthimos', 121, 'https://www.youtube.com/watch?v=Ki4haFrqSrw'), /*4*/
       ('Perfect Sense', 'David Mackenzie', 92, 'https://www.youtube.com/watch?v=A18GfVdNP1U'), /*5*/
       ('Her', 'Spike Jonze', 92, 'https://www.youtube.com/watch?v=dJTU48_yghs'); /*6*/

INSERT INTO movie_session (movie_id, hall_id, start_at, price)
VALUES (1,1,'2019-12-02 09:00:00', 80), /*mon*/
       (2,1,'2019-12-02 11:00:00', 80),
       (3,1,'2019-12-02 13:30:00', 80),
       (6,1,'2019-12-02 18:30:00', 120),
       (6,1,'2019-12-02 20:30:00', 120),

       (2,1,'2019-12-03 11:00:00', 80), /*tu*/
       (2,1,'2019-12-03 13:00:00', 80),
       (3,1,'2019-12-03 15:00:00', 80),
       (3,1,'2019-12-03 19:00:00', 120),
       (3,1,'2019-12-03 22:00:00', 120);


