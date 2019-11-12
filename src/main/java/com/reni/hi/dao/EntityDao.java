package com.reni.hi.dao;

import java.util.List;

public interface EntityDao<T> {

    T getById(int id);

    List<T> getAll();

    <E> List<T> getAllInRange(E from, E to);

    int create(T entity);

    boolean update(T entity);

    boolean remove(int id);
}