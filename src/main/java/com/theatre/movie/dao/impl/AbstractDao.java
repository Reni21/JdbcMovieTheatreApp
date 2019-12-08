package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.EntityMapper;
import com.theatre.movie.dao.StatementConsumer;
import com.theatre.movie.persistence.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public abstract class AbstractDao<T> {
    private static final Logger LOG = Logger.getLogger(AbstractDao.class);

    private ConnectionFactory connectionFactory;

    public T getById(String query, StatementConsumer<T> statementConsumer, EntityMapper<T> mapper) {
        T result = null;

        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementConsumer.accept(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result = mapper.map(resultSet);
                }
            }
            LOG.info("Extracted entity:\n" + result);

        } catch (SQLException e) {
            LOG.error("Exception while get entity by id", e);
            throw new RuntimeException(e);
        } finally {
            closeAutocommitConnection(conn);
        }
        return result;
    }

    public boolean checkIfDataExists(String query, StatementConsumer<T> statementConsumer) {
        int result = 0;

        Connection conn = getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementConsumer.accept(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("1");
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception while try to find match.", e);
        } finally {
            closeAutocommitConnection(conn);
        }
        LOG.info("Match result=" + result);
        return result == 1;
    }

    public List<T> getAll(String query, EntityMapper<T> mapper) {
        return getAll(query, null, mapper);
    }

    public List<T> getAll(String query, StatementConsumer<T> statementConsumer, EntityMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        Connection conn = getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            if (statementConsumer != null) {
                statementConsumer.accept(preparedStatement);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    T entity = mapper.map(resultSet);
                    result.add(entity);
                }
            }

        } catch (SQLException e) {
            LOG.error("Exception while getting all entities.", e);
            throw new RuntimeException(e);
        } finally {
            closeAutocommitConnection(conn);
        }
        return result;
    }

    public int create(String query, StatementConsumer<T> statementConsumer) {
        Connection conn = getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statementConsumer.accept(preparedStatement);

            int result = preparedStatement.executeUpdate();
            if (result != 1) {
                LOG.error("Could not create entity.");
                return -1;
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    LOG.info("Id for new entity record id=" + generatedId);
                    return generatedId;
                } else {
                    LOG.error("Creating entity failed, no ID obtained.");
                    throw new RuntimeException("Creating entity failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            LOG.error("Could not create entity.", e);
            throw new RuntimeException(e);
        } finally {
            closeAutocommitConnection(conn);
        }
    }

    public boolean update(String query, StatementConsumer<T> statementConsumer) {
        try {
            return updateRemove(query, statementConsumer);
        } catch (SQLException e) {
            LOG.error("Could not update entity.", e);
            throw new RuntimeException(e);
        }
    }

    public boolean remove(String query, StatementConsumer<T> statementConsumer) {
        try {
            return updateRemove(query, statementConsumer);
        } catch (SQLException e) {
            LOG.error("Could not remove entity.", e);
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection() {
        return connectionFactory.getConnection();
    }

    private boolean updateRemove(String query, StatementConsumer<T> statementConsumer) throws SQLException {
        Connection conn = getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementConsumer.accept(preparedStatement);

            int result = preparedStatement.executeUpdate();
            return result == 1;
        } finally {
            closeAutocommitConnection(conn);
        }
    }

    protected void closeAutocommitConnection(Connection connection) {
        try {
            if (connection != null && connection.getAutoCommit()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOG.error("Could not close connection.", e);
        }
    }
}

