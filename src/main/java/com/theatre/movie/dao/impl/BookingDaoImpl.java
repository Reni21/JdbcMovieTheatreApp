package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dto.BookingDto;
import com.theatre.movie.entity.Booking;
import com.theatre.movie.entity.BookingStatus;
import com.theatre.movie.persistence.DataSourceConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static com.theatre.movie.dao.impl.DbTablesConstants.BookingTable;

public class BookingDaoImpl extends AbstractDao<BookingDto> implements BookingDao {
    private static final Logger LOG = Logger.getLogger(BookingDaoImpl.class);


//    @Override
//    public List<Booking> getAllByMovieSessionId(int movieSessionId) {
//        LOG.info("Get Seat by hall id: " + BookingTable.MOVIE_SESSION_ID + "=" + movieSessionId);
//        String query = "SELECT * FROM `booking` WHERE " + BookingTable.MOVIE_SESSION_ID + " = ?";
//        return super.getAll(query,
//                ps -> ps.setInt(1, movieSessionId),
//                EntityMapperProvider.BOOKING_ENTITY_MAPPER);
//    }

    @Override
    public Set<Integer> getAllBookedSeatsIdByMovieSessionId(int movieSessionId) {
        String query = "SELECT seat_id FROM `booking` WHERE " + BookingTable.MOVIE_SESSION_ID + " = ?" +
                " AND (" + BookingTable.STATUS + " = 'BOOKED' OR " + BookingTable.STATUS + " = 'PAID')";
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


//    @Override
//    public List<Booking> getAllActualByUserId(int userId) {
//        String query = "SELECT b.* FROM `booking` b INNER JOIN `movie_session` s ON b." + BookingTable.MOVIE_SESSION_ID + " = s.session_id" +
//                "WHERE s.start_at >= CURRENT_TIMESTAMP() AND b." + BookingTable.USER_ID + " = ?;";
//        return super.getAll(query,
//                ps -> ps.setInt(1, userId),
//                EntityMapperProvider.BOOKING_ENTITY_MAPPER);
//    }

    @Override
    public Booking create(Booking booking) {
        LOG.debug("Create booking: + " + booking);

        String query = "INSERT INTO `booking` ("
                + BookingTable.CREATED_AT + ", " + BookingTable.USER_ID + ", "
                + BookingTable.SEAT_ID + ", " + BookingTable.MOVIE_SESSION_ID + ", "
                + BookingTable.STATUS
                + ") VALUE (?, ?, ?, ?, ?)";
        System.out.println(query);
        int id = super.create(query, ps -> {
            ps.setTimestamp(1, Timestamp.valueOf(booking.getCreatedAt()));
            ps.setInt(2, booking.getUserId());
            ps.setInt(3, booking.getBookedSeatId());
            ps.setInt(4, booking.getMovieSessionId());
            ps.setString(5, BookingStatus.BOOKED.toString());
        });
        booking.setBookingId(id);
        return booking;
    }
}
