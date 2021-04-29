import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static void printTop10(ArrayList<ArrayList<Integer>> userLists, ArrayList<Integer> movieID, HashMap<Integer, String> movies) throws IOException {
        int count = 0;
        int index = 0;
        while (count < 10) {
            ArrayList<Integer> list = userLists.get(index);
            Collections.sort(list);
            HashMap<Integer, Integer[]> ratings = Utils.getRatings(list, movieID);
            //Elkhan's code
            HashMap<Integer, Double> scores = new HashMap<>();
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
                if (count >= 10) break;
            }
            index += 1;
        }
    }

    //Elkhan's code
    public static void printMovie(Integer chosenMovie, HashMap<Integer, String> movies) throws IOException {
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/links.dat")));
        String line;
        while (true) {
            line = scan.readLine();
            int movieID = Integer.parseInt(line.split("::")[0]);
            if (chosenMovie == movieID) {
                System.out.println(movies.get(chosenMovie) + " ( http://www.imdb.com/title/tt" + line.split("::")[1] + " )");
                break;
            }
        }
    }

    public static ArrayList<ArrayList<Integer>> getAllUsers(String work, Integer occupation, Integer age, String gender) throws IOException {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        lists.add(getUsers(work, occupation, age, gender));
        lists.add(getUsers("", occupation, age, gender));
        lists.add(getUsers(work, occupation, -1, gender));
        lists.add(getUsers(work, occupation, age, ""));
        lists.add(getUsers("", occupation, -1, gender));
        lists.add(getUsers("", occupation, age, ""));
        lists.add(getUsers(work, occupation, -1, ""));
        lists.add(getUsers("", occupation, -1, ""));
        return lists;
    }

    // Elkhan's code
    public static HashMap<Integer, Integer[]> getRatings(ArrayList<Integer> userID, ArrayList<Integer> movieID) throws IOException {
        // ! --ratings.dat--
        // UserID::MovieID::Rating::Timestamp
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/ratings.dat")));
        int i = 0;
        int j = 0;
        String line;
        HashMap<Integer, Integer[]> ratingList = new HashMap<>();
        while ((line = scan.readLine()) != null) {
            String[] rating = line.split("::");
            // Collections.binarySearch() returns a negative number if the item not found;
            i = Collections.binarySearch(userID, Integer.parseInt(rating[0]));
            j = Collections.binarySearch(movieID, Integer.parseInt(rating[1]));
            if (i > -1 && j > -1) { // if we find corresponding movie and user;
                if (!ratingList.containsKey(Integer.parseInt(rating[1])))
                    ratingList.put(Integer.parseInt(rating[1]), new Integer[]{Integer.parseInt(rating[2]), 1});
                else
                    ratingList.put(Integer.parseInt(rating[1]), new Integer[]{ratingList.get(Integer.parseInt(rating[1]))[0] + Integer.parseInt(rating[2]), ratingList.get(Integer.parseInt(rating[1]))[1] + 1});
            }
        }
        scan.close();
        return ratingList;
    }

    //Elkhan's code
    public static ArrayList<Integer> getUsers(String work, Integer occupation, Integer age, String gender) throws IOException {
        // ! --users.dat--
        // UserID::Gender::Age::Occupation::Zip-code
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/users.dat")));
        ArrayList<Integer> list = new ArrayList<Integer>();
        HashMap<Integer, Integer[]> agelist = new HashMap<>();
        agelist.put(1, new Integer[]{1, 17});
        agelist.put(18, new Integer[]{18, 24});
        agelist.put(25, new Integer[]{25, 34});
        agelist.put(35, new Integer[]{35, 44});
        agelist.put(45, new Integer[]{45, 49});
        agelist.put(50, new Integer[]{50, 55});
        agelist.put(56, new Integer[]{56, Integer.MAX_VALUE});
        String line;
        while ((line = scan.readLine()) != null) {
            String[] user = line.split("::");
            if ((Integer.parseInt(user[3]) == occupation || work.length() == 0) &&
                    ((age == -1) || (agelist.get(Integer.parseInt(user[2]))[0] <= age &&
                            agelist.get(Integer.parseInt(user[2]))[1] >= age)) &&
                    (gender.length() == 0 || gender.toLowerCase().equals(user[1].toLowerCase())))
                list.add(Integer.parseInt(user[0]));
        }
        scan.close();
        return list;
    }

    // This function returns movieID-s with matching genres
    public static HashMap<Integer, String> getMovies(String[] genres) throws IOException {
        // ! --movies.dat--
        // MovieID::Title::Genres
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/movies.dat")));
        HashMap<Integer, String> list = new HashMap<>();
        String line;
        while ((line = scan.readLine()) != null) {
            String[] movie = line.split("::");
            String[] genres_list = movie[2].split("\\|");
            for (String s : genres) {
                if (s.length() == 0) {
                    continue;
                }
                for (String g : genres_list) {
                    if (s.toLowerCase().equals(g.toLowerCase())) {
                        list.put(Integer.parseInt(movie[0]), movie[1]);
                        break;
                    }
                }
            }
        }
        scan.close();
        return list;
    }

    //Elkhan's code
    public static HashMap<Integer, String> getMovies() throws IOException {
        // ! --movies.dat--
        // MovieID::Title::Genres
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/movies.dat")));
        HashMap<Integer, String> list = new HashMap<>();
        String line;
        while ((line = scan.readLine()) != null) {
            String[] movie = line.split("::");
            list.put(Integer.parseInt(movie[0]), movie[1]);
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
