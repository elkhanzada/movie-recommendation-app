package com.main;

import java.util.List;

public interface MovieDAL {
    List<Movie> getAllMovies();
    void addNewMovie(Movie movie);
    void deleteAll();

    List<Movie> getSpecificMovies(String[] genres, String exclude);
}
