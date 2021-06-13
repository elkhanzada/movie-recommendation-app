package com.main;

public class Movie {
    // ! --movies.dat--
    // MovieID::Title::Genres
    private int movieID;
    private String title;
    private String genres;
    private String poster;
    private String link;

    public String getLink() {
        return link;
    }
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Movie(int movieID, String title, String genres,String poster, String link) {
        this.movieID = movieID;
        this.title = title;
        this.genres = genres;
        this.poster = poster;
        this.link = link;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    @Override
    public boolean equals(Object movie){
        Movie mv = (Movie)movie;
        return this.getMovieID() == mv.getMovieID();
    }
    @Override
    public int hashCode(){
       return 0;
    }

}
