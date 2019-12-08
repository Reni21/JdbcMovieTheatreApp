package com.theatre.movie.persistence.transaction;

import java.sql.Connection;

public class ConnectionHolder {

    private static ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    public static void setCurrentConnection(Connection connection) {
        currentConnection.set(connection);
    }

    public static Connection getCurrentConnection() {
        return currentConnection.get();
    }

    public static void removeCurrentConnection() {
        currentConnection.remove();
    }
}
