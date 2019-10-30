package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.entity.Booking;
import com.theatre.movie.persistence.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.theatre.movie.dao.impl.DbTablesConstants.BookingTable;

public class BookingDaoImpl extends AbstractDao<Booking> implements BookingDao {
    private static final Logger LOG = Logger.getLogger(BookingDaoImpl.class);


    @Override
    public Booking getById(int id) {
        return null;
    }

    @Override
    public List<Booking> getAll() {
        return null;
    }

    @Override
    public int create(Booking entity) {
        return 0;
    }

    @Override
    public boolean update(Booking entity) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public List<Booking> getAllByMovieSessionId(int movieSessionId) {
        LOG.info("Get Seat by hall id: " + BookingTable.MOVIE_SESSION_ID + "=" + movieSessionId);
        String query = "SELECT * FROM `booking` WHERE " + BookingTable.MOVIE_SESSION_ID + " = ?";
        return super.getAll(query,
                ps -> ps.setInt(1, movieSessionId),
                EntityMapperProvider.BOOKING_ENTITY_MAPPER);
    }

    @Override
    public Set<Integer> getAllBookedSeatsIdByMovieSessionId(int movieSessionId) {
        String query = "SELECT booking.seat_id FROM `booking` WHERE " + BookingTable.MOVIE_SESSION_ID + " = ?";
        Set<Integer> result = new HashSet<>();

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, movieSessionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getInt(BookingTable.SEAT_ID));
                }
            }

        } catch (SQLException e) {
            LOG.error("Exception while getting booked seats id.\n", e);
        }
        return result;
    }


    @Override
    public List<Booking> getAllActualByUserId(int userId) {
        String query = "SELECT b.* FROM `booking` b INNER JOIN `movie_session` s ON b." + BookingTable.MOVIE_SESSION_ID + " = s.session_id" +
                "WHERE s.start_at >= CURRENT_TIMESTAMP() AND b." + BookingTable.USER_ID + " = ?;";
        return super.getAll(query,
                ps -> ps.setInt(1, userId),
                EntityMapperProvider.BOOKING_ENTITY_MAPPER);
    }
}
