import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String gender;
        int age;
        String work;
        String[] genres = new String[5];

        HashMap<String, Integer> workID = new HashMap<>();
        Utils.setOccupationHash(workID); // now workID contains all mappings

        //* Args check
        if (args.length < 3 || args.length > 4) {
            //-------------------------------------------------------------------
            //? are these messages enough for case with !=2 args?
            //-------------------------------------------------------------------
            System.out.println("Please, pass exactly 3 or 4 arguments!");
            System.out.print("Try to remove spaces between occupations consisting of several words, such as ");
            System.out.println("\"college student\" -> \"collegestudent\"");
            return;
        }

        //* Gender check
        gender = args[0].toLowerCase();
        if (gender.compareTo("") != 0 // if gender is passed
                && // and not equal to neither F nor M
                !(gender.compareTo("F") == 0 || gender.compareTo("M") == 0)) {

            System.out.println("Please, provide a proper arguement for Gender");
            System.out.println("It shall be empty - \"\", male - \"M\" or female \"F\"");
            return;
        }

        //* Age check
        if (args[1].length() > 0) {
            for (int i = 0; i < args[1].length(); i++) {
                if (!Character.isDigit(args[1].charAt(i))) {
                    System.out.println("Please, enter a valid argument for age!");
                    System.out.println("It should be a positive integer, containing only digits!");
                    System.out.printf("Age shall not exceed %d\n", Integer.MAX_VALUE);
                    return;
                }
            }
            age = Integer.parseInt(args[1]);
        } else age = -1;

        if (args.length == 4) {
            genres = args[3].toLowerCase().split("\\|");

            //* Genres check
            if (genres.length == 0) {
                System.out.println("Please enter valid input");
                System.out.println("Genres field should not be empty!");
                return;
            } else if (genres.length > 10) {
                System.out.println("Input for genres is too long, please try to include each genre only once.");
                return;
            }
            boolean is_empty = true;
            for (String genre : genres) {
                if (genre.length() > 50) {
                    System.out.println("There are no movies with genre " + genre);
                    System.out.println("Use '|' character to split genres");
                    return;
                }
                if (genre.length() > 0) is_empty = false;
            }
            if (is_empty) {
                System.out.println("Please enter valid input");
                return;
            }
        }
        //-------------------------------------------------------------------
        //! toLowerCase may fail for ! character? or \?
        //-------------------------------------------------------------------
        try {
            work = args[2].toLowerCase();
            Integer occup_id = workID.get(work);
            //* Occupation check
            if (occup_id == null) {
                //either some wrong string, or other type of occupation
                if (work.length() > 50) {
                    System.out.println("There is no such occupation (name is too long)");
                    return;
                } else {
                    occup_id = 0;
                    System.out.println("Since we can't recognize occupation " + work + ", we will regard it as 'other'");
                }
            }

            //* Here, real implementation begins
            ArrayList<ArrayList<Integer>> userLists = Utils.getAllUsers(work, occup_id, age, gender);
            HashMap<Integer, String> movies = args.length == 4 ? Utils.getMovies(genres) : Utils.getMovies();
            ArrayList<Integer> movieID = new ArrayList<>(movies.keySet());
            //Check if movieID is empty
            if (movieID.size() <= 0) {
                System.out.println("No movie found that satisfies requested genres: " + args[0]);
                return;
            }
            Collections.sort(movieID);
            Utils.printTop10(userLists, movieID, movies);
        }

        //* Developer's helpers
        catch (IOException e) {
            // todo: Proper error handling
            System.out.println("Some error happened");
            e.printStackTrace();
        } catch (NullPointerException e) {
            // todo: Find the source of a bug
            System.out.println("Null pointer exception somewhere");
            e.printStackTrace();
        }

    }
}
