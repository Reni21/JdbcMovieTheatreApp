package com.theatre.movie.dao.impl;

public class DbTablesConstants {

    static class MovieTable {
        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String DIRECTED_BY = "directed_by";
        public static final String DESCRIPTION = "description";
        public static final String DURATION_MIN = "duration_minutes";
        public static final String TRAILER_URL = "trailer_url";
        public static final String BACKGROUND_IMG_URL = "background_img_url";
        public static final String COVER_IMG_URL = "cover_img_url";
    }

    static class MovieSessionTable {
        public static final String SESSION_ID = "session_id";
        public static final String MOVIE_ID = "movie_id";
        public static final String HALL_ID = "hall_id";
        public static final String START_AT = "start_at";
        public static final String PRICE = "price";
    }

    static class SeatTable {
        public static final String SEAT_ID = "seat_id";
        public static final String SEAT_ROW = "seat_row";
        public static final String PLACE = "place";
        public static final String HALL_ID = "hall_id";
    }

    static class HallTable {
        public static final String HALL_ID = "hall_id";
        public static final String NAME = "name";
    }

    static class UserTable {
        public static final String USER_ID = "user_id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String ROLE = "role";
        public static final String EMAIL = "email";
    }

    static class BookingTable {
        public static final String BOOKING_ID = "booking_id";
        public static final String CREATED_AT = "created_at";
        public static final String USER_ID = "user_id";
        public static final String SEAT_ID = "seat_id";
        public static final String MOVIE_SESSION_ID = "session_id";
        public static final String STATUS = "status";
    }
}
