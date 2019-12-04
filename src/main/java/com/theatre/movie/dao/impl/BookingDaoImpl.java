package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dto.BookingViewDto;
import com.theatre.movie.entity.Booking;
import com.theatre.movie.entity.BookingStatus;
import com.theatre.movie.persistence.ConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.theatre.movie.dao.impl.DbTablesConstants.*;

public class BookingDaoImpl extends AbstractDao<BookingViewDto> implements BookingDao {
    private static final Logger LOG = Logger.getLogger(BookingDaoImpl.class);

    public BookingDaoImpl(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public Set<Integer> getAllBookedSeatsIdByMovieSessionId(int movieSessionId) {
        String query = "SELECT seat_id FROM `booking` WHERE " + BookingTable.MOVIE_SESSION_ID + " = ?" +
                " AND (" + BookingTable.STATUS + " = 'BOOKED' OR " + BookingTable.STATUS + " = 'PAID')";
        Set<Integer> result = new HashSet<>();

        try (Connection conn = getConnection();
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
    public List<BookingViewDto> getAllActualBookingByUserId(int userId) {
        String query =
                "SELECT b." + BookingTable.BOOKING_ID + ", s." + MovieSessionTable.START_AT +
                        ", m." + MovieTable.TITLE + ", m." + MovieTable.DURATION_MIN + ", h." + HallTable.NAME +
                        ", s2." + SeatTable.SEAT_ROW + ", s2." + SeatTable.PLACE +
                        " FROM `booking` b" +
                        " JOIN `movie_session` s ON b." + BookingTable.MOVIE_SESSION_ID + " = s." + MovieSessionTable.SESSION_ID +
                        " JOIN `movie` m on s." + MovieSessionTable.MOVIE_ID + " = m." + MovieTable.MOVIE_ID +
                        " JOIN `hall` h on s." + MovieSessionTable.HALL_ID + " = h." + HallTable.HALL_ID +
                        " JOIN `seat` s2 on b." + BookingTable.SEAT_ID + " = s2." + SeatTable.SEAT_ID +
                        " WHERE s." + MovieSessionTable.START_AT + " >= CURRENT_TIMESTAMP()" +
                        " AND b." + BookingTable.USER_ID + " = ?";

        return super.getAll(query,
                ps -> ps.setInt(1, userId),
                EntityMapperProvider.BOOKING_VIEW_ENTITY_MAPPER);
    }

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

    @Override
    public boolean isBookingForMovieSessionExist(Integer movieSessionId) {
        LOG.info("Check is booking for movie session exist session id=" + movieSessionId);
        String query = "SELECT 1 FROM `booking` WHERE " + BookingTable.MOVIE_SESSION_ID + " = ?";
        return super.checkIfDataExists(query,
                ps -> ps.setInt(1, movieSessionId));

    }
}
