package com.theatre.movie.persistence;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection getConnection();
}
