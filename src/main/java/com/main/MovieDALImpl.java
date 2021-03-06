package com.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Pattern;

@Repository
public class MovieDALImpl implements MovieDAL{
    @Autowired
    private MongoTemplate mt;

    @Override
    public List<Movie> getAllMovies() {
        return mt.findAll(Movie.class);
    }

    @Override
    public void addNewMovie(Movie movie) {
        mt.save(movie);
    }

    @Override
    public void deleteAll() {
        mt.getDb().drop();
    }

    @Override
    public Movie findMovie(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(name.toLowerCase()));
        return mt.findOne(query,Movie.class);
    }

    @Override
    public List<Movie> getSpecificMovies(String[] genres, String exclude) {

        List<Movie> movies = new ArrayList<>();
            for (String genre : genres) {
                Query query = new Query();
                if(genre!=null)
                    query.addCriteria(Criteria.where("genres").regex(genre).andOperator(Criteria.where("title").ne(exclude)));
                List<Movie> temp =mt.find(query,Movie.class);
                movies.addAll(temp);
            }
       Set<Movie> set = new HashSet<>(movies);
       movies.clear();
       movies.addAll(set);
        return movies;
    }

    @Override
    public List<Movie> getSuggestions(String key) {
        Query query = new Query();
        Pattern pattern = Pattern.compile(Pattern.quote(key));
        query.addCriteria(Criteria.where("title").regex(pattern));
        return mt.find(query,Movie.class);
    }
}
