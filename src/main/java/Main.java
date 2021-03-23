import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
     * @param args
     * @throws IOException
     */

    // xxx.yyy.YourClass Adventure educator
    // args[0] = genre, args[1] = occupation
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please, pass exactly 2 arguments!");
            System.out.print("Try to remove spaces between occupations ");
            System.out.print("consisting of several words, such as ");
            System.out.println("\"college student\" -> \"collegestudent\"");
        }
        else {
            String[] genres = args[0].split("&");
            String work = args[1];

            HashMap<String, Integer> workID = new HashMap<>();
            setOccupationHash(workID); // now workID contains all mappings

            try { 
                ArrayList<Integer> userID = getUsers(workID.get(work));
                ArrayList<Integer> movieID = getMovies(genres);
                Collections.sort(userID);
                Collections.sort(movieID);

                System.out.printf("The average rating for %s is: %f\n", args[1], scanRatings(userID, movieID));

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

    // This method scans "ratings.dat" file and returns the average
    private static double scanRatings(ArrayList<Integer> userID, ArrayList<Integer> movieID) throws IOException {
        // ! --ratings.dat--
        // UserID::MovieID::Rating::Timestamp

        BufferedReader scan = new BufferedReader(new FileReader(new File("../../../data/ratings.dat")));
        int count = 0, i, j, sum = 0;
        String line;
        while ((line = scan.readLine()) != null) {
            String[] rating = line.split("::");
            // Collections.binarySearch() returns a negative number if the item not found;
            i = Collections.binarySearch(userID, Integer.parseInt(rating[0]));
            j = Collections.binarySearch(movieID, Integer.parseInt(rating[1]));
            if (i > -1 && j > -1) { // if we find corresponding movie and user;
                sum += Integer.parseInt(rating[2]);
                count++;
            }
        }
        scan.close();
        return (double)(sum) / (double)(count);
    }

    // This function returns userID-s with matching occupation
    private static ArrayList<Integer> getUsers(int occupation) throws IOException {
        // ! --users.dat--
        // UserID::Gender::Age::Occupation::Zip-code

        BufferedReader scan = new BufferedReader(new FileReader( new File("../../../data/users.dat")));
        ArrayList<Integer> list = new ArrayList<Integer>();
        String line;
        while ((line = scan.readLine()) != null) {
            String[] user = line.split("::");
            if (Integer.parseInt(user[3]) == occupation) list.add(Integer.parseInt(user[0]));
        }
        scan.close();
        return list;
    }

    // This function returns movieID-s with matching genres
    private static ArrayList<Integer> getMovies(String[] genres) throws IOException {
        // ! --movies.dat--
        // MovieID::Title::Genres

        BufferedReader scan = new BufferedReader(new FileReader(new File("../../../data/movies.dat")));
        ArrayList<Integer> list = new ArrayList<Integer>();
        boolean contains;
        String line;
        while ((line = scan.readLine()) != null) {
            String[] movie = line.split("::");
            contains = true;
            for (String genre : genres) {
                if (!movie[2].toLowerCase().contains(genre.toLowerCase())) contains = false;
            }
            if (contains) list.add(Integer.parseInt(movie[0]));
        }
        scan.close();
        return list;
    }

    // This function simply maps occupation name to its category 
    private static void setOccupationHash(HashMap<String, Integer> hashmap) {
        if (hashmap.isEmpty()) {
            // ! Subject to change depending on profs answer
            hashmap.put("other",  0);
            hashmap.put("academic",  1);
            hashmap.put("educator",  1);
            hashmap.put("artist",  2);
            hashmap.put("clerical",  3);
            hashmap.put("admin",  3);
            hashmap.put("collegestudent",  4);
            hashmap.put("college",  4);
            hashmap.put("gradstudent",  4);
            hashmap.put("customerservice",  5);
            hashmap.put("doctor",  6);
            hashmap.put("healthcare",  6);
            hashmap.put("executive",  7);
            hashmap.put("managerial",  7);
            hashmap.put("farmer",  8);
            hashmap.put("homemaker",  9);
            hashmap.put("k-12student", 10);
            hashmap.put("lawyer", 11);
            hashmap.put("programmer", 12);
            hashmap.put("retired", 13);
            hashmap.put("sales", 14);
            hashmap.put("marketing", 14);
            hashmap.put("scientist", 15);
            hashmap.put("self-employed", 1);
            hashmap.put("technician", 17);
            hashmap.put("engineer", 17);
            hashmap.put("tradesman", 18);
            hashmap.put("craftsman", 18);
            hashmap.put("unemployed", 19);
            hashmap.put("writer", 20);
        }
    }
}


/**
 * My guess about the format of .dat files
 *
 * 
 * * Age is chosen as:
 *  1:  "Under 18"
 * 18:  "18-24"
 * 25:  "25-34"
 * 35:  "35-44"
 * 45:  "45-49"
 * 50:  "50-55"
 * 56:  "56+"
 * 
 * * Occupation is chosen from the following choices:
 *  0:  "other" or not specified
 *  1:  "academic/educator"
 *  2:  "artist"
 *  3:  "clerical/admin"
 *  4:  "college/grad student"
 *  5:  "customer service"
 *  6:  "doctor/health care"
 *  7:  "executive/managerial"
 *  8:  "farmer"
 *  9:  "homemaker"
 * 10:  "K-12 student"
 * 11:  "lawyer"
 * 12:  "programmer"
 * 13:  "retired"
 * 14:  "sales/marketing"
 * 15:  "scientist"
 * 16:  "self-employed"
 * 17:  "technician/engineer"
 * 18:  "tradesman/craftsman"
 * 19:  "unemployed"
 * 20:  "writer"
 * 
 *! --ratings.dat--
 * UserID::MovieID::Rating::Timestamp
 * 
 * * UserIDs range between 1 and 6040 
 * * MovieIDs range between 1 and 3952
 * * Ratings are made on a 5-star scale (whole-star ratings only)
 * * Timestamp is represented in seconds since the epoch as returned by time(2)
 * * Each user has at least 20 ratings
 * 
 *
 * 
 */