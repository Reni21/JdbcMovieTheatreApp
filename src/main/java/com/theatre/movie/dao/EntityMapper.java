package com.theatre.movie.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface EntityMapper<T> {
    T map(ResultSet resultSet) throws SQLException;
}
