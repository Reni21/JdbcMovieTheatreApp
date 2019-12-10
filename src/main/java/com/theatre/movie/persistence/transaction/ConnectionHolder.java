package com.theatre.movie.persistence.transaction;

import java.sql.Connection;

/**
 * The {@code ConnectionHolder} class store connection which use by
 * {@link com.theatre.movie.persistence.transaction.TransactionManager} class
 * and provide methods for <tt>get</tt> and <tt>set</tt> it
 * Properties: <b>currentConnection</b>
 *
 * @author Hlushchenko Renata
 * @see java.lang.ThreadLocal
 */

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
