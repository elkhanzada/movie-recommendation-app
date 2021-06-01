package com.main;

public class Rating {
    private int userId;
    private int score;
    private int movieId;
    private int votes;

    public void setVotes(int votes) {
        this.votes = votes;
    }
    public double getAverage(){
        double score = getScore();
        double votes = getVotes();
        return score/votes;
    }

    public int getVotes() {
        return votes;
    }
    public Rating(int userId, int movieId, int score) {
        this.userId = userId;
        this.movieId = movieId;
        this.score = score;
        this.votes = 1;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
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
}
