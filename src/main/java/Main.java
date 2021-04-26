import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    /**
     * todo: Implement the program described below
     * That is, the input to your program is a movie category (e.g., Adventure)
     * and the occupation of the user (e.g., educator). The expected output is
     * the average rating score of all movies in the given category rated by
     * the given occupation. The category input can be one or more categories
     * with “|” as a delimiter. For example, if the input is Adventure|Comedy,
     * then your program must output the average rating score of those movies
     * that belong to both Adventure and Comedy categories. The occupation input
     * must be only one.
     *
     * @param args
     * @throws IOException
     */

    // xxx.yyy.YourClass Adventure educator
    // args[0] = genre, args[1] = occupation
    public static void main(String[] args) {
        String gender;
        int age;
        String work;
        String[] genres = new String[5];
        if (args.length != 3 && args.length != 4) {
            //-------------------------------------------------------------------
            //are these messages enough for case with !=2 args?
            //-------------------------------------------------------------------
            System.out.println("Please, pass exactly 3 or 4 arguments!");
            System.out.print("Try to remove spaces between occupations consisting of several words, such as ");
            System.out.println("\"college student\" -> \"collegestudent\"");
        } else {
            //Elkhan's code
            if (args.length == 3) {
                gender = args[0].toLowerCase();
                if (args[1].length() > 0)
                    age = Integer.parseInt(args[1]);
                else
                    age = -1;
                work = args[2].toLowerCase();
            } else {
                gender = args[0].toLowerCase();
                if (args[1].length() > 0)
                    age = Integer.parseInt(args[1]);
                else
                    age = -1;
                work = args[2].toLowerCase();
                genres = args[3].toLowerCase().split("\\|");
                //START CHECK FOR GENRE INPUT
                if (genres.length == 0) {
                    System.out.println("Please enter valid input");
                    return;
                }
                if (genres.length > 10) {
                    //if too many genres
                    System.out.println("Input for genre is too long, please try to include a genre not more that one time.");
                    return;
                }
                boolean is_empty = true;
                for (String genre : genres) {
                    //if the genre string is too long
                    if (genre.length() > 50) {
                        System.out.println("There are no movies with genre " + genre);
                        System.out.println("Use '|' character to split genres");
                        return;
                    }
                    if (genre.length() > 0) {
                        is_empty = false;
                    }

                }
                if (is_empty) {
                    System.out.println("Please enter valid input");
                    return;
                }
                //END
            }
            //-------------------------------------------------------------------
            //toLowerCase may fail for ! character? or \?
            //-------------------------------------------------------------------
            HashMap<String, Integer> workID = new HashMap<>();
            Utils.setOccupationHash(workID); // now workID contains all mappings
            try {
                Integer occup_id = workID.get(work);
                boolean is_other = false;
                //START CHECK FOR OCCUPATION INPUT
                if (occup_id == null) {
                    //either some wrong string, or other type of occupation
                    if (work.length() > 50) {
                        System.out.println("There is no such occupation (name is too long)");
                        return;
                    } else {
                        is_other = true;
                        occup_id = 0;
                        System.out.println("Since we can't recognize occupation " + work + ", we will regard it as 'other'");
                    }
                }
                //END
                //Elkhan's code
                ArrayList<Integer> userID = Utils.getUsers(work, occup_id, age, gender);
                // To ignore work -> call getUsers("", occup_id, age, gender);
                // To ignore age -> call getUsers(work, occup_id, -1, gender);
                // To ignore gender -> call getUsers(work, occup_id, age, "");
                ArrayList<Integer> userIDNoWork = Utils.getUsers("", occup_id, age, gender);
                HashMap<Integer, String> movies = args.length == 4 ? Utils.getMovies(genres) : Utils.getMovies();
                Collections.sort(userID);
                Collections.sort(userIDNoWork);
                ArrayList<Integer> movieID = new ArrayList<>(movies.keySet());
                //Check if movieID is empty
                if (movieID.size() <= 0) {
                    System.out.println("No movie found that satisfies requested genres: " + args[0]);
                    return;
                }

                Collections.sort(movieID);
                HashMap<Integer, Integer[]> ratings = Utils.getRatings(userID, movieID);
                //Elkhan's code
                int count = 0;
                HashMap<Integer, Double> scores = new HashMap<>();
                if (ratings.size() < 10) {
//                    System.out.println("The algorithm cannot recommend 10 movies, it will be less");
                    HashMap<Integer, Integer[]> secondRatings = Utils.getRatings(userIDNoWork, movieID);
                    secondRatings.keySet().removeAll(ratings.keySet());
                    ratings.putAll(secondRatings);
                    if (ratings.size() < 10)
                        System.out.println("less");
                }

                for (Integer k : ratings.keySet()) {
                    scores.put(k, (double) ratings.get(k)[0] / (double) ratings.get(k)[1]);
                }
                HashMap<Integer, Double> sortedScores = scores.entrySet().stream()
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
                    Utils.printMovie(k, movies);
                    count++;
                    if (count == 10) break;
                }
            } catch (IOException e) {
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
}
