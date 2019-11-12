package com.reni.hi.dao.impl;

import com.reni.hi.dao.EntityMapper;
import com.reni.hi.dao.MovieDao;
import com.reni.hi.entity.Movie;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class MovieDaoImpl extends AbstractDao<Movie> implements MovieDao {
    private static final Logger LOG = Logger.getLogger(MovieDaoImpl.class);

    private static final String MOVIE_ID = "movie_id";
    private static final String TITLE = "title";
    private static final String DIRECTED_BY = "directed_by";
    private static final String DESCRIPTION = "description";
    private static final String DURATION_MIN = "duration_minutes";
    private static final String TRAILER_URL = "trailer_url";
    private static final String BACKGROUND_IMG_PATH = "background_img_path";
    private static final String COVER_IMG_PATH = "cover_img_path";

    private static final EntityMapper<Movie> MOVIE_ENTITY_MAPPER = resultSet -> {
        Movie movie = new Movie(resultSet.getString(TITLE),
                resultSet.getString(DIRECTED_BY),
                resultSet.getInt(DURATION_MIN));
        movie.setDescription(resultSet.getString(DESCRIPTION));
        movie.setTrailerUrl(resultSet.getString(TRAILER_URL));
        movie.setBackgroundImgPath(resultSet.getString(BACKGROUND_IMG_PATH));
        movie.setCoverImgPath(resultSet.getString(COVER_IMG_PATH));
        return movie;
    };

    @Override
    public Movie getById(int id) {
        String query = "SELECT * FROM `movie` WHERE " + MOVIE_ID + " = ?";
        return super.getById(query,
                ps -> ps.setInt(1, id),
                MOVIE_ENTITY_MAPPER);
    }

    @Override
    public List<Movie> getAll() {
        String query = "SELECT * FROM `movie`";
        return super.getAll(query, MOVIE_ENTITY_MAPPER);
    }

    @Override
    public int create(Movie entity) {
        LOG.debug("Create movie: + " + entity);

        String query = "INSERT INTO `movie` ("
                + TITLE + ", " + DIRECTED_BY + ", "
                + DESCRIPTION + ", " + DURATION_MIN + ", "
                + TRAILER_URL + ", " + BACKGROUND_IMG_PATH + ", " + COVER_IMG_PATH
                + ") VALUE (?, ?, ?, ?, ?, ?, ?)";
        System.out.println(query);
        return super.create(query, ps -> fillStatementWithCommonFields(ps, entity));
    }

    @Override
    public boolean update(Movie entity) {
        LOG.debug("Update movie: " + entity);

        String query = "UPDATE `movie` "
                + TITLE + " = ? , " + DIRECTED_BY + " = ? , "
                + DESCRIPTION + " = ? , " + DURATION_MIN + " = ? , "
                + TRAILER_URL + " = ? , " + BACKGROUND_IMG_PATH + " = ? , " + COVER_IMG_PATH + " = ? "
                + "WHERE " + MOVIE_ID + " = ?";
        return super.update(query, ps -> {
            fillStatementWithCommonFields(ps, entity);
            ps.setInt(8, entity.getMovieId());
        });
    }

    @Override
    public boolean remove(int id) {
        LOG.debug("Delete movie with id= " + id);
        String query = "DELETE FROM `movie` WHERE " + MOVIE_ID + " = ?";
        return super.remove(query, ps -> ps.setInt(1, id));
    }

    private void fillStatementWithCommonFields(PreparedStatement ps, Movie movie) {
        try {
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDirectedBy());
            ps.setString(3, movie.getDescription());
            ps.setInt(4, movie.getDurationMinutes());
            ps.setString(5, movie.getTrailerUrl());
            ps.setString(6, movie.getBackgroundImgPath());
            ps.setString(7, movie.getCoverImgPath());
        } catch (SQLException e) {
            LOG.error("Exception while create prepared statement.", e);
        }
    }

}
