package com.main;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainTest {
    @Autowired
    private MockMvc mvc;

    private String getResult(String channel, String input) throws Exception {
        JSONObject js = new JSONObject(input);
        MultiValueMap<String,String> args = new LinkedMultiValueMap<>();
        for(String k: js.keySet())
            args.add(k,js.getString(k));
        if(channel.equals("movies")){
            return this.mvc.perform(get("/" + channel + "/recommendations")
                    .params(args))
                    .andReturn().getResponse().getContentAsString();
        }else {
            return this.mvc.perform(get("/" + channel + "/recommendations")
                    .params(args))
                    .andReturn().getResponse().getContentAsString();
        }

    }

    @Test
    public void testMoreArgsInMovies() {
        try {
            String actual = getResult("movies", "{'title':'Toy Story', 'bla1': 'bla', 'bla2': 'bla'}");
            String expected = "Movie does not exist!\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testFourArgs() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "M");
            json.put("age", "25");
            json.put("occupation", "gradstudent");
            json.put("genre", "action");
            JSONArray jsonArray = new JSONArray("[ { \"genres\": \"action|adventure|fantasy|sci-fi\", \"link\": \"http://www.imdb.com/title/tt0076759\", \"movieID\": 260, \"title\": \"star wars: episode iv - a new hope (1977)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzVlY2MwMjktM2E4OS00Y2Y3LWE3ZjctYzhkZGM3YzA1ZWM2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"action|crime|drama\", \"link\": \"http://www.imdb.com/title/tt0068646\", \"movieID\": 858, \"title\": \"godfather, the (1972)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"action|adventure|drama|sci-fi|war\", \"link\": \"http://www.imdb.com/title/tt0080684\", \"movieID\": 1196, \"title\": \"star wars: episode v - the empire strikes back (1980)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"action|adventure\", \"link\": \"http://www.imdb.com/title/tt0082971\", \"movieID\": 1198, \"title\": \"raiders of the lost ark (1981)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjA0ODEzMTc1Nl5BMl5BanBnXkFtZTcwODM2MjAxNA@@..jpg\" }, { \"genres\": \"action|sci-fi|thriller\", \"link\": \"http://www.imdb.com/title/tt0133093\", \"movieID\": 2571, \"title\": \"matrix, the (1999)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"action|adventure|comedy|romance\", \"link\": \"http://www.imdb.com/title/tt0093779\", \"movieID\": 1197, \"title\": \"princess bride, the (1987)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"action|sci-fi|thriller\", \"link\": \"http://www.imdb.com/title/tt0088247\", \"movieID\": 1240, \"title\": \"terminator, the (1984)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BZGE2ZDgyOWUtNzdiNS00OTI3LTkwZGQtMTMwNzM4YWUxNGNhXkEyXkFqcGdeQXVyNjU2NjA5NjM@..jpg\" }, { \"genres\": \"action|drama|war\", \"link\": \"http://www.imdb.com/title/tt0112573\", \"movieID\": 110, \"title\": \"braveheart (1995)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMzkzMmU0YTYtOWM3My00YzBmLWI0YzctOGYyNTkwMWE5MTJkXkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"action|sci-fi|thriller|war\", \"link\": \"http://www.imdb.com/title/tt0090605\", \"movieID\": 1200, \"title\": \"aliens (1986)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BZGU2OGY5ZTYtMWNhYy00NjZiLWI0NjUtZmNhY2JhNDRmODU3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"action|horror|sci-fi|thriller\", \"link\": \"http://www.imdb.com/title/tt0078748\", \"movieID\": 1214, \"title\": \"alien (1979)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMmQ2MmU3NzktZjAxOC00ZDZhLTk4YzEtMDMyMzcxY2IwMDAyXkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" } ]");
            String actual = getResult("users", json.toString());
            String expected = jsonArray.toString(2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLongOccup() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "");
            json.put("occupation", "sasklasjfasdsadasfsafsafasfsafsafsafsfasfaskjflfkjasfklsfjaslkfjasflkjaasd");
            json.put("genre", "");
            String actual = getResult("users", json.toString());
            String expected = "There is no such registered occupation as sasklasjfasdsadasfsafsafasfsafsafsafsfasfaskjflfkjasfklsfjaslkfjasflkjaasd!\nIf you want to see some other ratings, please use \"other\" as an argument\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testStringAge() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "age");
            json.put("occupation", "");
            json.put("genre", "");
            String actual = getResult("users", json.toString());
            String expected = "Please, enter a valid argument for age!\nIt should be a positive integer, containing only digits!\nAge shall not exceed " + Integer.MAX_VALUE + "\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testPossibleAges() {
        try {
            JSONObject json = new JSONObject();
            JSONArray jsonArr = null;
            String actual = null;
            String expected = null;
            json.put("gender", "F");
            json.put("age", "1");
            json.put("occupation", "");
            json.put("genre", "");
            String data = "[ { \"genres\": \"animation|children's|comedy\", \"link\": \"http://www.imdb.com/title/tt0120363\", \"movieID\": 3114, \"title\": \"toy story 2 (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMWM5ZDcxMTYtNTEyNS00MDRkLWI3YTItNThmMGExMWY4NDIwXkEyXkFqcGdeQXVyNjUwNzk3NDc@..jpg\" }, { \"genres\": \"comedy|romance\", \"link\": \"http://www.imdb.com/title/tt0138097\", \"movieID\": 2396, \"title\": \"shakespeare in love (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BM2ZkNjM5MjEtNTBlMC00OTI5LTgyYmEtZDljMzNmNzhiNzY0XkEyXkFqcGdeQXVyNDYyMDk5MTU@..jpg\" }, { \"genres\": \"animation|children's|comedy\", \"link\": \"http://www.imdb.com/title/tt0114709\", \"movieID\": 1, \"title\": \"toy story (1995)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMDU2ZWJlMjktMTRhMy00ZTA5LWEzNDgtYmNmZTEwZTViZWJkXkEyXkFqcGdeQXVyNDQ2OTk4MzI@..jpg\" }, { \"genres\": \"thriller\", \"link\": \"http://www.imdb.com/title/tt0167404\", \"movieID\": 2762, \"title\": \"sixth sense, the (1999)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"adventure|children's|drama|musical\", \"link\": \"http://www.imdb.com/title/tt0032138\", \"movieID\": 919, \"title\": \"wizard of oz, the (1939)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"comedy|romance|war\", \"link\": \"http://www.imdb.com/title/tt0109830\", \"movieID\": 356, \"title\": \"forrest gump (1994)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"comedy|drama\", \"link\": \"http://www.imdb.com/title/tt0169547\", \"movieID\": 2858, \"title\": \"american beauty (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNTBmZWJkNjctNDhiNC00MGE2LWEwOTctZTk5OGVhMWMyNmVhXkEyXkFqcGdeQXVyMTMxODk2OTU@..jpg\" }, { \"genres\": \"animation|children's|comedy\", \"link\": \"http://www.imdb.com/title/tt0120623\", \"movieID\": 2355, \"title\": \"bug's life, a (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNThmZGY4NzgtMTM4OC00NzNkLWEwNmEtMjdhMGY5YTc1NDE4XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"children's|comedy|musical\", \"link\": \"http://www.imdb.com/title/tt0058331\", \"movieID\": 1028, \"title\": \"mary poppins (1964)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNmJkODczNjItNDI5Yy00MGI1LTkyOWItZDNmNjM4ZGI1ZDVlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMDI2NDg0NQ@@..jpg\" }, { \"genres\": \"action|adventure|fantasy|sci-fi\", \"link\": \"http://www.imdb.com/title/tt0076759\", \"movieID\": 260, \"title\": \"star wars: episode iv - a new hope (1977)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzVlY2MwMjktM2E4OS00Y2Y3LWE3ZjctYzhkZGM3YzA1ZWM2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" } ]";
            jsonArr = new JSONArray(data);
            actual = getResult("users", json.toString());
            expected = jsonArr.toString(2);
            assertEquals(expected, actual);
            json.put("age", "18");
            data = "[ { \"genres\": \"drama\", \"link\": \"http://www.imdb.com/title/tt0111161\", \"movieID\": 318, \"title\": \"shawshank redemption, the (1994)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@..jpg\" }, { \"genres\": \"comedy|drama\", \"link\": \"http://www.imdb.com/title/tt0169547\", \"movieID\": 2858, \"title\": \"american beauty (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNTBmZWJkNjctNDhiNC00MGE2LWEwOTctZTk5OGVhMWMyNmVhXkEyXkFqcGdeQXVyMTMxODk2OTU@..jpg\" }, { \"genres\": \"crime|thriller\", \"link\": \"http://www.imdb.com/title/tt0114814\", \"movieID\": 50, \"title\": \"usual suspects, the (1995)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"action|adventure|comedy|romance\", \"link\": \"http://www.imdb.com/title/tt0093779\", \"movieID\": 1197, \"title\": \"princess bride, the (1987)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"comedy|drama\", \"link\": \"http://www.imdb.com/title/tt0118799\", \"movieID\": 2324, \"title\": \"life is beautiful (la vita � bella) (1997)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BYmJmM2Q4NmMtYThmNC00ZjRlLWEyZmItZTIwOTBlZDQ3NTQ1XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"drama|war\", \"link\": \"http://www.imdb.com/title/tt0108052\", \"movieID\": 527, \"title\": \"schindler's list (1993)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@..jpg\" }, { \"genres\": \"drama|thriller\", \"link\": \"http://www.imdb.com/title/tt0102926\", \"movieID\": 593, \"title\": \"silence of the lambs, the (1991)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"comedy\", \"link\": \"http://www.imdb.com/title/tt0120601\", \"movieID\": 2997, \"title\": \"being john malkovich (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BYmUxY2MyOTQtYjRlMi00ZWEwLTkzODctZDMxNDcyNTFhYjNjXkEyXkFqcGdeQXVyNDk3NzU2MTQ@..jpg\" }, { \"genres\": \"thriller\", \"link\": \"http://www.imdb.com/title/tt0167404\", \"movieID\": 2762, \"title\": \"sixth sense, the (1999)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"animation|comedy\", \"link\": \"http://www.imdb.com/title/tt0108598\", \"movieID\": 1148, \"title\": \"wrong trousers, the (1993)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNDBmNGU4MTEtMmI2Mi00NjU1LTgxNTktMDQ1MDE0Y2MwMjYwXkEyXkFqcGdeQXVyNTAyODkwOQ@@..jpg\" } ]";
            jsonArr = new JSONArray(data);
            actual = getResult("users", json.toString());
            expected = jsonArr.toString(2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEmptyGender() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "");
            json.put("age", "25");
            json.put("occupation", "");
            json.put("genre", "");
            String data = "[ { \"genres\": \"drama\", \"link\": \"http://www.imdb.com/title/tt0111161\", \"movieID\": 318, \"title\": \"shawshank redemption, the (1994)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@..jpg\" }, { \"genres\": \"action|adventure\", \"link\": \"http://www.imdb.com/title/tt0082971\", \"movieID\": 1198, \"title\": \"raiders of the lost ark (1981)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjA0ODEzMTc1Nl5BMl5BanBnXkFtZTcwODM2MjAxNA@@..jpg\" }, { \"genres\": \"action|adventure|fantasy|sci-fi\", \"link\": \"http://www.imdb.com/title/tt0076759\", \"movieID\": 260, \"title\": \"star wars: episode iv - a new hope (1977)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzVlY2MwMjktM2E4OS00Y2Y3LWE3ZjctYzhkZGM3YzA1ZWM2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"action|crime|drama\", \"link\": \"http://www.imdb.com/title/tt0068646\", \"movieID\": 858, \"title\": \"godfather, the (1972)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"crime|thriller\", \"link\": \"http://www.imdb.com/title/tt0114814\", \"movieID\": 50, \"title\": \"usual suspects, the (1995)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"drama|war\", \"link\": \"http://www.imdb.com/title/tt0108052\", \"movieID\": 527, \"title\": \"schindler's list (1993)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@..jpg\" }, { \"genres\": \"mystery|thriller\", \"link\": \"http://www.imdb.com/title/tt0047396\", \"movieID\": 904, \"title\": \"rear window (1954)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNGUxYWM3M2MtMGM3Mi00ZmRiLWE0NGQtZjE5ODI2OTJhNTU0XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"animation|comedy|thriller\", \"link\": \"http://www.imdb.com/title/tt0112691\", \"movieID\": 745, \"title\": \"close shave, a (1995)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzliZjhkODctMGJjOS00YWQ3LWEzMjgtYmZjNzMwMDYwYWNjXkEyXkFqcGdeQXVyNTAyODkwOQ@@..jpg\" }, { \"genres\": \"thriller\", \"link\": \"http://www.imdb.com/title/tt0167404\", \"movieID\": 2762, \"title\": \"sixth sense, the (1999)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"animation|comedy\", \"link\": \"http://www.imdb.com/title/tt0108598\", \"movieID\": 1148, \"title\": \"wrong trousers, the (1993)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNDBmNGU4MTEtMmI2Mi00NjU1LTgxNTktMDQ1MDE0Y2MwMjYwXkEyXkFqcGdeQXVyNTAyODkwOQ@@..jpg\" } ]";
            JSONArray jsonArray = new JSONArray(data);
            String actual = getResult("users", json.toString());
            String expected = jsonArray.toString(2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLongGender() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "akfjaskfljflsakfjasflkaj");
            json.put("age", "");
            json.put("occupation", "");
            json.put("genre", "");
            String actual = getResult("users", json.toString());
            String expected = "Please, provide a proper argument for Gender\nIt shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLessMovie() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "1");
            json.put("occupation", "Doctor");
            json.put("genre", "Horror");
            String data = "[ { \"genres\": \"comedy|horror\", \"link\": \"http://www.imdb.com/title/tt0087332\", \"movieID\": 2716, \"title\": \"ghostbusters (1984)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTkxMjYyNzgwMl5BMl5BanBnXkFtZTgwMTE3MjYyMTE@..jpg\" }, { \"genres\": \"action|horror\", \"link\": \"http://www.imdb.com/title/tt0073195\", \"movieID\": 1387, \"title\": \"jaws (1975)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMmVmODY1MzEtYTMwZC00MzNhLWFkNDMtZjAwM2EwODUxZTA5XkEyXkFqcGdeQXVyNTAyODkwOQ@@..jpg\" }, { \"genres\": \"horror\", \"link\": \"http://www.imdb.com/title/tt0081505\", \"movieID\": 1258, \"title\": \"shining, the (1980)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BZWFlYmY2MGEtZjVkYS00YzU4LTg0YjQtYzY1ZGE3NTA5NGQxXkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"action|horror|sci-fi|thriller\", \"link\": \"http://www.imdb.com/title/tt0078748\", \"movieID\": 1214, \"title\": \"alien (1979)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMmQ2MmU3NzktZjAxOC00ZDZhLTk4YzEtMDMyMzcxY2IwMDAyXkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"horror|thriller\", \"link\": \"http://www.imdb.com/title/tt0054215\", \"movieID\": 1219, \"title\": \"psycho (1960)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNTQwNDM1YzItNDAxZC00NWY2LTk0M2UtNDIwNWI5OGUyNWUxXkEyXkFqcGdeQXVyNzkwMjQ5NzM@..jpg\" }, { \"genres\": \"horror|romance\", \"link\": \"http://www.imdb.com/title/tt0162661\", \"movieID\": 3081, \"title\": \"sleepy hollow (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTQ4Yjk5ODEtNGFkOS00OTY1LTgwYTYtOTFkNmU2NzBmNGM2XkEyXkFqcGdeQXVyNDQ2MTMzODA@..jpg\" }, { \"genres\": \"comedy|horror\", \"link\": \"http://www.imdb.com/title/tt0072431\", \"movieID\": 1278, \"title\": \"young frankenstein (1974)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTEwNjg2MjM2ODFeQTJeQWpwZ15BbWU4MDQ1MDU5OTEx..jpg\" }, { \"genres\": \"action|adventure|horror|thriller\", \"link\": \"http://www.imdb.com/title/tt0120616\", \"movieID\": 2617, \"title\": \"mummy, the (1999)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"horror\", \"link\": \"http://www.imdb.com/title/tt0100157\", \"movieID\": 3499, \"title\": \"misery (1990)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzY0ODQ3MTMxN15BMl5BanBnXkFtZTgwMDkwNTg4NjE@..jpg\" }, { \"genres\": \"horror|thriller\", \"link\": \"http://www.imdb.com/title/tt0063522\", \"movieID\": 2160, \"title\": \"rosemary's baby (1968)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BZmEwZGU2NzctYzlmNi00MGJkLWE3N2MtYjBlN2ZhMGJkZTZiXkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" } ]";
            JSONArray jsonArray = new JSONArray(data);
            String actual = getResult("users", json.toString());
            String expected = jsonArray.toString(2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEmptyGenre() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "");
            json.put("occupation", "Doctor");
            json.put("genre", "Adventure|Bratan");
            String actual = getResult("users", json.toString());
            String expected = "There is not such registered genre as bratan\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSameGenres() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "25");
            json.put("occupation", "Doctor");
            json.put("genre", "Adventure|Adventure");
            String actual = getResult("users", json.toString());
            String expected = "Please enter valid input for genres\nGenres should not repeat\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testMoreArgsInUsers() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "25");
            json.put("occupation", "Doctor");
            json.put("genre", "Adventure|Adventure");
            json.put("excessarg", "0");
            String actual = getResult("users", json.toString());
            String expected = "Please enter valid input for genres\n" +
                    "Genres should not repeat\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testRecommend() {
        try {
            JSONObject json = new JSONObject();
            json.put("title", "Wrong Trousers, The (1993)");
            json.put("limit", "1");
            String data = "[{ \"genres\": \"animation|comedy|thriller\", \"link\": \"http://www.imdb.com/title/tt0112691\", \"movieID\": 745, \"title\": \"close shave, a (1995)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzliZjhkODctMGJjOS00YWQ3LWEzMjgtYmZjNzMwMDYwYWNjXkEyXkFqcGdeQXVyNTAyODkwOQ@@..jpg\" }]";
            JSONArray jsonArray = new JSONArray(data);
            String actual = getResult("movies", json.toString());
            String expected = jsonArray.toString(2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testTitleNull() {
        try {
            JSONObject json = new JSONObject();
            json.put("limit", "20");
            String actual = getResult("movies", json.toString());
            String expected = "Movie does not exist!\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLimitNull() {
        try {
            JSONObject json = new JSONObject();
            json.put("title", "Bratan");
            String actual = getResult("movies", json.toString());
            String expected = "Movie does not exist!\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testMoreLimitWithFewMovies() {
        try {
            JSONObject json = new JSONObject();
            json.put("title", "Across the Sea of Time (1995)");
            json.put("limit", "50");
            String actual = getResult("movies", json.toString());
            JSONArray jsonArray = new JSONArray("[ { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0118147\", \"movieID\": 1147, \"title\": \"when we were kings (1996)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTQ4NjU3MzI3NF5BMl5BanBnXkFtZTgwMjYyODU3MDI@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0110057\", \"movieID\": 246, \"title\": \"hoop dreams (1994)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMWMxNDAxN2QtMjQxYS00NzI4LWJlMTctOGJkNTdkNmMyYmJiXkEyXkFqcGdeQXVyNTI4MjkwNjA@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0096257\", \"movieID\": 1189, \"title\": \"thin blue line, the (1988)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0088178\", \"movieID\": 2859, \"title\": \"stop making sense (1984)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjUxZmZmYzQtMzgzNy00OGVmLWE5M2ItOTA5MTZiMTk4MTY5XkEyXkFqcGdeQXVyNTI4MjkwNjA@..jpg\" }, { \"genres\": \"comedy|documentary\", \"link\": \"http://www.imdb.com/title/tt0098213\", \"movieID\": 2064, \"title\": \"roger & me (1989)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjRlYWVlZWUtZDE3NS00NjY3LTgwMjItMTc1OGM2ZDI1M2IzXkEyXkFqcGdeQXVyNDQzMDg4Nzk@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0109508\", \"movieID\": 162, \"title\": \"crumb (1994)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BYTViMDM5YjktMDhjNy00MjZjLTg2ODctOGI3MzkzYWNiODA0XkEyXkFqcGdeQXVyNTAyODkwOQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0117293\", \"movieID\": 1361, \"title\": \"paradise lost: the child murders at robin hood hills (1996)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BN2EzNWNlZGYtMjQ1YS00YTI4LThkYTctNjRjYzI1NDdmNjEyXkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0108515\", \"movieID\": 556, \"title\": \"war room, the (1993)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzU4NThlNmUtMDdiMi00NzgyLTk4MTQtZTI0NjQ2OTg0ZDFjXkEyXkFqcGdeQXVyNjQ2MjQ5NzM@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0186508\", \"movieID\": 2677, \"title\": \"buena vista social club (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BZjc2MDY3MjgtYmI4YS00MmQ0LThkNmUtNDQxNWJkZGRmNGFhXkEyXkFqcGdeQXVyMTMxMTY0OTQ@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0181288\", \"movieID\": 3007, \"title\": \"american movie (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjMxMDFhYzQtZDZmZS00ZDBjLWIzY2QtNDBiMDQ5YjBlNTk1XkEyXkFqcGdeQXVyNTAyODkwOQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0164312\", \"movieID\": 3077, \"title\": \"42 up (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjAwNjYxMzUwNF5BMl5BanBnXkFtZTcwMjI3OTgxMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0112651\", \"movieID\": 581, \"title\": \"celluloid closet, the (1995)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0120661\", \"movieID\": 1797, \"title\": \"everest (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTUwNzA0ODg1OV5BMl5BanBnXkFtZTcwMzA3MDUxMQ@@..jpg\" }, { \"genres\": \"documentary|war\", \"link\": \"http://www.imdb.com/title/tt0085809\", \"movieID\": 1289, \"title\": \"koyaanisqatsi (1983)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BZjlmMWVhYjQtNTZmOC00OTE4LWIxMGItOTg2OTlkZTZkYmI2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0116481\", \"movieID\": 2330, \"title\": \"hands on a hard body (1996)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTQxOTYxODYyN15BMl5BanBnXkFtZTcwMDI3MDg4OQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0117040\", \"movieID\": 1111, \"title\": \"microcosmos (microcosmos: le peuple de l'herbe) (1996)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTQ4MDU2ODcyNF5BMl5BanBnXkFtZTcwOTk2NDEzMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0120370\", \"movieID\": 2693, \"title\": \"trekkies (1997)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTc0NjU1NzI0OV5BMl5BanBnXkFtZTcwOTIwMzMyMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0082252\", \"movieID\": 3679, \"title\": \"decline of western civilization, the (1981)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTkzOTg3MzUxMl5BMl5BanBnXkFtZTcwMDg1OTM2MQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0100332\", \"movieID\": 1192, \"title\": \"paris is burning (1990)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzk1ODAzNTQtYTQyOC00ZTcxLWE3Y2ItZmJhNGI2MGM5NjkwXkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0107472\", \"movieID\": 363, \"title\": \"wonderful, horrible life of leni riefenstahl, the (die macht der bilder) (1993)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzQ0OTM1NjQ2M15BMl5BanBnXkFtZTYwMzkwNTE5..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0192335\", \"movieID\": 3182, \"title\": \"mr. death: the rise and fall of fred a. leuchter jr. (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BOGEwNjI3N2MtMjQ3Yi00ZDE2LWE2YmMtYzU5YTQ0ODlkMjEzXkEyXkFqcGdeQXVyNTc4Njg5MjA@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0119107\", \"movieID\": 1649, \"title\": \"fast, cheap & out of control (1997)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjEyMTQ4OTg3NF5BMl5BanBnXkFtZTcwNzE3NDIyMQ@@..jpg\" }, { \"genres\": \"comedy|documentary\", \"link\": \"http://www.imdb.com/title/tt0124295\", \"movieID\": 1827, \"title\": \"big one, the (1997)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTUxNDgzNTMyNF5BMl5BanBnXkFtZTcwODM3NTYyMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0108328\", \"movieID\": 549, \"title\": \"thirty-two short films about glenn gould (1993)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjA4MTA3NjI0N15BMl5BanBnXkFtZTcwMzUxOTAwMQ@@..jpg\" }, { \"genres\": \"documentary|drama\", \"link\": \"http://www.imdb.com/title/tt0116913\", \"movieID\": 1050, \"title\": \"looking for richard (1996)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BOTdkZTUzMGUtYzNhOC00ODEyLWI2MjktNTY1ZjRhM2Y2ZDYxXkEyXkFqcGdeQXVyNjUwNzk3NDc@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0060371\", \"movieID\": 3653, \"title\": \"endless summer, the (1966)\", \"poster\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0110480\", \"movieID\": 759, \"title\": \"maya lin: a strong clear vision (1994)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTIzMTUxMjE1Nl5BMl5BanBnXkFtZTcwOTcxNzkxMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0233687\", \"movieID\": 3859, \"title\": \"eyes of tammy faye, the (2000)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTU5NTU0Mjc0M15BMl5BanBnXkFtZTcwMTI2NTc0MQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0144801\", \"movieID\": 3281, \"title\": \"brandon teena story, the (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTI2NTAzMTQ5OV5BMl5BanBnXkFtZTcwNTgxNDkxMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0109934\", \"movieID\": 602, \"title\": \"great day in harlem, a (1994)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjA5MzU5NTE2Ml5BMl5BanBnXkFtZTcwOTUzNDEzMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0112373\", \"movieID\": 116, \"title\": \"anne frank remembered (1995)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTQ3NjcxMjEwMV5BMl5BanBnXkFtZTYwODMwNTc4..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0218043\", \"movieID\": 3327, \"title\": \"beyond the mat (2000)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTQ5NDUzODkyOF5BMl5BanBnXkFtZTcwNjY3OTIyMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0114805\", \"movieID\": 206, \"title\": \"unzipped (1995)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjAwODIwMTk2Nl5BMl5BanBnXkFtZTcwMzA4NDUyMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0097372\", \"movieID\": 3338, \"title\": \"for all mankind (1989)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNGM0YWJhNDgtZjk4OC00YTEzLThmZjUtODllMTFmNjQ4YWVlXkEyXkFqcGdeQXVyNjQ2MjQ5NzM@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0094980\", \"movieID\": 3680, \"title\": \"decline of western civilization part ii: the metal years, the (1988)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BZGQwODQ0NmYtN2JlYy00YzA2LThlMGEtMDk1MWMwZWM5MGIxXkEyXkFqcGdeQXVyMjI4MjA5MzA@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0103767\", \"movieID\": 3677, \"title\": \"baraka (1992)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMjA1Njc1NzctMTQxYy00ZDc0LWEzOTYtNmMyYjJjMWQ4MWJiXkEyXkFqcGdeQXVyNTA4NzY1MzY@..jpg\" }, { \"genres\": \"documentary|musical\", \"link\": \"http://www.imdb.com/title/tt0096328\", \"movieID\": 3142, \"title\": \"u2: rattle and hum (1988)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTY4MDYzNzQ4NF5BMl5BanBnXkFtZTcwMjAxODEzMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0208261\", \"movieID\": 3188, \"title\": \"life and times of hank greenberg, the (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTI1NDE3OTY2MV5BMl5BanBnXkFtZTcwNzE2NDIyMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0150230\", \"movieID\": 2323, \"title\": \"cruise, the (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTY5Njg5NTAxMV5BMl5BanBnXkFtZTcwNTIzMzIzMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0236216\", \"movieID\": 3539, \"title\": \"filth and the fury, the (2000)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTk5MTk0NDEyNF5BMl5BanBnXkFtZTYwNTM4MTk4..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0200849\", \"movieID\": 3002, \"title\": \"my best fiend (mein liebster feind) (1999)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTQ1NzQ2ODI4MF5BMl5BanBnXkFtZTgwOTc1NDgwMzE@..jpg\" }, { \"genres\": \"comedy|documentary\", \"link\": \"http://www.imdb.com/title/tt0236388\", \"movieID\": 3865, \"title\": \"original kings of comedy, the (2000)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BMTI5NDY1NDU1M15BMl5BanBnXkFtZTcwODcyNTMyMQ@@..jpg\" }, { \"genres\": \"documentary\", \"link\": \"http://www.imdb.com/title/tt0141986\", \"movieID\": 1865, \"title\": \"wild man blues (1998)\", \"poster\": \"https://m.media-amazon.com/images/M/MV5BNzU1Njg3ODcyNl5BMl5BanBnXkFtZTcwMzcwNzgxMQ@@..jpg\" } ]");
            String expected = jsonArray.toString(2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testWrongLimit() {
        try {
            JSONObject json = new JSONObject();
            json.put("title", "Across the Sea of Time (1995)");
            json.put("limit", "50bratan");
            String actual = getResult("movies", json.toString());
            String expected = "Limit must be an integer!\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }
}
