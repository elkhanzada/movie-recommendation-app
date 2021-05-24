package com.main;

import com.main.Main;
import com.main.MainController;

import static org.junit.Assert.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void testMoreArgs(){
        try{

            this.mvc.perform(get("/movies/recommendations")
            .accept("{title: Toy Story (1994)}"))
            .andDo(print());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public JSONObject tojsonobj(String imdb, String genre, String title){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("genre", genre);
            jsonObject.put("imdb", imdb);
            return jsonObject;
        }catch(Exception e){
            return null;
        }
    }
    @Test
    public void testFourArgs(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "M");
            hash.put("age", "25");
            hash.put("occupation", "gradstudent");
            hash.put("genre", "action");
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0076759",
                    "Action|Adventure|Fantasy|Sci-Fi",
                    "Star Wars: Episode IV - A New Hope (1977)" ));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0068646",
                    "Action|Crime|Drama",
                    "Godfather, The (1972)" ));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0080684",
                    "Action|Adventure|Drama|Sci-Fi|War",
                    "Star Wars: Episode V - The Empire Strikes Back (1980)"));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0082971",
                    "Action|Adventure",
                    "Raiders of the Lost Ark (1981)"));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0133093",
                    "Action|Sci-Fi|Thriller",
                    "Matrix, The (1999)"));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0093779",
                    "Action|Adventure|Comedy|Romance",
                    "Princess Bride, The (1987)"));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0088247",
                    "Action|Sci-Fi|Thriller",
                    "Terminator, The (1984)"));
            jsonArray.put(tojsonobj( "http://www.imdb.com/title/tt0112573",
                    "Action|Drama|War",
                    "Braveheart (1995)"));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0090605",
                    "Action|Sci-Fi|Thriller|War",
                    "Aliens (1986)"));
            jsonArray.put(tojsonobj("http://www.imdb.com/title/tt0078748",
                    "Action|Horror|Sci-Fi|Thriller",
                    "Alien (1979)"));

            assertEquals(Main.getMovies(hash), jsonArray.toString(2));
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    @Test
    public void testLongOccup(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "");
            hash.put("occupation", "sasklasjfasdsadasfsafsafasfsafsafsafsfasfaskjflfkjasfklsfjaslkfjasflkjaasd");
            hash.put("genre", "");
            assertEquals(Main.getMovies(hash), "There is no such registered occupation as sasklasjfasdsadasfsafsafasfsafsafsafsfasfaskjflfkjasfklsfjaslkfjasflkjaasd!\nIf you want to see some other ratings, please use \"other\" as an argument\n");
        }catch (Exception e){

        }
    }

    @Test
    public void testNullOccup(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "25");
            hash.put("occupation", null);
            hash.put("genre", "null");
            assertEquals(Main.getMovies(hash), "Occupation key is not given\n");
        }catch (Exception e){

        }
    }
    @Test
    public void testNullGenre(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "25");
            hash.put("occupation", "");
            hash.put("genre", null);
            assertEquals(Main.getMovies(hash), "Genre key is not given\n");
        }catch (Exception e){

        }
    }
    @Test
    public void testNullAge(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", null);
            hash.put("occupation", "");
            hash.put("genre", "null");
            assertEquals(Main.getMovies(hash), "Age key is not given\n");
        }catch (Exception e){

        }
    }
    @Test
    public void testNullGender(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", null);
            hash.put("age", "25");
            hash.put("occupation", "");
            hash.put("genre", "null");
            assertEquals("Gender key is not given\n", Main.getMovies(hash));
        }catch (Exception e){

        }
    }

    @Test
    public void testStringAge(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "age");
            hash.put("occupation", "");
            hash.put("genre", "");
            assertEquals(Main.getMovies(hash), "Please, enter a valid argument for age!\nIt should be a positive integer, containing only digits!\nAge shall not exceed " + Integer.MAX_VALUE + "\n");
        }catch (Exception e){

        }
    }
    @Test
    public void testPossibleAges(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "1");
            hash.put("occupation", "");
            hash.put("genre", "");
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0120363\",\"genre\": \"Animation|Children's|Comedy\",\"title\": \"Toy Story 2 (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0138097\",\"genre\": \"Comedy|Romance\",\"title\": \"Shakespeare in Love (1998)\" },{\"imdb\": \"http://www.imdb.com/title/tt0114709\",\"genre\": \"Animation|Children's|Comedy\",\"title\": \"Toy Story (1995)\" },{\"imdb\": \"http://www.imdb.com/title/tt0167404\",\"genre\": \"Thriller\",\"title\": \"Sixth Sense, The (1999)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0032138\",\"genre\": \"Adventure|Children's|Drama|Musical\",\"title\": \"Wizard of Oz, The (1939)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0109830\",\"genre\": \"Comedy|Romance|War\",\"title\": \"Forrest Gump (1994)\" },{\"imdb\": \"http://www.imdb.com/title/tt0169547\",\"genre\": \"Comedy|Drama\",\"title\": \"American Beauty (1999)\" },{\"imdb\": \"http://www.imdb.com/title/tt0120623\",\"genre\": \"Animation|Children's|Comedy\",\"title\": \"Bug's Life, A (1998)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0058331\",\"genre\": \"Children's|Comedy|Musical\",\"title\": \"Mary Poppins (1964)\" }, {\"imdb\": \"http://www.imdb.com/title/tt0076759\",\"genre\": \"Action|Adventure|Fantasy|Sci-Fi\",\"title\": \"Star Wars: Episode IV - A New Hope (1977)\" }]";
            JSONArray jsonArr = new JSONArray(data);
            assertEquals(Main.getMovies(hash), jsonArr.toString(2));
            hash.replace("age", "18");
            data = "[{\"imdb\": \"http://www.imdb.com/title/tt0111161\",\"genre\": \"Drama\",\"title\": \"Shawshank Redemption, The (1994)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0169547\",\"genre\": \"Comedy|Drama\",\"title\": \"American Beauty (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0114814\",\"genre\": \"Crime|Thriller\",\"title\": \"Usual Suspects, The (1995)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0093779\",\"genre\": \"Action|Adventure|Comedy|Romance\",\"title\": \"Princess Bride, The (1987)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0118799\",\"genre\": \"Comedy|Drama\",\"title\": \"Life Is Beautiful (La Vita � bella) (1997)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108052\",\"genre\": \"Drama|War\",\"title\": \"Schindler's List (1993)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0102926\",\"genre\": \"Drama|Thriller\",\"title\": \"Silence of the Lambs, The (1991)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0120601\",\"genre\": \"Comedy\",\"title\": \"Being John Malkovich (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0167404\",\"genre\": \"Thriller\",\"title\": \"Sixth Sense, The (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108598\",\"genre\": \"Animation|Comedy\",\"title\": \"Wrong Trousers, The (1993)\"} ]";
            JSONArray jsonArr2 = new JSONArray(data);
            assertEquals(Main.getMovies(hash), jsonArr2.toString(2));
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyGender(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "");
            hash.put("age", "25");
            hash.put("occupation", "");
            hash.put("genre", "");
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0111161\",\"genre\": \"Drama\",\"title\": \"Shawshank Redemption, The (1994)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0082971\",\"genre\": \"Action|Adventure\",\"title\": \"Raiders of the Lost Ark (1981)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0076759\",\"genre\": \"Action|Adventure|Fantasy|Sci-Fi\",\"title\": \"Star Wars: Episode IV - A New Hope (1977)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0068646\",\"genre\": \"Action|Crime|Drama\",\"title\": \"Godfather, The (1972)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0114814\",\"genre\": \"Crime|Thriller\",\"title\": \"Usual Suspects, The (1995)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108052\",\"genre\": \"Drama|War\",\"title\": \"Schindler's List (1993)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0047396\",\"genre\": \"Mystery|Thriller\",\"title\": \"Rear Window (1954)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0112691\",\"genre\": \"Animation|Comedy|Thriller\",\"title\": \"Close Shave, A (1995)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0167404\",\"genre\": \"Thriller\",\"title\": \"Sixth Sense, The (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0108598\",\"genre\": \"Animation|Comedy\",\"title\": \"Wrong Trousers, The (1993)\"}]";
            JSONArray jsonArray = new JSONArray(data);
            assertEquals(jsonArray.toString(2), Main.getMovies(hash));
        }catch (Exception e){

        }
    }
    @Test
    public void testLongGender(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "akfjaskfljflsakfjasflkaj");
            hash.put("age", "");
            hash.put("occupation", "");
            hash.put("genre", "");
            assertEquals("Please, provide a proper arguement for Gender\nIt shall be empty - \"\", male - \"M\" or \"m\", female \"F\" or \"f\"\n", Main.getMovies(hash));
        }catch (Exception e){

        }
    }
    @Test
    public void testLessMovie(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "1");
            hash.put("occupation", "Doctor");
            hash.put("genre", "Horror");
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0087332\",\"genre\": \"Comedy|Horror\",\"title\": \"Ghostbusters (1984)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0073195\",\"genre\": \"Action|Horror\",\"title\": \"Jaws (1975)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0081505\",\"genre\": \"Horror\",\"title\": \"Shining, The (1980)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0078748\",\"genre\": \"Action|Horror|Sci-Fi|Thriller\",\"title\": \"Alien (1979)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0054215\",\"genre\": \"Horror|Thriller\",\"title\": \"Psycho (1960)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0162661\",\"genre\": \"Horror|Romance\",\"title\": \"Sleepy Hollow (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0072431\",\"genre\": \"Comedy|Horror\",\"title\": \"Young Frankenstein (1974)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0120616\",\"genre\": \"Action|Adventure|Horror|Thriller\",\"title\": \"Mummy, The (1999)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0100157\",\"genre\": \"Horror\",\"title\": \"Misery (1990)\"}, {\"imdb\": \"http://www.imdb.com/title/tt0063522\",\"genre\": \"Horror|Thriller\",\"title\": \"Rosemary's Baby (1968)\"} ]";
            JSONArray jsonArray = new JSONArray(data);
            assertEquals(jsonArray.toString(2), Main.getMovies(hash));
        }catch (Exception e){

        }
    }
    @Test
    public void emptyGenre(){
        try{
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "");
            hash.put("occupation", "Doctor");
            hash.put("genre", "||Adventure| ||");
            assertEquals("There is not such registered genre as \n", Main.getMovies(hash));
        }catch (Exception e){

        }
    }
    @Test
    public void sameGenres() {
        try {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "25");
            hash.put("occupation", "Doctor");
            hash.put("genre", "Adventure|Adventure");
            assertEquals("Please enter valid input for genres\nGenres should not repeat\n", Main.getMovies(hash));
        } catch (Exception e) {

        }
    }
    @Test
    public void moreArgs() {
        try {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("gender", "F");
            hash.put("age", "25");
            hash.put("occupation", "Doctor");
            hash.put("genre", "Adventure|Adventure");
            hash.put("excessarg", "0");
            assertEquals("Please, pass exactly 4 keys to JSON!\n" +
                    "Try to remove spaces between occupations consisting of several words, such as \"college student\" -> \"collegestudent\"\n", Main.getMovies(hash));

        } catch (Exception e) {

        }
    }
    @Test
    public void testrecommend() {
        try {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("title", "Wrong Trousers, The (1993)");
            hash.put("limit", "1");
            String data = "[{\"imdb\": \"http://www.imdb.com/title/tt0112691\", \"genre\": \"Animation|Comedy|Thriller\", \"title\": \"Close Shave, A (1995)\" }]";
            JSONArray jsonArray = new JSONArray(data);
            assertEquals(Main.recommendMovies(hash), jsonArray.toString(2));
        } catch (Exception e) {

        }
    }
    @Test
    public void titleNull() {
        try {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("title", null);
            hash.put("limit", "20");
            assertEquals(Main.recommendMovies(hash), "Title is not given\n");
        } catch (Exception e) {

        }
    }
    @Test
    public void limitnull() {
        try {
            HashMap<String, String> hash = new HashMap<String, String>();
            hash.put("title", "kl");
            hash.put("limit", null);
            assertEquals("Movie doesn't exist in the movies.dat", Main.recommendMovies(hash));
        } catch (Exception e) {

        }
    }
}
