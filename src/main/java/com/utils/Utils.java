package com.utils;

import com.main.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.IllegalArgumentException;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static List<Movie> getTopN(List<List<User>> userLists, List<Movie> movies, RatingDAL ratingDAL, int n) throws IOException {
        int count = 0;
        int index = 0;
        ArrayList<Integer> printedList = new ArrayList<>();
        List<Movie> movieArray = new ArrayList<>();
        while (count < n) {
            int allvotes = 0;
            double totalMean = 0;
            if (index >= userLists.size()) break;
            List<User> list = userLists.get(index);
            List<Rating> ratings = Utils.getRatings(list,movies,ratingDAL);
            HashMap<Integer, Double> scores = new HashMap<>();
            for (Rating rating: ratings) {
                allvotes += rating.getVotes(list);
                totalMean += rating.getAverage(list);
            }
            if (allvotes != 0)
                totalMean /= allvotes;
            for (Rating rating: ratings) {
                scores.put(rating.getMovieId(), weightedRating(rating.getAverage(list),
                        rating.getVotes(list),
                        10,
                        totalMean));
            }
            LinkedHashMap<Integer, Double> sortedScores = scores.entrySet().stream()
                    .sorted(Comparator.comparingDouble(e -> -e.getValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> {
                                throw new AssertionError();
                            },
                            LinkedHashMap::new
                    ));
            for (Integer k : sortedScores.keySet()) {

                if (sortedScores.get(k) >= 3.0) {
                    if (printedList.contains(k))
                        continue;
                    else
                        printedList.add(k);
                    movieArray.add(Utils.getMovieDetails(k, movies));
                    count++;
                }
                if (count >= n) break;
            }
            index++;
        }
        return movieArray;
    }

    public static double weightedRating(double R, double v, double m, double C) {
        return (R * v + C * m) / (v + m);
    }

    public static Movie getMovieDetails(Integer chosenMovie, List<Movie> movies){
        for(Movie movie: movies){
            if(movie.getMovieID()==chosenMovie)
                return movie;
        }
        return null;
    }

    public static List<List<User>> getAllUsers(Integer occupation, Integer age, String gender, UserDAL userDAL) throws IOException {
        List<List<User>> lists = new ArrayList<>();
        if(occupation==-1&&age==-1&&gender.equals("")) {
            lists.add(userDAL.getSpecificUsers(occupation, age, gender));
            return lists;
        }
        lists.add(userDAL.getSpecificUsers(occupation, age, gender));
        lists.add(userDAL.getSpecificUsers(-1, age, gender));
        lists.add(userDAL.getSpecificUsers(occupation, age, ""));
        lists.add(userDAL.getSpecificUsers(occupation, -1, gender));
        lists.add(userDAL.getSpecificUsers(-1, age, ""));
        lists.add(userDAL.getSpecificUsers(occupation, -1, ""));
        lists.add(userDAL.getSpecificUsers(-1, -1, gender));
        lists.add(userDAL.getSpecificUsers(-1, -1, ""));
        return lists;
    }

    public static List<Rating> getRatings(List<User>users,List<Movie> movies, RatingDAL ratingDAL) {
        // ! --ratings.dat--
        // UserID::MovieID::Rating::Timestamp
        return ratingDAL.getSpecificRatings(users,movies);
    }
    // This function returns genres of the provided movie
    public static String[] getGenres(String movieName,MovieDAL movieDAL) throws IllegalArgumentException, IOException {
        Movie mv = movieDAL.findMovie(movieName);
        if (mv==null) throw new IllegalArgumentException("Movie does not exist!\n");
        else return mv.getGenres().split("\\|");
    }

    // This function returns movieID-s with matching genres
    public static List<Movie> getMovies(String[] genres, String exclude, MovieDAL movieDAL) {
        // ! --movies.dat--
        // MovieID::Title::Genres
        return movieDAL.getSpecificMovies(genres,exclude);
    }
    public static void setGenres(Set<String> set) throws IOException {
        BufferedReader scan = new BufferedReader(new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream("data/movies.dat")));
        String line;
        while ((line = scan.readLine()) != null) {
            String[] genres = line.split("::")[2].split("\\|");
            for (String genre : genres) set.add(genre.toLowerCase());
        }
    }
}
