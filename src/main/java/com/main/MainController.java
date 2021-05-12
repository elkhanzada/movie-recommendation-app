package com.main;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import com.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/users/recommend")
    public String getMovies(@RequestBody String input) {
        JSONObject js = new JSONObject(input);
        HashMap<String,String> args = new HashMap<>();
        for(String key: js.keySet())
            args.put(key,js.getString(key));
        return Main.getMovies(args);
    }
}