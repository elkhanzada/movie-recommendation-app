package com.main;

import java.util.List;

public interface RatingDAL {
    List<Rating> getAllRatings();
    void addNewRating(List<Rating> rating);
    void deleteAll();
    List<Rating> getSpecificRatings(List<User> users, List<Movie> movies);
}
