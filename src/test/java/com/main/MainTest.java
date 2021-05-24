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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainTest {
    @Autowired
    private MockMvc mvc;

    private String getResult(String channel, String input) throws Exception {
        return this.mvc.perform(get("/" + channel + "/recommendations")
                .content(input))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testMoreArgsInMovies() {
        try {
            String actual = getResult("movies", "{'title':'Toy Story', 'bla1': 'bla', 'bla2': 'bla'}");
            String expected = "Please, pass exactly 1 or 2 keys to JSON!\n";
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
            JSONArray jsonArray = new JSONArray("[ { \"imdb\": \"http://www.imdb.com/title/tt0076759\", \"genre\": \"Action|Adventure|Fantasy|Sci-Fi\", \"title\": \"Star Wars: Episode IV - A New Hope (1977)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0068646\", \"genre\": \"Action|Crime|Drama\", \"title\": \"Godfather, The (1972)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0080684\", \"genre\": \"Action|Adventure|Drama|Sci-Fi|War\", \"title\": \"Star Wars: Episode V - The Empire Strikes Back (1980)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0082971\", \"genre\": \"Action|Adventure\", \"title\": \"Raiders of the Lost Ark (1981)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0133093\", \"genre\": \"Action|Sci-Fi|Thriller\", \"title\": \"Matrix, The (1999)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0093779\", \"genre\": \"Action|Adventure|Comedy|Romance\", \"title\": \"Princess Bride, The (1987)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0088247\", \"genre\": \"Action|Sci-Fi|Thriller\", \"title\": \"Terminator, The (1984)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0112573\", \"genre\": \"Action|Drama|War\", \"title\": \"Braveheart (1995)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0090605\", \"genre\": \"Action|Sci-Fi|Thriller|War\", \"title\": \"Aliens (1986)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0078748\", \"genre\": \"Action|Horror|Sci-Fi|Thriller\", \"title\": \"Alien (1979)\" } ] ");
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
    public void testNullOccup() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "25");
            json.put("genre", "null");
            String actual = getResult("users", json.toString());
            String expected = "Occupation key is not given\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testNullGenre() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("age", "25");
            json.put("occupation", "");
            String actual = getResult("users", json.toString());
            String expected = "Genre key is not given\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testNullAge() {
        try {
            JSONObject json = new JSONObject();
            json.put("gender", "F");
            json.put("occupation", "");
            json.put("genre", "null");
            String actual = getResult("users", json.toString());
            String expected = "Age key is not given\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testNullGender() {
        try {
            JSONObject json = new JSONObject();
            json.put("age", "25");
            json.put("occupation", "");
            json.put("genre", "null");
            String actual = getResult("users", json.toString());
            String expected = "Gender key is not given\n";
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
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0120363\",\"genre\": \"Animation|Children's|Comedy\",\"title\": \"Toy Story 2 (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0138097\",\"genre\": \"Comedy|Romance\",\"title\": \"Shakespeare in Love (1998)\" },{\"imdb\": \"http://www.imdb.com/title/tt0114709\",\"genre\": \"Animation|Children's|Comedy\",\"title\": \"Toy Story (1995)\" },{\"imdb\": \"http://www.imdb.com/title/tt0167404\",\"genre\": \"Thriller\",\"title\": \"Sixth Sense, The (1999)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0032138\",\"genre\": \"Adventure|Children's|Drama|Musical\",\"title\": \"Wizard of Oz, The (1939)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0109830\",\"genre\": \"Comedy|Romance|War\",\"title\": \"Forrest Gump (1994)\" },{\"imdb\": \"http://www.imdb.com/title/tt0169547\",\"genre\": \"Comedy|Drama\",\"title\": \"American Beauty (1999)\" },{\"imdb\": \"http://www.imdb.com/title/tt0120623\",\"genre\": \"Animation|Children's|Comedy\",\"title\": \"Bug's Life, A (1998)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0058331\",\"genre\": \"Children's|Comedy|Musical\",\"title\": \"Mary Poppins (1964)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0076759\",\"genre\": \"Action|Adventure|Fantasy|Sci-Fi\",\"title\": \"Star Wars: Episode IV - A New Hope (1977)\" }]";
            jsonArr = new JSONArray(data);
            actual = getResult("users", json.toString());
            expected = jsonArr.toString(2);
            assertEquals(expected, actual);
            json.put("age", "18");
            data = "[{\"imdb\": \"http://www.imdb.com/title/tt0111161\",\"genre\": \"Drama\",\"title\": \"Shawshank Redemption, The (1994)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0169547\",\"genre\": \"Comedy|Drama\",\"title\": \"American Beauty (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0114814\",\"genre\": \"Crime|Thriller\",\"title\": \"Usual Suspects, The (1995)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0093779\",\"genre\": \"Action|Adventure|Comedy|Romance\",\"title\": \"Princess Bride, The (1987)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0118799\",\"genre\": \"Comedy|Drama\",\"title\": \"Life Is Beautiful (La Vita � bella) (1997)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108052\",\"genre\": \"Drama|War\",\"title\": \"Schindler's List (1993)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0102926\",\"genre\": \"Drama|Thriller\",\"title\": \"Silence of the Lambs, The (1991)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0120601\",\"genre\": \"Comedy\",\"title\": \"Being John Malkovich (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0167404\",\"genre\": \"Thriller\",\"title\": \"Sixth Sense, The (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108598\",\"genre\": \"Animation|Comedy\",\"title\": \"Wrong Trousers, The (1993)\"} ]";
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
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0111161\",\"genre\": \"Drama\",\"title\": \"Shawshank Redemption, The (1994)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0082971\",\"genre\": \"Action|Adventure\",\"title\": \"Raiders of the Lost Ark (1981)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0076759\",\"genre\": \"Action|Adventure|Fantasy|Sci-Fi\",\"title\": \"Star Wars: Episode IV - A New Hope (1977)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0068646\",\"genre\": \"Action|Crime|Drama\",\"title\": \"Godfather, The (1972)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0114814\",\"genre\": \"Crime|Thriller\",\"title\": \"Usual Suspects, The (1995)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108052\",\"genre\": \"Drama|War\",\"title\": \"Schindler's List (1993)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0047396\",\"genre\": \"Mystery|Thriller\",\"title\": \"Rear Window (1954)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0112691\",\"genre\": \"Animation|Comedy|Thriller\",\"title\": \"Close Shave, A (1995)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0167404\",\"genre\": \"Thriller\",\"title\": \"Sixth Sense, The (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108598\",\"genre\": \"Animation|Comedy\",\"title\": \"Wrong Trousers, The (1993)\"}]";
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
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0087332\",\"genre\": \"Comedy|Horror\",\"title\": \"Ghostbusters (1984)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0073195\",\"genre\": \"Action|Horror\",\"title\": \"Jaws (1975)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0081505\",\"genre\": \"Horror\",\"title\": \"Shining, The (1980)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0078748\",\"genre\": \"Action|Horror|Sci-Fi|Thriller\",\"title\": \"Alien (1979)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0054215\",\"genre\": \"Horror|Thriller\",\"title\": \"Psycho (1960)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0162661\",\"genre\": \"Horror|Romance\",\"title\": \"Sleepy Hollow (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0072431\",\"genre\": \"Comedy|Horror\",\"title\": \"Young Frankenstein (1974)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0120616\",\"genre\": \"Action|Adventure|Horror|Thriller\",\"title\": \"Mummy, The (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0100157\",\"genre\": \"Horror\",\"title\": \"Misery (1990)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0063522\",\"genre\": \"Horror|Thriller\",\"title\": \"Rosemary's Baby (1968)\"} ]";
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
            String expected = "Please, pass exactly 4 keys to JSON!\n" +
                    "Try to remove spaces between occupations consisting of several words, such as \"college student\" -> \"collegestudent\"\n";
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
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0112691\", \"genre\": \"Animation|Comedy|Thriller\", \"title\": \"Close Shave, A (1995)\" }]";
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
            String expected = "Title is not given\n";
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLimitNull() {
        try {
            JSONObject json = new JSONObject();
            json.put("title", "kl");
            String actual = getResult("movies", json.toString());
            String expected = "Movie doesn't exist in the movies.dat\n";
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
            JSONArray jsonArray = new JSONArray("[ { \"imdb\": \"http://www.imdb.com/title/tt0118147\", \"genre\": \"Documentary\", \"title\": \"When We Were Kings (1996)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0110057\", \"genre\": \"Documentary\", \"title\": \"Hoop Dreams (1994)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0096257\", \"genre\": \"Documentary\", \"title\": \"Thin Blue Line, The (1988)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0088178\", \"genre\": \"Documentary\", \"title\": \"Stop Making Sense (1984)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0098213\", \"genre\": \"Comedy|Documentary\", \"title\": \"Roger & Me (1989)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0109508\", \"genre\": \"Documentary\", \"title\": \"Crumb (1994)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0117293\", \"genre\": \"Documentary\", \"title\": \"Paradise Lost: The Child Murders at Robin Hood Hills (1996)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0108515\", \"genre\": \"Documentary\", \"title\": \"War Room, The (1993)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0186508\", \"genre\": \"Documentary\", \"title\": \"Buena Vista Social Club (1999)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0181288\", \"genre\": \"Documentary\", \"title\": \"American Movie (1999)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0164312\", \"genre\": \"Documentary\", \"title\": \"42 Up (1998)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0112651\", \"genre\": \"Documentary\", \"title\": \"Celluloid Closet, The (1995)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0120661\", \"genre\": \"Documentary\", \"title\": \"Everest (1998)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0085809\", \"genre\": \"Documentary|War\", \"title\": \"Koyaanisqatsi (1983)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0116481\", \"genre\": \"Documentary\", \"title\": \"Hands on a Hard Body (1996)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0117040\", \"genre\": \"Documentary\", \"title\": \"Microcosmos (Microcosmos: Le peuple de l'herbe) (1996)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0120370\", \"genre\": \"Documentary\", \"title\": \"Trekkies (1997)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0082252\", \"genre\": \"Documentary\", \"title\": \"Decline of Western Civilization, The (1981)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0100332\", \"genre\": \"Documentary\", \"title\": \"Paris Is Burning (1990)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0107472\", \"genre\": \"Documentary\", \"title\": \"Wonderful, Horrible Life of Leni Riefenstahl, The (Die Macht der Bilder) (1993)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0192335\", \"genre\": \"Documentary\", \"title\": \"Mr. Death: The Rise and Fall of Fred A. Leuchter Jr. (1999)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0119107\", \"genre\": \"Documentary\", \"title\": \"Fast, Cheap & Out of Control (1997)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0124295\", \"genre\": \"Comedy|Documentary\", \"title\": \"Big One, The (1997)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0108328\", \"genre\": \"Documentary\", \"title\": \"Thirty-Two Short Films About Glenn Gould (1993)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0116913\", \"genre\": \"Documentary|Drama\", \"title\": \"Looking for Richard (1996)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0060371\", \"genre\": \"Documentary\", \"title\": \"Endless Summer, The (1966)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0110480\", \"genre\": \"Documentary\", \"title\": \"Maya Lin: A Strong Clear Vision (1994)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0233687\", \"genre\": \"Documentary\", \"title\": \"Eyes of Tammy Faye, The (2000)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0144801\", \"genre\": \"Documentary\", \"title\": \"Brandon Teena Story, The (1998)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0109934\", \"genre\": \"Documentary\", \"title\": \"Great Day in Harlem, A (1994)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0112373\", \"genre\": \"Documentary\", \"title\": \"Anne Frank Remembered (1995)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0218043\", \"genre\": \"Documentary\", \"title\": \"Beyond the Mat (2000)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0114805\", \"genre\": \"Documentary\", \"title\": \"Unzipped (1995)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0097372\", \"genre\": \"Documentary\", \"title\": \"For All Mankind (1989)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0094980\", \"genre\": \"Documentary\", \"title\": \"Decline of Western Civilization Part II: The Metal Years, The (1988)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0103767\", \"genre\": \"Documentary\", \"title\": \"Baraka (1992)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0096328\", \"genre\": \"Documentary|Musical\", \"title\": \"U2: Rattle and Hum (1988)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0208261\", \"genre\": \"Documentary\", \"title\": \"Life and Times of Hank Greenberg, The (1998)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0150230\", \"genre\": \"Documentary\", \"title\": \"Cruise, The (1998)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0236216\", \"genre\": \"Documentary\", \"title\": \"Filth and the Fury, The (2000)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0200849\", \"genre\": \"Documentary\", \"title\": \"My Best Fiend (Mein liebster Feind) (1999)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0236388\", \"genre\": \"Comedy|Documentary\", \"title\": \"Original Kings of Comedy, The (2000)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0141986\", \"genre\": \"Documentary\", \"title\": \"Wild Man Blues (1998)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0111161\", \"genre\": \"Drama\", \"title\": \"Shawshank Redemption, The (1994)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0068646\", \"genre\": \"Action|Crime|Drama\", \"title\": \"Godfather, The (1972)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0114814\", \"genre\": \"Crime|Thriller\", \"title\": \"Usual Suspects, The (1995)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0108052\", \"genre\": \"Drama|War\", \"title\": \"Schindler's List (1993)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0047478\", \"genre\": \"Action|Drama\", \"title\": \"Seven Samurai (The Magnificent Seven) (Shichinin no samurai) (1954)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0082971\", \"genre\": \"Action|Adventure\", \"title\": \"Raiders of the Lost Ark (1981)\" }, { \"imdb\": \"http://www.imdb.com/title/tt0108598\", \"genre\": \"Animation|Comedy\", \"title\": \"Wrong Trousers, The (1993)\" } ]");
            String expected = jsonArray.toString(2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }
}
