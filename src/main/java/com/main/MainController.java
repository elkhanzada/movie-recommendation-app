package com.main;
import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final UserDAL userDAL;
    private final MovieDAL movieDAL;
    private final RatingDAL ratingDAL;
    public MainController(UserDAL userDAL, MovieDAL movieDAL, RatingDAL ratingDAL) throws IOException {
        this.userDAL = userDAL;
        this.movieDAL = movieDAL;
        this.ratingDAL = ratingDAL;
        movieDAL.deleteAll();
        userDAL.deleteAll();
        ratingDAL.deleteAll();
        saveUsers();
        saveMovies();
        saveRatings();
    }


    @GetMapping("/users/recommendations")
    public String getMovies(
            @RequestParam(value = "gender", defaultValue = "") String gender,
            @RequestParam(value = "age", defaultValue = "") String age,
            @RequestParam(value = "occupation", defaultValue = "") String occupation,
            @RequestParam(value = "genre", defaultValue = "") String genre
    ) {
        try{
            HashMap<String,String> args = new HashMap<>();
            args.put("gender",gender);
            args.put("age",age);
            args.put("occupation",occupation);
            args.put("genre",genre);
            return Main.getMovies(args,userDAL,movieDAL,ratingDAL);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage()+" Please pass json in right format\n";
        }
    }
    @GetMapping("/users")
    public List<User> getMoviesBeta()
    {
        List<User> users = userDAL.getSpecificUsers(-1,-1,"");
        return users;
    }
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return movieDAL.getSpecificMovies(new String[]{""},"toy story (1995)");
    }

    private void saveMovies() throws IOException {
        // ! --movies.dat--
        // MovieID::Title::Genres
        BufferedReader scan1 = new BufferedReader(new FileReader(new File("data/movie_poster.csv")));
        BufferedReader scan2 = new BufferedReader(new FileReader(new File("data/links.dat")));
        BufferedReader scan3 = new BufferedReader(new FileReader(new File("data/movies.dat")));
        String line;
        HashMap<Integer,String > moviePosters = new HashMap<>();
        HashMap<Integer,String > movieLinks = new HashMap<>();
        while ((line = scan1.readLine()) != null) {
            String[] movie_poster = line.split(",");
            moviePosters.put(Integer.parseInt(movie_poster[0]),movie_poster[1]);
        }
        scan1.close();
        while ((line = scan2.readLine()) != null) {
            String[] movie_link = line.split("::");
            movieLinks.put(Integer.parseInt(movie_link[0]),movie_link[1]);
        }
        scan2.close();
        while ((line = scan3.readLine()) != null) {
            String[] movie = line.split("::");
            String poster = moviePosters.get(Integer.parseInt(movie[0]));
            String link = movieLinks.get(Integer.parseInt(movie[0]));
            Movie mv = new Movie(Integer.parseInt(movie[0]),movie[1].toLowerCase(),movie[2].toLowerCase(),poster==null?"":poster, link==null?"":"http://www.imdb.com/title/tt"+link);
            movieDAL.addNewMovie(mv);
        }
        scan3.close();
    }

    private void saveUsers() throws IOException {
        // ! --users.dat--
        // UserID::Gender::Age::Occupation::Zip-code
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/users.dat")));
        String line;
        while ((line = scan.readLine()) != null) {
            String[] user = line.split("::");
            userDAL.addNewUser(new User(Integer.parseInt(user[0]),user[1].toLowerCase(),Integer.parseInt(user[2]),Integer.parseInt(user[3])));
        }
    }
    private void saveRatings() throws IOException {
        // ! --ratings.dat--
        // UserID::MovieID::Rating::Timestamp
        BufferedReader scan = new BufferedReader(new FileReader(new File("data/ratings.dat")));
        String line;
        List<Rating> ratings  = new ArrayList<>();
        while ((line = scan.readLine()) != null) {
            String[] rating = line.split("::");
            Rating rat = new Rating(Integer.parseInt(rating[0]),Integer.parseInt(rating[1]),Integer.parseInt(rating[2]));
            boolean isFound = false;
            for(int i = 0; i<ratings.size();i++){
                Rating rating1 = ratings.get(i);
                if(rating1.getMovieId()==rat.getMovieId()){
                    rating1.setScore(rating1.getScore()+rat.getScore());
                    rating1.setVotes(rating1.getVotes()+1);
                    ratings.set(i,rating1);
                    isFound = true;
                    break;
                }
            }
            if(!isFound)ratings.add(rat);
//            ratingDAL.addNewRating(new Rating(Integer.parseInt(rating[0]),Integer.parseInt(rating[1]),Integer.parseInt(rating[2])));
        }
        ratingDAL.addNewRating(ratings);
    }


    @GetMapping("/movies/recommendations")
    public String getRecommendations( @RequestParam(value = "title", defaultValue = "") String title,
                                      @RequestParam(value = "limit", defaultValue = "") String limit
                                      ) {
        try {
            HashMap<String,String> args = new HashMap<>();
            args.put("title",title);
            args.put("limit",limit);
            return Main.recommendMovies(args,userDAL,movieDAL,ratingDAL);
        }catch (Exception e){
            return "Please pass json in right format\n";
        }
    }
}