USE movie_theatre;

INSERT INTO user (username, password, role, email)
VALUES ('admin', '$2a$12$5JtCXmg8uG2q9ONOWpe1m.NNcen71StlJF6hMcm4i28pZbRRsDM8K', 'ROLE_ADMIN', 'admin@gmail.com'),
       ('reni', '$2a$12$x5PPRAeu7Q3waMFci7gAb.JXRX/sRCmOfYdgF4/nmBY8BBh4m1uUq', 'ROLE_USER', 'reni@gmail.com'),
       ('test', '$2a$12$9ZkXUOxheiPzR0fqQvYh2OW.o8Topk0wDXfFSLiRINelISI81fpA6', 'ROLE_USER', 'test@gmail.com');

INSERT INTO hall (name)
VALUES ('1');

INSERT INTO seat (seat_row, place, hall_id)
VALUES (1, 1, 1),
       (1, 2, 1),
       (1, 3, 1),
       (1, 4, 1),
       (1, 5, 1),
       (1, 6, 1),
       (1, 7, 1),
       (1, 8, 1),
       (1, 9, 1),
       (2, 1, 1),
       (2, 2, 1),
       (2, 3, 1),
       (2, 4, 1),
       (2, 5, 1),
       (2, 6, 1),
       (2, 7, 1),
       (2, 8, 1),
       (2, 9, 1),
       (3, 1, 1),
       (3, 2, 1),
       (3, 3, 1),
       (3, 4, 1),
       (3, 5, 1),
       (3, 6, 1),
       (3, 7, 1),
       (3, 8, 1),
       (3, 9, 1),
       (4, 1, 1),
       (4, 2, 1),
       (4, 3, 1),
       (4, 4, 1),
       (4, 5, 1),
       (4, 6, 1),
       (4, 7, 1),
       (4, 8, 1),
       (4, 9, 1),
       (5, 1, 1),
       (5, 2, 1),
       (5, 3, 1),
       (5, 4, 1),
       (5, 5, 1),
       (5, 6, 1),
       (5, 7, 1),
       (5, 8, 1),
       (5, 9, 1);

INSERT INTO movie (title, directed_by, duration_minutes, trailer_url, background_img_url, cover_img_url)
VALUES ('Lobster', 'Yorgos Lanthimos', 116,
        'https://www.youtube.com/watch?v=vU29VfayDMw',
        'https://s3.amazonaws.com/bncore/wp-content/uploads/2016/06/288350.jpg',
        'https://bookshelf.ca/i/size/o/media--0665557f-463f-e93c-f1ed-ee05bae19537/w/600'),

       ('The Hobbit', 'Peter Jackson', 462,
        'https://www.youtube.com/watch?v=JTSoD4BBCJc',
        'https://www.xtrafondos.com/wallpapers/resized/bilbo-baggins-en-el-hobbit-3-99.jpg?s=large',
        'https://is1-ssl.mzstatic.com/image/thumb/Video69/v4/f4/28/dc/f428dca5-5b1d-172e-71f3-e129dd679ac5/pr_source.lsr/268x0w.png'),

       ('Calvary', 'John Michael McDonagh', 100,
        'https://www.youtube.com/watch?v=LGM5rq_vX4U',
        'https://img03.rl0.ru/afisha/c1500x600i/daily.afisha.ru/uploads/images/5/4f/54fa152f06c7433eba285a421638e51f.jpg',
        'https://upload.wikimedia.org/wikipedia/ru/thumb/4/48/McDonagh%27_Calvary.jpg/272px-McDonagh%27_Calvary.jpg'),

       ('Eat Pray Love', 'Ryan Murphy', 133,
        'https://www.youtube.com/watch?v=mjay5vgIwt4',
        'https://budsvetomprod.s3.amazonaws.com/system/redactor_assets//pictures/4792/content_252427_original.jpg',
        'https://upload.wikimedia.org/wikipedia/en/thumb/7/7e/Eat_pray_love_ver2.jpg/220px-Eat_pray_love_ver2.jpg'),

       ('Bird Box', 'Susanne Bier', 124,
        'https://www.youtube.com/watch?v=o2AsIXSh2xo',
        'https://cdnb.artstation.com/p/assets/images/images/015/835/357/large/aaron-sims-creative-asc-birdbox-creature-dsn-v009.jpg?1558635527',
        'https://upload.wikimedia.org/wikipedia/en/thumb/b/bd/Bird_Box_%28film%29.png/220px-Bird_Box_%28film%29.png'),

       ('Eternal Sunshine of the Spotless Mind', 'Michel Gondry', 108,
        'https://www.youtube.com/watch?v=07-QBnEkgXU',
        'http://hd-kino.net/uploads/posts/2015-11/1447508640_5.jpg',
        'https://upload.wikimedia.org/wikipedia/ru/thumb/a/af/Eternal_Sunshine_of_the_Spotless_Mind.jpg/269px-Eternal_Sunshine_of_the_Spotless_Mind.jpg');

INSERT INTO movie_session (movie_id, hall_id, start_at, price)
VALUES (1, 1, '2019-12-11 7:00:00', 60),
       (3, 1, '2019-12-11 9:30:00', 80),
       (2, 1, '2019-12-11 11:15:00', 80),
       (1, 1, '2019-12-11 16:30:00', 80),
       (1, 1, '2019-12-11 18:30:00', 120),

       (4, 1, '2019-12-12 7:00:00', 60),
       (4, 1, '2019-12-12 11:45:00', 80),
       (5, 1, '2019-12-12 13:00:00', 80),
       (6, 1, '2019-12-12 20:00:00', 120);

INSERT INTO booking (created_at, user_id, seat_id, session_id, status)
VALUES ('2019-12-10 15:50:53', 2, 7, 1, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 8, 1, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 25, 1, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 40, 1, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 41, 1, 'BOOKED'),

       ('2019-12-10 15:50:53', 2, 1, 2, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 2, 2, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 3, 2, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 27, 2, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 28, 2, 'BOOKED'),

       ('2019-12-10 15:50:53', 2, 5, 3, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 6, 3, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 7, 3, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 8, 3, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 11, 3, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 19, 3, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 29, 3, 'BOOKED'),

       ('2019-12-10 15:50:53', 2, 8, 4, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 25, 4, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 40, 4, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 41, 4, 'BOOKED'),

       ('2019-12-10 15:50:53', 2, 1, 5, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 2, 5, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 3, 5, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 27, 5, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 28, 5, 'BOOKED'),

       ('2019-12-10 15:50:53', 2, 5, 6, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 6, 6, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 7, 6, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 8, 6, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 11, 6, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 19, 6, 'BOOKED'),
       ('2019-12-10 15:50:53', 2, 29, 6, 'BOOKED');




