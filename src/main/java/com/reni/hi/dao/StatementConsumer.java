package com.reni.hi.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementConsumer<T> {
    void accept(PreparedStatement ps) throws SQLException;
}
