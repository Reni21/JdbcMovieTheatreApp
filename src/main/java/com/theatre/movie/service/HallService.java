package com.theatre.movie.service;

import com.theatre.movie.dao.DaoFactory;
import com.theatre.movie.dao.HallDao;
import com.theatre.movie.entity.Hall;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class HallService {
    private HallDao hallDao = DaoFactory.getHallDao();

    public List<Hall> getAll(){
        return hallDao.getAll();
    }
}
