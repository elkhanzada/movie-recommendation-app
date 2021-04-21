import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.lang.Math;

//how to do autocompletion?

public class Main {
    public static void main(String[] args) {
	    if (args.length != 3 && args.length != 4) {
            System.out.println("Please, pass exactly 3 or 4 arguments!");
            System.out.print("Try to remove spaces between occupations consisting of several words, such as ");
            System.out.println("\"college student\" -> \"collegestudent\"");
        }
        //args[0] = gender, args[1] = age, args[2] = occupation
        else if(args.length == 3){
            boolean is_gender = true;
            boolean is_age = true;
            boolean is_occup = true;
            if(args[0].length() < 1){
                System.out.println("First argument is empty, we will provide top 10 movies ignoring the gender");
                is_gender = false;
            }
            if(args[1].length() < 1){
                System.out.println("Second argument is empty, we will provide top 10 movies ignoring the age");
                is_age = false;
            }
            if(args[2].length() < 1){
                System.out.println("Third argument is empty, we will provide top 10 movies ignoring the occupation");
                is_occup = false;
            }
            //------------------
            String gender = "";
            if(is_gender) {
                gender = args[0].toLowerCase();
                if (!gender.equals("f") && !gender.equals("m")) {
                    System.out.println("Please pass your gender as M/m if you are male and F/f if you are female");
                    return;
                }
            }
            //------------------
            Integer age = 0;
            if(is_age) {
                try {
                    age = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    System.out.println("The age you provided is not a valid integer or too long. Please pass your age as an integer");
                    return;
                }
                age = setAge(age);
            }
            //------------------
            Integer occup_id = 0;
            if(is_occup) {
                String work = args[2].toLowerCase();
                HashMap<String, Integer> workID = new HashMap<>();
                setOccupationHash(workID);
                occup_id = workID.get(work);
                boolean is_other = false;
                //START CHECK FOR OCCUPATION INPUT
                if (occup_id == null) {
                    //some wrong string
                    if (work.length() > 50) {
                        System.out.println("There is no such occupation (name is too long)");
                        return;
                    }
                    //the type of occupation is - other
                    else {
                        is_other = true;
                        occup_id = 0;
                        System.out.println("Since we can't recognize occupation " + work + ", we will regard it as 'other'");
                    }
                }
            }
            //------------------
            //age, occup_id, gender (in lower case)
            ArrayList<Integer> userIds;
            try{
                userIds= getUsers(gender, age, occup_id);
            }catch(Exception e) {
                System.out.println(e);
                return;
            }
            //What if userIds is empty for this moment??????????????????????????????????
            Collections.sort(userIds);
            ArrayList<Integer> movieIds;
            try {
                movieIds = scanRatings(userIds);
            }catch(Exception e){
                System.out.println(e);
                return;
            }
            //What if movieIds is empty for this moment??????????????????????????????????

        }
    }

    private static Integer setAge(Integer age){
        if(age < 18){
            return 1;
        }else if(age < 25){
            return 18;
        }else if(age < 35){
            return 25;
        }else if(age < 45){
            return 35;
        }else if(age < 50){
            return 45;
        }else if(age < 56){
            return 50;
        }else{
            return 56;
        }
    }

    // This method scans "ratings.dat" file and returns the list of MovieIds' with high ratings
    private static ArrayList<Integer> scanRatings(ArrayList<Integer> userID) throws IOException {
        // ! --ratings.dat--
        // UserID::MovieID::Rating::Timestamp
        BufferedReader scan = new BufferedReader(new FileReader(new File("../../../data/ratings.dat")));
        ArrayList<Integer> list = new ArrayList<Integer>();
        String line;
        int i;
        while ((line = scan.readLine()) != null) {
            String[] rating = line.split("::");
            // Collections.binarySearch() returns a negative number if the item not found;
            i = Collections.binarySearch(userID, Integer.parseInt(rating[0]));
            if (i > -1) { // if we find corresponding and user;
                if (Integer.parseInt(rating[2]) == 4) {
                    list.add(Integer.parseInt(rating[1]));
                } else if (Integer.parseInt(rating[2]) == 5) {
                    list.add(0, Integer.parseInt(rating[1]));
                } else {
                    continue;
                }
            }
        }
        scan.close();
        return list;
    }

    // This function returns array of userIds' with given data
    private static ArrayList<Integer> getUsers(String gender, Integer age, Integer occupation) throws IOException {
        // ! --users.dat--
        // UserID::Gender::Age::Occupation::Zip-code
        BufferedReader scan = new BufferedReader(new FileReader( new File("../../../data/users.dat")));
        ArrayList<Integer> list = new ArrayList<Integer>();
        String line;
        while ((line = scan.readLine()) != null) {
            String[] user = line.split("::");
            if (user[1].toLowerCase().equals(gender) && Integer.parseInt(user[2]) == age && Integer.parseInt(user[3]) == occupation){
                list.add(Integer.parseInt(user[0]));
            }
        }
        scan.close();
        return list;
    }

    // This function returns movieID-s with matching genres
    private static ArrayList<Integer> getMovies(String[] genres) throws IOException {
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
