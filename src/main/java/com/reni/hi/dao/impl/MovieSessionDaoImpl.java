package com.reni.hi.dao.impl;

import com.reni.hi.dao.EntityMapper;
import com.reni.hi.dao.MovieSessionDao;
import com.reni.hi.entity.MovieSession;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class MovieSessionDaoImpl extends AbstractDao<MovieSession> implements MovieSessionDao {
    private static final Logger LOG = Logger.getLogger(MovieSessionDaoImpl.class);

    private static final String SESSION_ID = "session_id";
    private static final String MOVIE_ID = "movie_id";
    private static final String HALL_ID = "hall_id";
    private static final String START_AT = "start_at";
    private static final String PRICE = "price";

    private static final EntityMapper<MovieSession> MOVIE_SESSION_MAPPER = resultSet -> {
        MovieSession session = new MovieSession(
                resultSet.getInt(MOVIE_ID),
                resultSet.getInt(HALL_ID),
                resultSet.getTimestamp(START_AT).toLocalDateTime(),
                resultSet.getDouble(PRICE));
        session.setSessionId(resultSet.getInt(SESSION_ID));
        return session;
    };

    @Override
    public int create(MovieSession entity) {
        LOG.debug("Create movie session: + " + entity);

        String query = "INSERT INTO `movie_session` ("
                + MOVIE_ID + ", " + HALL_ID + ", "
                + START_AT + ", " + PRICE
                + ") VALUE (?, ?, ?, ?)";
        return super.create(query, ps -> {
            ps.setInt(1, entity.getMovieId());
            ps.setInt(2, entity.getHallId());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getStartAt()));
            ps.setDouble(4, entity.getPrice());
        });
    }

    @Override
    public List<MovieSession> getAllInRange(LocalDateTime from, LocalDateTime to) {
        String query = "SELECT * FROM `movie_session` WHERE " + START_AT + " BETWEEN ? AND ?";
        return super.getAll(query,
                ps -> {
                    ps.setTimestamp(1, Timestamp.valueOf(from));
                    ps.setTimestamp(2, Timestamp.valueOf(to));
                },
                MOVIE_SESSION_MAPPER);
    }

    @Override
    public MovieSession getById(int id) {
        String query = "SELECT * FROM `movie_session` WHERE " + SESSION_ID + " = ?";
        return super.getById(query,
                ps -> ps.setInt(1, id),
                MOVIE_SESSION_MAPPER);
    }

    @Override
    public boolean remove(int id) {
        LOG.debug("Delete movie session with id= " + id);

        String query = "DELETE FROM `movie_session` WHERE " + SESSION_ID + " = ?";
        return super.remove(query, ps -> ps.setInt(1, id));
    }
}
