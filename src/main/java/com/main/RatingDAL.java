package com.main;

import java.util.List;

//Just an interface for the class. It will be implemented in dal file
public interface RatingDAL {
    List<Rating> getAllRatings();
    void addNewRating(List<Rating> rating);
    void deleteAll();
    List<Rating> getSpecificRatings(List<User> users, List<Movie> movies);
}
