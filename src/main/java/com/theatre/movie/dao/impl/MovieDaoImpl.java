package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.persistence.ConnectionFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.theatre.movie.dao.impl.DbTablesConstants.MovieTable;


public class MovieDaoImpl extends AbstractDao<Movie> implements MovieDao {
    private static final Logger LOG = Logger.getLogger(MovieDaoImpl.class);

    public MovieDaoImpl(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public Movie getById(int id) {
        String query = "SELECT * FROM `movie` WHERE " + DbTablesConstants.MovieTable.MOVIE_ID + " = ?";
        return super.getById(query,
                ps -> ps.setInt(1, id),
                EntityMapperProvider.MOVIE_ENTITY_MAPPER);
    }

    @Override
    public List<Movie> getAll(int offset, int limit) {
        String query = "SELECT * FROM `movie` ORDER BY " + MovieTable.MOVIE_ID + " LIMIT ? OFFSET ?";
        return super.getAll(query, ps -> {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
        }, EntityMapperProvider.MOVIE_ENTITY_MAPPER);
    }

    @Override
    public List<MovieSimpleViewDto> getAllPreview() {
        String query = "SELECT * FROM `movie`";
        List<Movie> movies = super.getAll(query, EntityMapperProvider.MOVIE_ENTITY_MAPPER);
        List<MovieSimpleViewDto> movieSimple = new ArrayList<>(movies.size());
        movies.forEach(movie -> {
            MovieSimpleViewDto dto = new MovieSimpleViewDto(movie.getMovieId(), movie.getTitle(), movie.getDurationMinutes());
            movieSimple.add(dto);
        });
        return movieSimple;
    }

    @Override
    public long getMovieCount() {
        String query = "SELECT COUNT(*) FROM `movie`";
        long count = 0;

        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception while try to find movies count.", e);
        }
        return count;
    }

    @Override
    public int create(Movie entity) {
        LOG.debug("Create movie: + " + entity);

        String query = "INSERT INTO `movie` ("
                + MovieTable.TITLE + ", " + MovieTable.DIRECTED_BY + ", "
                + MovieTable.DESCRIPTION + ", " + MovieTable.DURATION_MIN + ", "
                + MovieTable.TRAILER_URL + ", " + MovieTable.BACKGROUND_IMG_URL + ", " + MovieTable.COVER_IMG_URL
                + ") VALUE (?, ?, ?, ?, ?, ?, ?)";
        System.out.println(query);
        return super.create(query, ps -> fillStatementWithCommonFields(ps, entity));
    }

    @Override
    public boolean update(Movie entity) {
        LOG.debug("Update movie: " + entity);

        String query = "UPDATE `movie` "
                + MovieTable.TITLE + " = ? , " + MovieTable.DIRECTED_BY + " = ? , "
                + MovieTable.DESCRIPTION + " = ? , " + MovieTable.DURATION_MIN + " = ? , "
                + MovieTable.TRAILER_URL + " = ? , " + MovieTable.BACKGROUND_IMG_URL + " = ? , " + MovieTable.COVER_IMG_URL + " = ? "
                + "WHERE " + MovieTable.MOVIE_ID + " = ?";
        return super.update(query, ps -> {
            fillStatementWithCommonFields(ps, entity);
            ps.setInt(8, entity.getMovieId());
        });
    }

    @Override
    public boolean remove(int id) {
        LOG.debug("Delete movie with id= " + id);
        String query = "DELETE FROM `movie` WHERE " + DbTablesConstants.MovieTable.MOVIE_ID + " = ?";
        return super.remove(query, ps -> ps.setInt(1, id));
    }

    private void fillStatementWithCommonFields(PreparedStatement ps, Movie movie) {
        try {
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDirectedBy());
            ps.setString(3, movie.getDescription());
            ps.setInt(4, movie.getDurationMinutes());
            ps.setString(5, movie.getTrailerUrl());
            ps.setString(6, movie.getBackgroundImgUrl());
            ps.setString(7, movie.getCoverImgUrl());
        } catch (SQLException e) {
            LOG.error("Exception while create prepared statement.", e);
        }
    }

}
