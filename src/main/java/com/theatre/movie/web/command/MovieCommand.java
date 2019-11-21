package com.theatre.movie.web.command;

import com.theatre.movie.dao.CrudDao;
import com.theatre.movie.web.PageData;
import com.theatre.movie.entity.Movie;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

@Setter
public class MovieCommand implements Command {
    private CrudDao<Movie> movieDao;

    public MovieCommand(CrudDao<Movie> movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public PageData execute(HttpServletRequest req) {
        long moviesCount = movieDao.getAll().size();
        req.setAttribute("count", moviesCount);
        return new PageData("/ui/movie.jsp");
    }

}
