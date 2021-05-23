package com.main;

import com.utils.Utils;
import org.json.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.*;
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    public static String recommendMovies(HashMap<String, String> args) throws IOException {
        int limit = 10;
        if (args.get("limit") != null) limit = Integer.parseInt(args.get("limit"));
        try {
            StringBuilder errorMessage = new StringBuilder();
            String[] genres = Utils.getGenres(args.get("title"));
            ArrayList<ArrayList<Integer>> userLists = new ArrayList<ArrayList<Integer>>();
            userLists.add(Utils.getUsers("", -1, -1, ""));
            HashMap<Integer, String[]> movies = Utils.getMovies(genres);
            ArrayList<Integer> movieID = new ArrayList<>(movies.keySet());
            //Check if movieID is empty
            if (movieID.size() <= 0) {
                errorMessage.append("No movie found that satisfies requested genres: ").append(args.get("genre")).append("\n");
                return errorMessage.toString();
            }
            Collections.sort(movieID);
            JSONArray topN = Utils.printTopN(userLists, movieID, movies, limit);
            return topN.toString(2);
        } 
        catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        catch (IOException e) {
            return e.toString();
        }
    }

    public static String getMovies(String[] args){
        String gender;
        int age;
        String work;
        boolean is_empty = false;
        String[] genres = new String[5];
        StringBuilder errorMessage = new StringBuilder();
        HashMap<String, Integer> workID = new HashMap<>();
        Utils.setOccupationHash(workID); // now workID contains all mappings

        Set<String> genreTypes = new HashSet<String>();
        try {
            Utils.setGenres(genreTypes);
        }
        catch (Exception e) {
            return e.toString();
        }

        //* Args check
        if (args.length < 3 || args.length > 4) {
            //-------------------------------------------------------------------
            //? are these messages enough for case with !=2 args?
            //-------------------------------------------------------------------
            errorMessage.append("Please, pass exactly 3 or 4 arguments!\n");
            errorMessage.append("Try to remove spaces between occupations consisting of several words, such as \"college student\" -> \"collegestudent\"\n");
            return errorMessage.toString();
        }

        //* Gender check
        gender = args[0].toLowerCase();
        if (!(gender.compareTo("") == 0 || gender.compareTo("f") == 0 || gender.compareTo("m") == 0)) {
            errorMessage.append("Please, provide a proper arguement for Gender\n");
            errorMessage.append("It shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"\n");
            return errorMessage.toString();
        }

        //* Age check
        if (args[1].length() > 0) {
            for (int i = 0; i < args[1].length(); i++) {
                if (!Character.isDigit(args[1].charAt(i))) {
                    errorMessage.append("Please, enter a valid argument for age!\n");
                    errorMessage.append("It should be a positive integer, containing only digits!\n");
                    errorMessage.append("Age shall not exceed "+Integer.MAX_VALUE+"\n");
                    return errorMessage.toString();
                }
            }
            age = Integer.parseInt(args[1]);
        } else age = -1;

        if (args.length == 4) {
            genres = args[3].toLowerCase().split("\\|");
            if(genres.length == 0){
                errorMessage.append("Please enter a valid input for genres! The input must include at least one genre\n");
                return errorMessage.toString();
            }
            //* Genres check
            HashSet<String> set = new HashSet<>();
            for (String genre : genres) {
                set.add(genre);
                if (!genreTypes.contains(genre)) {
                    errorMessage.append("Please enter a valid input for genres!\n");
                    if (genre.compareTo("") == 0) {
                        is_empty = true;
                        continue;
                    }
                    else {
                        errorMessage.append("There is not such registered genre as ").append(genre).append("\n");
                        return errorMessage.toString();
                    }
                }
            }
            if (set.size() != genres.length) {
                errorMessage.append("Please enter valid input for genres\n");
                errorMessage.append("Genres should not repeat\n");
                return errorMessage.toString();
            }

        }

        //! toLowerCase may fail for ! character? or \?

        try {
            work = args[2].toLowerCase();
            Integer occup_id = workID.get(work);
            if (work.compareTo("") == 0) occup_id = 0;
            //* Occupation check
            if (occup_id == null) {
                errorMessage.append("There is no such registered occupation as ").append(work).append("!\n");
                errorMessage.append("If you want to see some other ratings, please use \"other\" as an argument\n");
                return errorMessage.toString();
            }

            //* Here, real implementation begins
            ArrayList<ArrayList<Integer>> userLists = Utils.getAllUsers(work, occup_id, age, gender);
            HashMap<Integer, String[]> movies = !is_empty ? Utils.getMovies(genres) : Utils.getMovies();
            ArrayList<Integer> movieID = new ArrayList<>(movies.keySet());
            //Check if movieID is empty
            if (movieID.size() <= 0) {
                errorMessage.append("No movie found that satisfies requested genres: ").append(args[3]).append("\n");
                return errorMessage.toString();
            }
            Collections.sort(movieID);
            JSONArray top10 = Utils.printTopN(userLists, movieID, movies, 10);
            top10.toString(2);
        }

        //* Developer's helpers
        catch (IOException e) {
            // todo: Proper error handling
            return e.toString();
        }
        return "Some error happened\n";
    }
    public static String getMovies(HashMap<String,String> args){
        String gender;
        int age;
        String work;
        boolean is_empty = false;
        String[] genres = new String[5];
        StringBuilder errorMessage = new StringBuilder();
        HashMap<String, Integer> workID = new HashMap<>();
        Utils.setOccupationHash(workID); // now workID contains all mappings

        Set<String> genreTypes = new HashSet<String>();
        try {
            Utils.setGenres(genreTypes);
        }
        catch (Exception e) {
            return e.toString();
        }

        //* Args check
        if (args.size() < 3 || args.size() > 4) {
            //-------------------------------------------------------------------
            //? are these messages enough for case with !=2 args?
            //-------------------------------------------------------------------
            errorMessage.append("Please, pass exactly 3 or 4 arguments!\n");
            errorMessage.append("Try to remove spaces between occupations consisting of several words, such as \"college student\" -> \"collegestudent\"\n");
            return errorMessage.toString();
        }

        //* Gender check
        gender = args.get("gender").toLowerCase();
        if (!(gender.compareTo("") == 0 || gender.compareTo("f") == 0 || gender.compareTo("m") == 0)) {
            errorMessage.append("Please, provide a proper arguement for Gender\n");
            errorMessage.append("It shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"\n");
            return errorMessage.toString();
        }

        //* Age check
        if (args.get("age").length() > 0) {
            for (int i = 0; i < args.get("age").length(); i++) {
                if (!Character.isDigit(args.get("age").charAt(i))) {
                    errorMessage.append("Please, enter a valid argument for age!\n");
                    errorMessage.append("It should be a positive integer, containing only digits!\n");
                    errorMessage.append("Age shall not exceed "+Integer.MAX_VALUE+"\n");
                    return errorMessage.toString();
                }
            }
            age = Integer.parseInt(args.get("age"));
        } else age = -1;

        if (args.size() == 4) {
            genres = args.get("genre").toLowerCase().split("\\|");
            if(genres.length == 0){
                errorMessage.append("Please enter a valid input for genres! The input must include at least one genre\n");
                return errorMessage.toString();
            }
            //* Genres check
            HashSet<String> set = new HashSet<>();
            for (String genre : genres) {
                set.add(genre);
                if (!genreTypes.contains(genre)) {
                    errorMessage.append("Please enter a valid input for genres!\n");
                    if (genre.compareTo("") == 0) {
                        is_empty = true;
                        break;
                    }
                    else {
                        errorMessage.append("There is not such registered genre as ").append(genre).append("\n");
                        return errorMessage.toString();
                    }
                }
            }
            if (set.size() != genres.length) {
                errorMessage.append("Please enter valid input for genres\n");
                errorMessage.append("Genres should not repeat\n");
                return errorMessage.toString();
            }

        }

        //! toLowerCase may fail for ! character? or \?

        try {
            work = args.get("occupation").toLowerCase();
            Integer occup_id = workID.get(work);
            if (work.compareTo("") == 0) occup_id = 0;
            //* Occupation check
            if (occup_id == null) {
                errorMessage.append("There is no such registered occupation as ").append(work).append("!\n");
                errorMessage.append("If you want to see some other ratings, please use \"other\" as an argument\n");
                return errorMessage.toString();
            }

            //* Here, real implementation begins
            ArrayList<ArrayList<Integer>> userLists = Utils.getAllUsers(work, occup_id, age, gender);
            HashMap<Integer, String[]> movies = !is_empty ? Utils.getMovies(genres) : Utils.getMovies();
            ArrayList<Integer> movieID = new ArrayList<>(movies.keySet());
            //Check if movieID is empty
            if (movieID.size() <= 0) {
                errorMessage.append("No movie found that satisfies requested genres: ").append(args.get("genre")).append("\n");
                return errorMessage.toString();
            }
            Collections.sort(movieID);
            JSONArray top10 = Utils.printTopN(userLists, movieID, movies, 10);
            return top10.toString(2);
        }

        //* Developer's helpers
        catch (IOException e) {
            // todo: Proper error handling
            return e.toString();
        }
    }
}
