DROP SCHEMA IF EXISTS movie_theatre;
CREATE SCHEMA movie_theatre CHAR SET utf8 COLLATE utf8_general_ci;
USE movie_theatre;

CREATE TABLE user
(
    user_id  INT NOT NULL AUTO_INCREMENT,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     ENUM ("ADMIN", "USER"),
    email    VARCHAR(255) UNIQUE,
    PRIMARY KEY (user_id)
);

CREATE TABLE hall
(
    hall_id  INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL,
    PRIMARY KEY (hall_id)
);

CREATE TABLE seat
(
    seat_id INT NOT NULL AUTO_INCREMENT,
    seat_row INT,
    place INT,
    hall_id INT,
    PRIMARY KEY (seat_id),
    FOREIGN KEY (hall_id) REFERENCES hall (hall_id),
    UNIQUE KEY `uc-seat-address` (seat_row, place, hall_id)
);

CREATE TABLE movie
(
    movie_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL UNIQUE ,
    directed_by VARCHAR(255) NOT NULL ,
    description TEXT,
    duration_minutes INT,
    trailer_url VARCHAR(255),
    background_img_url VARCHAR(255) UNIQUE ,
    cover_img_url VARCHAR(255) UNIQUE ,
    PRIMARY KEY (movie_id)
);

CREATE TABLE movie_session
(
    session_id INT NOT NULL AUTO_INCREMENT,
    movie_id INT,
    hall_id INT,
    start_at TIMESTAMP,
    price DOUBLE,
    PRIMARY KEY (session_id),
    FOREIGN KEY (movie_id) REFERENCES movie (movie_id),
    FOREIGN KEY (hall_id) REFERENCES hall (hall_id)
);

CREATE TABLE booking
(
    booking_id INT NOT NULL AUTO_INCREMENT,
    created_at TIMESTAMP,
    user_id INT,
    seat_id INT,
    session_id INT,
    status ENUM("PAID", "BOOKED", "CANCELED"),
    PRIMARY KEY (booking_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (seat_id) REFERENCES seat (seat_id),
    FOREIGN KEY (session_id) REFERENCES movie_session (session_id),
    UNIQUE KEY `uc-seat-session` (seat_id, session_id)
);