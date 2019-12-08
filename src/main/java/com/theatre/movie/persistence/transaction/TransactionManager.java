package com.theatre.movie.persistence.transaction;

import com.theatre.movie.persistence.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class TransactionManager {
    private static final Logger LOG = Logger.getLogger(TransactionManager.class);

    private final ConnectionFactory connectionFactory;

    public void begin() {
        Connection currentConnection = connectionFactory.getConnection();
        try {
            currentConnection.setAutoCommit(false);
            ConnectionHolder.setCurrentConnection(currentConnection);
            LOG.debug("Begin new transaction, set current connection for thread: " + Thread.currentThread().getName());
        } catch (SQLException e) {
            LOG.error("Can't begin transaction: ", e);
            throw new RuntimeException(e);
        }
    }

    public void commit() {
        try {
            ConnectionHolder.getCurrentConnection().commit();
            LOG.debug("Commit transaction");
        } catch (SQLException e) {
            LOG.error("Can't commit transaction: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(ConnectionHolder.getCurrentConnection());
            ConnectionHolder.removeCurrentConnection();
            LOG.debug("Remove current connection for thread: " + Thread.currentThread().getName());
        }

    }

    public void rollback() {
        try {
            ConnectionHolder.getCurrentConnection().rollback();
            LOG.debug("Rollback transaction");
        } catch (SQLException e) {
            LOG.error("Can't rollback transaction: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(ConnectionHolder.getCurrentConnection());
            ConnectionHolder.removeCurrentConnection();
            LOG.debug("Remove current connection for thread: " + Thread.currentThread().getName());
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOG.error("Can't close connection.", e);
        }
    }
}
