package com.main;

import com.utils.Utils;
import org.json.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {
    public static void main(String[] args)  {
        SpringApplication.run(Main.class, args);
    }
    public static List<?> recommendMovies(HashMap<String, String> args,UserDAL userDAL,MovieDAL movieDAL,RatingDAL ratingDAL) {
        int limit = 10;
        List<Error> err = new ArrayList<>();
        if (args.size() > 2){
            err.add(new Error("Please, pass exactly 1 or 2 keys to JSON!\n"));
            return err;
        }
        try {
            limit = Integer.parseInt(args.get("limit"));
        }catch (NumberFormatException e){
            err.add(new Error("Limit must be an integer!\n"));
            return err;
        }
        try {
            String[] genres = Utils.getGenres(args.get("title"),movieDAL);
            List<List<User>> userLists = new ArrayList<>();
            userLists.add(userDAL.getAllUsers());
            List<Movie> first_movies = Utils.getMovies(genres, args.get("title").toLowerCase(),movieDAL);
            List<Movie> second_movies = Utils.getMovies(genres, args.get("title").toLowerCase(),movieDAL);
            second_movies.removeAll(first_movies);
            List<Movie> topN1 = Utils.getTopN(userLists, first_movies,ratingDAL , limit);
            List<Movie> topN2 = Utils.getTopN(userLists, second_movies,ratingDAL, limit - topN1.size());
            topN1.addAll(topN2);
            return topN1;
        } catch (IllegalArgumentException e) {
            err.add(new Error(e.getMessage()));
            return err;
        } catch (IOException e) {
            err.add(new Error(e.getMessage()));
            return err;
        }
    }

    public static List<?> getMovies(HashMap<String, String> args, UserDAL userDAL, MovieDAL movieDAL, RatingDAL ratingDAL) {
        String gender;
        int age;
        String work;
        boolean is_empty = false;
        String[] genres = null;
        StringBuilder errorMessage = new StringBuilder();
        List<Error> err = new ArrayList<>();
        Set<String> genreTypes = new HashSet<String>();
        try {
            Utils.setGenres(genreTypes);
        } catch (Exception e) {
            err.add(new Error(e.toString()));
            return err;
        }
        //* Args check
        if (args.size() != 4) {
            //-------------------------------------------------------------------
            //? are these messages enough for case with !=2 args?
            //-------------------------------------------------------------------
            errorMessage.append("Please, pass exactly 4 keys to JSON!\n");
            errorMessage.append("Try to remove spaces between occupations consisting of several words, such as \"college student\" -> \"collegestudent\"\n");
            err.add(new Error(errorMessage.toString()));
            return err;
        }

        //* Gender check
        gender = args.get("gender").toLowerCase();
        if (!(gender.compareTo("") == 0 || gender.compareTo("f") == 0 || gender.compareTo("m") == 0)) {
            errorMessage.append("Please, provide a proper argument for Gender\n");
            errorMessage.append("It shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"\n");
            err.add(new Error(errorMessage.toString()));
            return err;
        }

        //* Age check
        if (args.get("age").length() > 0) {
            for (int i = 0; i < args.get("age").length(); i++) {
                if (!Character.isDigit(args.get("age").charAt(i))) {
                    errorMessage.append("Please, enter a valid argument for age!\n");
                    errorMessage.append("It should be a positive integer, containing only digits!\n");
                    errorMessage.append("Age shall not exceed " + Integer.MAX_VALUE + "\n");
                    err.add(new Error(errorMessage.toString()));
                    return err;
                }
            }
            age = Integer.parseInt(args.get("age"));
        } else age = -1;

        //* Genres check
        if (args.get("genre").length() == 0) {
//            genres = args.get("genre").toLowerCase().split("\\|");
        } else {
            genres = args.get("genre").toLowerCase().split("\\|");
            HashSet<String> set = new HashSet<>();
            for (String genre : genres) {
                set.add(genre);
                if (!genreTypes.contains(genre)) {
                    errorMessage.append("There is not such registered genre as ").append(genre).append("\n");
                    err.add(new Error(errorMessage.toString()));
                    return err;
                }
            }

            if (set.size() != genres.length) {
                errorMessage.append("Please enter valid input for genres\n");
                errorMessage.append("Genres should not repeat\n");
                err.add(new Error(errorMessage.toString()));
                return err;
            }
        }


        //! toLowerCase may fail for ! character? or \?

        try {
            int occup_id = -1;
            work = args.get("occupation").toLowerCase();
            if (work.compareTo("") != 0)
                occup_id = Integer.parseInt(args.get("occupation"));
            //* Here, real implementation begins
            List<List<User>> userLists = Utils.getAllUsers(occup_id, age, gender,userDAL);
            List<Movie> movies = genres==null?movieDAL.getAllMovies():Utils.getMovies(genres, "",movieDAL);
            //Check if movieID is empty
            if (movies.size() <= 0) {
                errorMessage.append("No movie found that satisfies requested genres: ").append(args.get("genre")).append("\n");
                err.add(new Error(errorMessage.toString()));
                return err;
            }
            return Utils.getTopN(userLists, movies,ratingDAL, 10);
        }

        //* Developer's helpers
        catch (IOException e) {
            // todo: Proper error handling
            err.add(new Error(errorMessage.toString()));
            return err;
        }
    }
}
