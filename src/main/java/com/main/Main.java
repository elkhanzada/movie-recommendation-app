package com.main;

import com.utils.Utils;
import org.json.JSONArray;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

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
//            System.out.println(e.toString());
            return e.toString();
        }

        //* Args check
        if (args.length < 3 || args.length > 4) {
            //-------------------------------------------------------------------
            //? are these messages enough for case with !=2 args?
            //-------------------------------------------------------------------
            errorMessage.append("Please, pass exactly 3 or 4 arguments!\n");
            errorMessage.append("Try to remove spaces between occupations consisting of several words, such as \"college student\" -> \"collegestudent\"\n");
//            System.out.println("Please, pass exactly 3 or 4 arguments!");
//            System.out.print("Try to remove spaces between occupations consisting of several words, such as ");
//            System.out.println("\"college student\" -> \"collegestudent\"");
            return errorMessage.toString();
        }

        //* Gender check
        gender = args[0].toLowerCase();
        if (!(gender.compareTo("") == 0 || gender.compareTo("f") == 0 || gender.compareTo("m") == 0)) {
            errorMessage.append("Please, provide a proper arguement for Gender\n");
            errorMessage.append("It shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"\n");
//            System.out.println("Please, provide a proper arguement for Gender");
//            System.out.println("It shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"");
            return errorMessage.toString();
        }

        //* Age check
        if (args[1].length() > 0) {
            for (int i = 0; i < args[1].length(); i++) {
                if (!Character.isDigit(args[1].charAt(i))) {
                    errorMessage.append("Please, enter a valid argument for age!\n");
                    errorMessage.append("It should be a positive integer, containing only digits!\n");
                    errorMessage.append("Age shall not exceed "+Integer.MAX_VALUE+"\n");
//                    System.out.println("Please, enter a valid argument for age!");
//                    System.out.println("It should be a positive integer, containing only digits!");
//                    System.out.printf("Age shall not exceed %d\n", Integer.MAX_VALUE);
                    return errorMessage.toString();
                }
            }
            age = Integer.parseInt(args[1]);
        } else age = -1;

        if (args.length == 4) {
            genres = args[3].toLowerCase().split("\\|");
            if(genres.length == 0){
                errorMessage.append("Please enter a valid input for genres! The input must include at least one genre\n");
//                System.out.println("Please enter a valid input for genres! The input must include at least one genre");
                return errorMessage.toString();
            }
            //* Genres check
            HashSet<String> set = new HashSet<>();
            for (String genre : genres) {
                set.add(genre);
                if (!genreTypes.contains(genre)) {
                    errorMessage.append("Please enter a valid input for genres!\n");
//                    System.out.println("Please enter a valid input for genres!");
                    if (genre.compareTo("") == 0) {
                        is_empty = true;
                        continue;
                    }
                    else {
                        errorMessage.append("There is not such registered genre as ").append(genre).append("\n");
//                        System.out.println("There is not such registered genre as " + genre);
                        return errorMessage.toString();
                    }
                }
            }
            if (set.size() != genres.length) {
                errorMessage.append("Please enter valid input for genres\n");
                errorMessage.append("Genres should not repeat\n");
//                System.out.println("Please enter valid input for genres");
//                System.out.println("Genres should not repeat");
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
//                System.out.println("There is no such registered occupation as " + work + "!");
//                System.out.println("If you want to see some other ratings, please use \"other\" as an argument");
                return errorMessage.toString();
            }

            //* Here, real implementation begins
            ArrayList<ArrayList<Integer>> userLists = Utils.getAllUsers(work, occup_id, age, gender);
            HashMap<Integer, String[]> movies = !is_empty ? Utils.getMovies(genres) : Utils.getMovies();
            ArrayList<Integer> movieID = new ArrayList<>(movies.keySet());
            //Check if movieID is empty
            if (movieID.size() <= 0) {
                errorMessage.append("No movie found that satisfies requested genres: ").append(args[3]).append("\n");
//                System.out.println("No movie found that satisfies requested genres: " + args[3]).;
                return errorMessage.toString();
            }
            Collections.sort(movieID);
            JSONArray top10 = Utils.printTop10(userLists, movieID, movies);
            top10.toString(2);
        }

        //* Developer's helpers
        catch (IOException e) {
            // todo: Proper error handling
//            System.out.println("Some error happened");
            return e.toString();
        }
        return "Some error happened\n";
    }
}
