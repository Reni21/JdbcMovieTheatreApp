package com.reni.hi.web.command;

import com.reni.hi.dao.CrudDao;
import com.reni.hi.web.PageData;
import com.reni.hi.entity.Movie;
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
