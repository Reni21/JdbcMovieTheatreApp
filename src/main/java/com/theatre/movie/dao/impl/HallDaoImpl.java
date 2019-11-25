package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.HallDao;
import com.theatre.movie.entity.Hall;
import com.theatre.movie.entity.Seat;
import com.theatre.movie.persistence.ConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.theatre.movie.dao.impl.DbTablesConstants.HallTable;
import static com.theatre.movie.dao.impl.DbTablesConstants.SeatTable;

public class HallDaoImpl extends AbstractDao<Hall> implements HallDao {
    private static final Logger LOG = Logger.getLogger(HallDaoImpl.class);

    public HallDaoImpl(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public Hall getHallById(int id) {
        LOG.info("Get hall by id=" + id);
//        String query = "SELECT * FROM `hall` WHERE " + HallTable.HALL_ID + " = ?";
        String query = "SELECT * FROM hall h" +
                " LEFT JOIN seat s on h." + HallTable.HALL_ID + " = s." + SeatTable.HALL_ID +
                " WHERE h." + HallTable.HALL_ID + " = ?";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                Hall hall = null;
                while (rs.next()) {
                    if (hall == null) {
                        hall = EntityMapperProvider.HALL_ENTITY_MAPPER.map(rs);
                    }
                    Seat seat = EntityMapperProvider.SEAT_ENTITY_MAPPER.map(rs);
                    hall.addSeat(seat);
                }
                return hall;
            }
        } catch (SQLException e) {
            LOG.error("Exception while getting full hall entity", e);
        }
        return null;

//        return super.getById(query,
//                ps -> ps.setInt(1, id),
//                EntityMapperProvider.HALL_ENTITY_MAPPER);
    }
}