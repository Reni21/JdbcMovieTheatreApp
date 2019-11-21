package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.entity.Movie;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class MovieDaoImpl extends AbstractDao<Movie> implements MovieDao {
    private static final Logger LOG = Logger.getLogger(MovieDaoImpl.class);


    @Override
    public Movie getById(int id) {
        String query = "SELECT * FROM `movie` WHERE " + DbTablesConstants.MovieTable.MOVIE_ID + " = ?";
        return super.getById(query,
                ps -> ps.setInt(1, id),
                EntityMapperProvider.MOVIE_ENTITY_MAPPER);
    }

    @Override
    public List<Movie> getAll() {
        String query = "SELECT * FROM `movie`";
        return super.getAll(query, EntityMapperProvider.MOVIE_ENTITY_MAPPER);
    }

    @Override
    public int create(Movie entity) {
        LOG.debug("Create movie: + " + entity);

        String query = "INSERT INTO `movie` ("
                + DbTablesConstants.MovieTable.TITLE + ", " + DbTablesConstants.MovieTable.DIRECTED_BY + ", "
                + DbTablesConstants.MovieTable.DESCRIPTION + ", " + DbTablesConstants.MovieTable.DURATION_MIN + ", "
                + DbTablesConstants.MovieTable.TRAILER_URL + ", " + DbTablesConstants.MovieTable.BACKGROUND_IMG_URL + ", " + DbTablesConstants.MovieTable.COVER_IMG_URL
                + ") VALUE (?, ?, ?, ?, ?, ?, ?)";
        System.out.println(query);
        return super.create(query, ps -> fillStatementWithCommonFields(ps, entity));
    }

    @Override
    public boolean update(Movie entity) {
        LOG.debug("Update movie: " + entity);

        String query = "UPDATE `movie` "
                + DbTablesConstants.MovieTable.TITLE + " = ? , " + DbTablesConstants.MovieTable.DIRECTED_BY + " = ? , "
                + DbTablesConstants.MovieTable.DESCRIPTION + " = ? , " + DbTablesConstants.MovieTable.DURATION_MIN + " = ? , "
                + DbTablesConstants.MovieTable.TRAILER_URL + " = ? , " + DbTablesConstants.MovieTable.BACKGROUND_IMG_URL + " = ? , " + DbTablesConstants.MovieTable.COVER_IMG_URL + " = ? "
                + "WHERE " + DbTablesConstants.MovieTable.MOVIE_ID + " = ?";
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
