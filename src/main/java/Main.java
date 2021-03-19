import java.util.ArrayList;

public class Main {

    /**
     * That is, the input to your program is a movie category (e.g., Adventure) 
     * and the occupation of the user (e.g., educator). The expected output is 
     * the average rating score of all movies in the given category rated by 
     * the given occupation. The category input can be one or more categories 
     * with “|” as a delimiter. For example, if the input is Adventure|Comedy, 
     * then your program must output the average rating score of those movies 
     * that belong to both Adventure and Comedy categories. The occupation input 
     * must be only one.
     * @param args
     */
    public static void main(String[] args) { 
        /**
         * todo: We have to process args properly
         * ? How?
         */
    }
}


/**
 * My guess about the format of .dat files
 *! --user.dat--
 * UserID::Gender::Age::Occupation::Zip-code
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
 *! --movies.dat--
 * MovieID::Title::Genres
 * 
 */



class Movie {
    public final int id;
    public final String title;
    ArrayList<String> genre;

    public Movie(int id, String title) {
        this.id = id;
        this.title = title;
        genre = new ArrayList<>(3);
    }

    public void addGenre(String _genre) {
        genre.add(_genre);
    }

    public boolean isOfGenre(String _genre) {
        return genre.contains(_genre);
    }
}

class User {
    public final int id, age;
    public final char gender;
    // ? What to do with occupation?
    // ? Do we need zip-code?
}