package com.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RatingDALImpl implements RatingDAL{
    @Autowired
    MongoTemplate mt;
    @Override
    public List<Rating> getAllRatings() {
        return mt.findAll(Rating.class);
    }

    @Override
    public void addNewRating(List<Rating> rating) {
        mt.insertAll(rating);
    }

    @Override
    public void deleteAll() {
        mt.getDb().drop();
    }

    @Override
    public List<Rating> getSpecificRatings(List<User> users, List<Movie> movies) {
        List<Rating> ratings = new ArrayList<>();
        for(Movie movie: movies){
            Query query = new Query();
            query.addCriteria(Criteria.where("movieId").is(movie.getMovieID()));
            ratings.addAll(mt.find(query,Rating.class));
        }
        Set<Rating> set = new HashSet<>(ratings);
        ratings.clear();
        ratings.addAll(set);
        int i = 0;
        while(i<ratings.size()) {
            boolean isFound = false;
            for (User user : users) {
                if (ratings.get(i).getUserId() == user.getUserId()) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) ratings.remove(i);
            i++;
        }
        return ratings;
    }
}
