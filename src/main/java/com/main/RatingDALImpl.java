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
        if(users.size()+movies.size()==MainController.numOfUsers+MainController.numOfMovies)
            return MainController.allRatings;
        for(Movie movie: movies){
            Query query = new Query();
            query.addCriteria(Criteria.where("movieId").is(movie.getMovieID()));
            Rating rt = mt.findOne(query,Rating.class);
            if(rt!=null) {
                for (User user : users) {
                    if (rt.getUserIds().contains(user.getUserId())) {
                        ratings.add(rt);
                        break;
                    }
                }
            }
        }
        return ratings;
    }
}
