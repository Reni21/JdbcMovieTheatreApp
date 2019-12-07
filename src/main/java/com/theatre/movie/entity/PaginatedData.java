package com.theatre.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaginatedData<T> {
    private List<T> data;
    private int pagesCount;
    private int currentPage;
}
