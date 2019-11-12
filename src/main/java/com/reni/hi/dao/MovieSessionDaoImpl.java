package com.reni.hi.dao;

import com.reni.hi.entity.MovieSession;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class MovieSessionDaoImpl extends AbstractDao<MovieSession> {
    private static final Logger LOG = Logger.getLogger(MovieSessionDaoImpl.class);

    private static final String SESSION_ID = "session_id";
    private static final String MOVIE_ID = "movie_id";
    private static final String HALL_ID = "hall_id";
    private static final String START_AT = "start_at";
    private static final String PRICE = "price";

    @Override
    public MovieSession getById(int id) {
        String query = "SELECT * FROM `movie_session` WHERE " + SESSION_ID + " = ?";
        return super.getById(query,
                pStatement -> pStatement.setInt(1, id),
                getMapper());
    }

    @Override
    public List<MovieSession> getAll() {
        String query = "SELECT * FROM `movie_session`";
        return super.getAll(query, getMapper());
    }

    @Override
    public <E> List<MovieSession> getAllInRange(E from, E to) {
        Timestamp searchFrom = Timestamp.valueOf((LocalDateTime) from);
        LOG.info("Sessions search start from dateTime: " + from);
        Timestamp searchTo = Timestamp.valueOf((LocalDateTime) to);
        LOG.info("Sessions search and on dateTime: " + to);

        String query = "SELECT * FROM `movie_session` WHERE " + START_AT + " BETWEEN ? AND ?";
        return super.getAllInRange(query,
                pStatement -> {
                    pStatement.setTimestamp(1, searchFrom);
                    pStatement.setTimestamp(2, searchTo);
                },
                getMapper());
    }

    @Override
    public int create(MovieSession entity) {
        LOG.debug("Create movie session: + " + entity);

        String query = "INSERT INTO `movie_session` ("
                        + MOVIE_ID + ", " + HALL_ID + ", "
                        + START_AT + ", " + PRICE
                        + ") VALUE (?, ?, ?, ?)";
        System.out.println(query);
        return super.create(query, pStatement -> {
            pStatement.setInt(1, entity.getMovieId());
            pStatement.setInt(2, entity.getHallId());
            pStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartAt()));
            pStatement.setDouble(4, entity.getPrice());
        });
    }

    @Override
    public boolean update(MovieSession entity) {
        LOG.debug("Update movie session: " + entity);

        String query = "UPDATE `movie_session` "
                        + MOVIE_ID + " = ? , " + HALL_ID + " = ? , "
                        + START_AT + " = ? , " + PRICE + " = ? , "
                        + "WHERE " + SESSION_ID + " = ?";
        return super.update(query, pStatement -> {
            pStatement.setInt(1, entity.getMovieId());
            pStatement.setInt(2, entity.getHallId());
            pStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartAt()));
            pStatement.setDouble(4, entity.getPrice());
            pStatement.setInt(5, entity.getSessionId());
        });
    }

    @Override
    public boolean remove(int id) {
        LOG.debug("Delete movie session with id= " + id);

        String query = "DELETE FROM `movie_session` WHERE " + SESSION_ID + " = ?";
        return update(query, pStatement -> pStatement.setInt(1, id));
    }

    private EntityMapper<MovieSession> getMapper() {
        return resultSet -> {
            MovieSession session = new MovieSession(
                    resultSet.getInt(MOVIE_ID),
                    resultSet.getInt(HALL_ID),
                    resultSet.getTimestamp(START_AT).toLocalDateTime(),
                    resultSet.getDouble(PRICE));
            session.setSessionId(resultSet.getInt(SESSION_ID));
            return session;
        };
    }

}
