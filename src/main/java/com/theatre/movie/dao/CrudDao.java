package com.theatre.movie.dao;

import java.util.List;

public interface CrudDao<T> {

    T getById(int id);

    List<T> getAll(int offset, int limit);

    int create(T entity);

    boolean update(T entity);

    boolean remove(int id);
}