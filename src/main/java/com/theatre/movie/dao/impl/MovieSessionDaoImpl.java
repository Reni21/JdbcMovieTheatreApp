package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.entity.Hall;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.entity.Seat;
import com.theatre.movie.persistence.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.theatre.movie.dao.impl.DbTablesConstants.*;

public class MovieSessionDaoImpl extends AbstractDao<MovieSession> implements MovieSessionDao {
    private static final Logger LOG = Logger.getLogger(MovieSessionDaoImpl.class);

    @Override
    public List<MovieSession> getAllInRange(LocalDateTime from, LocalDateTime to) {
        LOG.info("Extract movie sessions in date range from=" + from + " to=" + to);
        String query =
                "SELECT * FROM `movie_session` s JOIN `movie` m ON s.movie_id = m.movie_id WHERE " +
                        MovieSessionTable.START_AT + " BETWEEN ? AND ?";
        return super.getAll(query,
                ps -> {
                    ps.setTimestamp(1, Timestamp.valueOf(from));
                    ps.setTimestamp(2, Timestamp.valueOf(to));
                },
                EntityMapperProvider.MOVIE_SESSION_SIMPLE_MAPPER);
    }

    @Override
    public MovieSession getById(int movieSessionId) {
        String query = "SELECT * FROM movie_session ms" +
                " JOIN movie m ON ms." + MovieSessionTable.MOVIE_ID + " = m." + MovieTable.MOVIE_ID +
                " JOIN hall h ON ms." + MovieSessionTable.MOVIE_ID + " = h." + HallTable.HALL_ID +
                " LEFT JOIN seat s on h." + HallTable.HALL_ID + " = s." + SeatTable.HALL_ID +
                " WHERE ms." + MovieSessionTable.SESSION_ID + " = ?";

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, movieSessionId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                MovieSession session = null;
                while (rs.next()) {
                    if (session == null) {
                        session = mapMovieSession(rs);
                    }
                    Seat seat = EntityMapperProvider.SEAT_ENTITY_MAPPER.map(rs);
                    session.getHall().addSeat(seat);
                }
                return session;
            }
        } catch (SQLException e) {
            LOG.error("Exception while getting full movie session entity", e);
        }
        return null;
    }

    private MovieSession mapMovieSession(ResultSet rs) throws SQLException {
        Movie movie = EntityMapperProvider.MOVIE_ENTITY_MAPPER.map(rs);
        Hall hall = EntityMapperProvider.HALL_ENTITY_MAPPER.map(rs);

        MovieSession session = new MovieSession(movie, hall,
                rs.getTimestamp(MovieSessionTable.START_AT).toLocalDateTime(),
                rs.getDouble(MovieSessionTable.PRICE));
        session.setSessionId(rs.getInt(MovieSessionTable.SESSION_ID));
        return session;
    }

}
