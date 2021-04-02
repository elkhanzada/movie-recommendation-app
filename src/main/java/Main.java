import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.lang.Math;

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
	//-------------------------------------------------------------------
	//are these messages enough for case with !=2 args?		
	//-------------------------------------------------------------------
            System.out.println("Please, pass exactly 2 arguments!");
            System.out.print("Try to remove spaces between occupations consisting of several words, such as ");
            System.out.println("\"college student\" -> \"collegestudent\"");
        }
        else{	    
	    String[] genres = args[0].split("\\|");
       	    String work = args[1].toLowerCase(); 
	    //-------------------------------------------------------------------
	    //toLowerCase may fail for ! character? or \?		
	    //-------------------------------------------------------------------
            HashMap<String, Integer> workID = new HashMap<>();
            setOccupationHash(workID); // now workID contains all mappings
            try {
		Integer occup_id = workID.get(work);
		boolean is_other = false;
		//START CHECK FOR OCCUPATION INPUT
		if(occup_id == null){
			//either some wrong string, or other type of occupation
			if(work.length() > 50){
				System.out.println("There is no such occupation (name is too long)");
				return;	
			}
			else{
				is_other = true;
				occup_id = 0;
				System.out.println("Since we can't recognize occupation " + work + ", we will regard it as 'other'"); 
			}
		}
		//END

		//START CHECK FOR GENRE INPUT
		if(genres.length == 0){
			System.out.println("Please enter valid input");
			return;
		}
		if(genres.length > 10){
			//if too many genres
			System.out.println("Input for genre is too long, please try to include a genre not more that one time.");
			return;
		}
		boolean is_empty = true;
		for(String genre : genres){
			//if the genre string is too long
			if(genre.length() > 50){
				System.out.println("There are no movies with genre " + genre);
				System.out.println("Use '|' character to split genres");
				return;
			}
			if(genre.length() > 0){
				is_empty = false;
			}
			
		}
		if(is_empty){
			System.out.println("Please enter valid input");
			return;
		}
		//END
                ArrayList<Integer> userID = getUsers(occup_id);
                ArrayList<Integer> movieID = getMovies(genres);
                Collections.sort(userID);
		//Check if movieID is empty
		if(movieID.size() <= 0){
			System.out.println("No movie found that satisfies requested genres: " + args[0]);
			return;
		}	
                Collections.sort(movieID);
		String output;
		if(is_other){
			output = "other";
		}else{
			output = args[1];
		}
                System.out.printf("The average rating for %s is: %.2f\n", output, scanRatings(userID, movieID));
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

        BufferedReader scan = new BufferedReader(new FileReader(new File("data/ratings.dat")));
        int count = 0;
        int i = 0;
       	int j = 0;
        int sum = 0;
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
       // double not_rounded =  (double)(sum) / (double)(count);
       //return Math.round(not_rounded * 100.0) / 100.0;
        return (double)sum / (double)count;
    }

    // This function returns userID-s with matching occupation
    private static ArrayList<Integer> getUsers(Integer occupation) throws IOException {
        // ! --users.dat--
        // UserID::Gender::Age::Occupation::Zip-code

        BufferedReader scan = new BufferedReader(new FileReader( new File("data/users.dat")));
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
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/movies.dat")));
        ArrayList<Integer> list = new ArrayList<Integer>();
        boolean contains;
        String line;
        while ((line = scan.readLine()) != null) {
            String[] movie = line.split("::");
	    String[] genres_list = movie[2].split("\\|");
            contains = true;
            for(String s : genres){
		if(s.length() == 0){
			continue;
		}
		boolean found = false;
		for(String g: genres_list){
			if(s.toLowerCase().equals(g.toLowerCase())){
				found = true;
			}
		}
		if(!found){
			contains = false;
			break;
		}
	    }
	    
            //for (String genre : genres) {
            //    if (!movie[2].toLowerCase().contains(genre.toLowerCase())) contains = false;
            //}
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
