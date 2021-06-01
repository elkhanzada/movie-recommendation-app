package com.utils;

import com.main.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.lang.IllegalArgumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static JSONArray getTopN(List<List<User>> userLists, List<Movie> movies, RatingDAL ratingDAL, int n) throws IOException {
        int count = 0;
        int index = 0;
        ArrayList<Integer> printedList = new ArrayList<>();
        JSONArray movieArray = new JSONArray();
        while (count < n) {
            int allvotes = 0;
            double totalMean = 0;
            if (index >= userLists.size()) break;
            List<User> list = userLists.get(index);
            List<Rating> ratings = Utils.getRatings(list,movies,ratingDAL);
            HashMap<Integer, Double> scores = new HashMap<>();
            for (Rating rating: ratings) {
                allvotes += rating.getVotes();
                totalMean += rating.getAverage();
            }
            if (allvotes != 0)
                totalMean /= allvotes;
            for (Rating rating: ratings) {
                scores.put(rating.getMovieId(), weightedRating(rating.getAverage(),
                        rating.getVotes(),
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
                    movieArray.put(new JSONObject(Utils.getMovieDetails(k, movies)));
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

    public static List<List<User>> getAllUsers(String work, Integer occupation, Integer age, String gender, UserDAL userDAL) throws IOException {
        List<List<User>> lists = new ArrayList<>();
        lists.add(userDAL.getSpecificUsers(occupation, age, gender));
        lists.add(userDAL.getSpecificUsers(occupation, age, gender));
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
    public static String[] getGenres(String movieName) throws IllegalArgumentException, IOException {
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/movies.dat")));
        movieName = movieName.toLowerCase();
        String[] result = new String[5];
        String line;
        boolean found = false;
        while ((line = scan.readLine()) != null) {
            String[] movies = line.split("::");
            if (movieName.equals(movies[1].toLowerCase())) {
                result = movies[2].toLowerCase().split("\\|");
                found = true;
                break;
            }
        }
        scan.close();
        if (!found) {
            throw new IllegalArgumentException("Movie doesn't exist in the movies.dat\n");
        }
        return result;
    }

    // This function returns movieID-s with matching genres
    public static List<Movie> getMovies(String[] genres, String exclude, MovieDAL movieDAL) throws IOException {
        // ! --movies.dat--
        // MovieID::Title::Genres

        return movieDAL.getSpecificMovies(genres,exclude);
    }

    public static HashMap<Integer, String[]> getMovies(String exclude) throws IOException {
        // ! --movies.dat--
        // MovieID::Title::Genres
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/movies.dat")));
        HashMap<Integer, String[]> list = new HashMap<>();
        String line;
        while ((line = scan.readLine()) != null) {
            String[] movie = line.split("::");
            if (movie[1].toLowerCase().equals(exclude)) continue;
            list.put(Integer.parseInt(movie[0]), new String[]{movie[1], movie[2]});
        }
        scan.close();
        return list;
    }

    // This function simply maps occupation name to its category
    public static void setOccupationHash(HashMap<String, Integer> hashmap) {
        hashmap.put("other", 0);
        hashmap.put("academic", 1);
        hashmap.put("educator", 1);
        hashmap.put("artist", 2);
        hashmap.put("clerical", 3);
        hashmap.put("admin", 3);
        hashmap.put("collegestudent", 4);
        hashmap.put("college", 4);
        hashmap.put("gradstudent", 4);
        hashmap.put("customerservice", 5);
        hashmap.put("doctor", 6);
        hashmap.put("healthcare", 6);
        hashmap.put("executive", 7);
        hashmap.put("managerial", 7);
        hashmap.put("farmer", 8);
        hashmap.put("homemaker", 9);
        hashmap.put("k-12student", 10);
        hashmap.put("lawyer", 11);
        hashmap.put("programmer", 12);
        hashmap.put("retired", 13);
        hashmap.put("sales", 14);
        hashmap.put("marketing", 14);
        hashmap.put("scientist", 15);
        hashmap.put("self-employed", 16);
        hashmap.put("technician", 17);
        hashmap.put("engineer", 17);
        hashmap.put("tradesman", 18);
        hashmap.put("craftsman", 18);
        hashmap.put("unemployed", 19);
        hashmap.put("writer", 20);
    }

    public static void setGenres(Set<String> set) throws FileNotFoundException, IOException {
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/movies.dat")));
        String line;
        while ((line = scan.readLine()) != null) {
            String[] genres = line.split("::")[2].split("\\|");
            for (String genre : genres) set.add(genre.toLowerCase());
        }
    }
}
