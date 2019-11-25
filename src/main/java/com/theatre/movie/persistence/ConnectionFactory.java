package com.theatre.movie.persistence;

import java.sql.Connection;

public interface ConnectionFactory {

    public Connection getConnection();
}
