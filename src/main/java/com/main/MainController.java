package com.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public String getMovies(@RequestBody String name) {
        JSONObject js = new JSONObject(name);
        ArrayList<String> args = new ArrayList<>();
        for(String key: js.keySet())
            args.add(js.getString(key));
        return Main.getMovies((String[]) args.toArray());
    }
}