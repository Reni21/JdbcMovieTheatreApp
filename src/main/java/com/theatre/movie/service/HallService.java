package com.theatre.movie.service;

import com.theatre.movie.dao.HallDao;
import com.theatre.movie.entity.Hall;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * The {@code HallService} class provides methods for manage halls information
 * represented by {@link com.theatre.movie.entity.Hall} class
 * Properties: <b>hallDao</b>
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.dao.HallDao
 */

@AllArgsConstructor
public class HallService {
    private HallDao hallDao;

    public List<Hall> getAll() {
        return hallDao.getAll();
    }
}
