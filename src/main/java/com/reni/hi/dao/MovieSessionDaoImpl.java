package com.reni.hi.dao;

import com.reni.hi.entity.MovieSession;
import com.reni.hi.persistence.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieSessionDaoImpl {
    private static final Logger LOG = Logger.getLogger(MovieSessionDaoImpl.class);

    public List<MovieSession> getAll() {
        return null;
    }

    public List<MovieSession> getAllSessionsInRange(LocalDateTime searchFrom, LocalDateTime searchTo){
        Timestamp from = Timestamp.valueOf(searchFrom);
        LOG.info("Sessions search start from dateTime: " + from);
        Timestamp to = Timestamp.valueOf(searchTo);
        LOG.info("Sessions search and on dateTime: " + to);

        String sql = "SELECT * FROM `movie_session` WHERE start_at BETWEEN ? AND ?";
        List<MovieSession> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, from);
            preparedStatement.setTimestamp(2, to);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(extractMovieSession(resultSet));
            }

        } catch (SQLException e) {
            LOG.error("Exception while getting sessions in range");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                LOG.error("Exception while closing resultSet");
            }
        }
        return result;

    }

    private MovieSession extractMovieSession(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("session_id");
        int movieId = resultSet.getInt("movie_id");
        int hallId = resultSet.getInt("hall_id");
        LocalDateTime startAt = resultSet.getTimestamp("start_at").toLocalDateTime();
        Double price = resultSet.getDouble("price");

        MovieSession session = new MovieSession(movieId, hallId, startAt, price);
        session.setSessionId(id);
        return session;
    }

}
