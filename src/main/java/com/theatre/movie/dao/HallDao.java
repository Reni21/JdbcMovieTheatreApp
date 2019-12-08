package com.theatre.movie.dao;

import com.theatre.movie.entity.Hall;

import java.util.List;

public interface HallDao {

    Hall getHallById(int id);

    List<Hall> getAll();
}
