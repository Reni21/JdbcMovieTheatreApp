package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.persistence.ConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.theatre.movie.dao.impl.DbTablesConstants.MovieSessionTable;

public class MovieSessionDaoImpl extends AbstractDao<MovieSession> implements MovieSessionDao {
    private static final Logger LOG = Logger.getLogger(MovieSessionDaoImpl.class);

    public MovieSessionDaoImpl(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public List<MovieSession> getAllInRange(LocalDateTime from, LocalDateTime to) {
        LOG.info("Extract movie sessions in date range from=" + from + " to=" + to);
        String query =
                "SELECT * FROM `movie_session` WHERE " + MovieSessionTable.START_AT + " BETWEEN ? AND ?";
        return super.getAll(query,
                ps -> {
                    ps.setTimestamp(1, Timestamp.valueOf(from));
                    ps.setTimestamp(2, Timestamp.valueOf(to));
                },
                EntityMapperProvider.MOVIE_SESSION_ENTITY_MAPPER);
    }

    @Override
    public MovieSession getById(int movieSessionId) {
        LOG.info("Extract movie sessions with id=" + movieSessionId);
        String query = "SELECT * FROM `movie_session` WHERE " + MovieSessionTable.SESSION_ID + " = ?";
        return super.getById(query,
                ps -> ps.setInt(1, movieSessionId),
                EntityMapperProvider.MOVIE_SESSION_ENTITY_MAPPER);
    }

}
