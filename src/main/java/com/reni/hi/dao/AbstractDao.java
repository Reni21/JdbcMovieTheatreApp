package com.reni.hi.dao;

import com.reni.hi.persistence.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> implements EntityDao<T> {
    private static final Logger LOG = Logger.getLogger(AbstractDao.class);

    public T getById(String query, StatementMapper<T> statementMapper, EntityMapper<T> mapper) {
        T result = null;

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementMapper.map(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result = mapper.map(resultSet);
                }
            }

        } catch (SQLException e) {
            LOG.error("Exception while getting all entities", e);
        }
        return result;
    }

    public List<T> getAll(String query, EntityMapper<T> mapper) {
        List<T> result = new ArrayList<>();

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                result.add(entity);
            }

        } catch (SQLException e) {
            LOG.error("Exception while getting all entities", e);
        }

        return result;
    }

    public List<T> getAllInRange(String query, StatementMapper<T> statementMapper, EntityMapper<T> mapper) {
        List<T> result = new ArrayList<>();

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementMapper.map(preparedStatement);

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

    public int create(String query, StatementMapper<T> statementMapper) {
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statementMapper.map(preparedStatement);

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

    public boolean update(String query, StatementMapper<T> statementMapper) {
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            statementMapper.map(preparedStatement);

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            LOG.error("Could not update entity.", e);
        }
        return false;
    }
}

