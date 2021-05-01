import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String gender;
        int age;
        String work;
        String[] genres = new String[5];

        HashMap<String, Integer> workID = new HashMap<>();
        Utils.setOccupationHash(workID); // now workID contains all mappings

        Set<String> genreTypes = new HashSet<String>();
        try {
            Utils.setGenres(genreTypes);
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return;
        }

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
        if (!(gender.compareTo("") == 0 || gender.compareTo("f") == 0 || gender.compareTo("m") == 0)) {
            System.out.println("Please, provide a proper arguement for Gender");
            System.out.println("It shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"");
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
            if (genres.length == 0 || genres.length > 10) {
                System.out.println("Please enter valid input");
                System.out.println("Genres field should not be empty!");
                System.out.println("Genres should not repeat and exceed 10!");
                return;
            }
            for (String genre : genres) {
                if (!genreTypes.contains(genre)) {
                    System.out.println("Please enter a valid input for genres!");
                    System.out.println("There is no such genre as " + genre);
                    System.out.println("If you want to see the ratings for other occupations, simply type \"other\"");
                    return;
                }
            }

        }

        //! toLowerCase may fail for ! character? or \?

        try {
            work = args[2].toLowerCase();
            Integer occup_id = (work.compareTo("") == 0 ? 0 : workID.get(work));
            //* Occupation check
            if (occup_id == null) {
                System.out.println("There is no such occupation as " + work + "!");
                System.out.println("If you want to see some other ratings, please use \"other\" as an argument");
                return;
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
        }
    }
}
