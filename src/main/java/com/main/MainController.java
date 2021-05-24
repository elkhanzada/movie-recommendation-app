package com.main;
import java.util.*;
import org.json.JSONObject;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/users/recommendations")
    public String getMovies(@RequestBody String input) {
        JSONObject js = null;
        try{
            js = new JSONObject(input.toLowerCase());
        }catch (Exception e){
            return "Please pass json in right format\n";
        }
        HashMap<String,String> args = new HashMap<>();
        for(String key: js.keySet())
            args.put(key,js.getString(key));
        return Main.getMovies(args);
    }
    @GetMapping("/movies/recommendations")
    public String getRecommendations(@RequestBody String input) throws IOException {
        JSONObject js = null;
        try {
            js = new JSONObject(input.toLowerCase());
        }catch (Exception e){
            return "Please pass json in right format\n";
        }
        HashMap<String,String> args = new HashMap<>();
        try {
            for (String key : js.keySet()) {
                if (key.equals("limit")) args.put(key, Integer.toString(js.getInt(key)));
                else args.put(key, js.getString(key));
            }
        }catch (Exception e) {
            return "Value of limit must be integer!\n";
        }
        return Main.recommendMovies(args);
    }
}