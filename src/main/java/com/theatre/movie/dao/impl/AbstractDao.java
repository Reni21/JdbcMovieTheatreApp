package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.EntityMapper;
import com.theatre.movie.dao.StatementConsumer;
import com.theatre.movie.persistence.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
    private static final Logger LOG = Logger.getLogger(AbstractDao.class);

    public T getById(String query, StatementConsumer<T> statementConsumer, EntityMapper<T> mapper) {
        T result = null;

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementConsumer.accept(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result = mapper.map(resultSet);
                }
            }

        } catch (SQLException e) {
            LOG.error("Exception while getting all entities", e);
        }
        LOG.info("Extracted entity:\n" + result);
        return result;
    }

    public boolean checkIfDataExists(String query, StatementConsumer<T> statementConsumer) {
        int result = 0;

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementConsumer.accept(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt("1");
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception while try to find match.", e);
        }
        LOG.info("Match result=" + result);
        return result == 1;
    }

    public List<T> getAll(String query, EntityMapper<T> mapper) {
        return getAll(query, null, mapper);
    }

    public List<T> getAll(String query, StatementConsumer<T> statementConsumer, EntityMapper<T> mapper) {
        List<T> result = new ArrayList<>();

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
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
        }
        return result;
    }

    public int create(String query, StatementConsumer<T> statementConsumer) {
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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
                    LOG.error("Creating movieSession failed, no ID obtained.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            LOG.error("Could not create entity.", e);
        }
        return -1;
    }

    public boolean update(String query, StatementConsumer<T> statementConsumer) {
        try {
            return updateRemove(query, statementConsumer);
        } catch (SQLException e) {
            LOG.error("Could not update entity.", e);
        }
        return false;
    }

    public boolean remove(String query, StatementConsumer<T> statementConsumer) {
        try {
            return updateRemove(query, statementConsumer);
        } catch (SQLException e) {
            LOG.error("Could not remove entity.", e);
        }
        return false;
    }

    private boolean updateRemove(String query, StatementConsumer<T> statementConsumer) throws SQLException {
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementConsumer.accept(preparedStatement);

            int result = preparedStatement.executeUpdate();
            return result == 1;
        }
    }
}

