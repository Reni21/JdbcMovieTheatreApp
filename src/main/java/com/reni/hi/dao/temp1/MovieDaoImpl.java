package com.reni.hi.dao.temp1;

import com.reni.hi.persistence.DataSourceConnectionFactory;
import com.reni.hi.entity.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MovieDaoImpl implements MovieDao {


    public List<Movie> getAll() {
        String sql = "SELECT * FROM `movie`";
        List<Movie> result = new ArrayList<>();

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                result.add(extractMovie(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // LOG.error("Exception while getting all entities");
        }
        return result;
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        String sql = "SELECT * FROM `movie` WHERE movie_id = ?";

        try (Connection conn = DataSourceConnectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return extractMovie(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //LOG.error("Exception while getting all entities");
        }
        return null;
    }

    private Movie extractMovie(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("movie_id");
        String title = resultSet.getString("title");
        String directedBy = resultSet.getString("directed_by");
        String description = resultSet.getString("description");
        Integer duration = resultSet.getInt("duration_minutes");
        String trailerUrl = resultSet.getString("trailer_url");
        String backgroundImgPath = resultSet.getString("background_img_path");
        String coverImgPath = resultSet.getString("cover_img_path");

        Movie movie = new Movie(title, directedBy, duration);
        movie.setMovieId(id);
        movie.setDescription(description);
        movie.setTrailerUrl(trailerUrl);
        movie.setBackgroundImgPath(backgroundImgPath);
        movie.setCoverImgPath(coverImgPath);
        return movie;
    }
}
