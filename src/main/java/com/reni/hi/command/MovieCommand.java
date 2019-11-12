package com.reni.hi.command;

import com.reni.hi.dao.CrudDao;
import com.reni.hi.dto.PageDto;
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
    public PageDto execute(HttpServletRequest req) {
        long moviesCount = movieDao.getAll().size();
        req.setAttribute("count", moviesCount);
        return new PageDto("/ui/movie.jsp");
    }

}
