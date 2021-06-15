package com.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//This class is to query rating information from the db
public class Rating {
    private HashMap<Integer,Integer> userIds;
    private int score;
    private HashMap<Integer, Integer> userScores;
    private int movieId;
    private int votes;

    public void setVotes(int votes) {
        this.votes = votes;
    }
    public double getAverage(List<User> users){
        double score = getScore(users);
        double votes = getVotes(users);
        return score/votes;
    }
    public double getAverage(){
        double score = getScore();
        double votes = getVotes();
        return score/votes;
    }

    public int getVotes() {
        return votes;
    }
    public int getVotes(List<User> users) {
        int votes = 0;
        for(User user: users){
            if(userIds.get(user.getUserId())!=null)
                votes++;
        }
        return votes;
    }
    public Rating(int movieId, int score) {
        this.movieId = movieId;
        this.score = score;
        this.votes = 1;
        userIds = new HashMap<>();
        userScores = new HashMap<>();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HashMap<Integer, Integer> getUserIds() {
        return userIds;
    }
    public int getScore() {
        return score;
    }
    public int getScore(List<User> users) {
        int score = 0;
        for(User user: users){
            if(userScores.get(user.getUserId())!=null)
                score+=userScores.get(user.getUserId());
        }
        return score;
    }

    public int getMovieId() {
        return movieId;
    }

    @Override
    public boolean equals(Object other){
        Rating rating = (Rating)other;
        return this.getMovieId() == rating.getMovieId();
    }
    @Override
    public int hashCode(){
        return 0;
    }

    public void addUser(int id) {
        userIds.put(id,id);
    }
    public void addUserScore(int id, int score) {
        userScores.put(id,score);
    }
}
