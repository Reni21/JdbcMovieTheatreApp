USE movie_theatre;

INSERT INTO user (login, password, role, email)
VALUES ('admin', 'admin', 'ADMIN', 'admin@gmail.com'),
       ('reni', 'reni', 'USER', 'reni@gmail.com'),
       ('test', 'test', 'USER', 'test@gmail.com');

INSERT INTO hall (number)
VALUES (1);

INSERT INTO seat (seat_row, place, hall_id)
VALUES (1, 1, 1), (1, 2, 1), (1, 3, 1), (1, 4, 1), (1, 5, 1), (1, 6, 1), (1, 7, 1), (1, 8, 1), (1, 9, 1),
       (2, 1, 1), (2, 2, 1), (2, 3, 1), (2, 4, 1), (2, 5, 1), (2, 6, 1), (2, 7, 1), (2, 8, 1), (2, 9, 1),
       (3, 1, 1), (3, 2, 1), (3, 3, 1), (3, 4, 1), (3, 5, 1), (3, 6, 1), (3, 7, 1), (3, 8, 1), (3, 9, 1),
       (4, 1, 1), (4, 2, 1), (4, 3, 1), (4, 4, 1), (4, 5, 1), (4, 6, 1), (4, 7, 1), (4, 8, 1), (4, 9, 1),
       (5, 1, 1), (5, 2, 1), (5, 3, 1), (5, 4, 1), (5, 5, 1), (5, 6, 1), (5, 7, 1), (5, 8, 1), (5, 9, 1);

INSERT INTO movie (title, directed_by, duration_minutes, trailer_url, background_img_url, cover_img_url)
VALUES ('Eternal Sunshine of the Spotless Mind', 'Michel Gondry', 108,
        'https://www.youtube.com/watch?v=07-QBnEkgXU',
        'http://hd-kino.net/uploads/posts/2015-11/1447508640_5.jpg',
        'https://upload.wikimedia.org/wikipedia/ru/thumb/a/af/Eternal_Sunshine_of_the_Spotless_Mind.jpg/269px-Eternal_Sunshine_of_the_Spotless_Mind.jpg'), /*1*/
       ('Lobster', 'Yorgos Lanthimos', 116,
        'https://www.youtube.com/watch?v=vU29VfayDMw',
        'https://thecriticalcritics.com/review/wp-content/images/the-lobster-still-1-1160x480.jpg',
        'https://bookshelf.ca/i/size/o/media--0665557f-463f-e93c-f1ed-ee05bae19537/w/600'); /*2*/

INSERT INTO movie_session (movie_id, hall_id, start_at, price)
VALUES (1,1,'2019-11-13 10:30:00', 60),
       (1,1,'2019-11-13 13:00:00', 80),
       (2,1,'2019-11-13 15:00:00', 80),
       (2,1,'2019-11-13 19:00:00', 120);


