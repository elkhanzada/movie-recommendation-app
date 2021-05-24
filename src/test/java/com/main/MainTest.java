package com.main;

import com.main.Main;
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
    @Test
    public void testEmptyEmptyEmpty(){
        try{
            Main.getMovies(new String[]{"","",""});
        }catch (Exception e){

        }
    }@Test
    public void testEmptyEmptyEmptyEmpty(){
        try{
            Main.getMovies(new String[]{"","","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyEmptyEmptyString(){
        try{
            Main.getMovies(new String[]{"","","","Adventure"});
        }catch (Exception e){
        }
    }
    @Test
    public void testStringEmptyEmpty(){
        try{
            Main.getMovies(new String[]{"F","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testSplitSplitSplit(){
        try{
            Main.getMovies(new String[]{"|","|","|"});
        }catch (Exception e){

        }
    }
    @Test
    public void testSplitStringString(){
        try{
            Main.getMovies(new String[]{"|","25","gradstudent"});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyGenre(){
        try{
            Main.getMovies(new String[]{"M","25","gradstudent",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongGenreList(){
        try{
            Main.getMovies(new String[]{"F","25","gradstudent","a|b|c|d|e|f|g|h|i|j|k|l|m|n|o"});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongGenre(){
        try{
            Main.getMovies(new String[]{"F","25","gradstudent","asdfasdfafafadasfsdasdsdfddfhffuhsdgfidfhiuhfiufhfiusahiasuhdaisuhdaihasduadiauhdasi"});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmpyFullGenre(){
        try{
            Main.getMovies(new String[]{"M","25","gradstudent","|Horror|"});
        }catch (Exception e){

        }
    }
    @Test
    public void testFullGenreEmpty(){
        try{
            Main.getMovies(new String[]{"M","25","gradstudent","Horror||"});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyOccup(){
        try{
            Main.getMovies(new String[]{"F","25","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongOccup(){
        try{
            Main.getMovies(new String[]{"F","25","sasklasjfasdsadasfsafsafasfsafsafsafsfasfaskjflfkjasfklsfjaslkfjasflkjaasd"});
        }catch (Exception e){

        }
    }
    @Test
    public void testUnknownOccup(){
        try{
            Main.getMovies(new String[]{"F","25","aibar"});
        }catch (Exception e){

        }
    }
    @Test
    public void testNullOccup(){
        try{
            Main.getMovies(new String[]{"F","25","sasklasjfaskjflfkjasfklsfjaslkfjasflkjaasd",null});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyAge(){
        try{
            Main.getMovies(new String[]{"M","","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongAge(){
        try{
            Main.getMovies(new String[]{"F","2523212311234112312123121241231241231124",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testStringAge(){
        try{
            Main.getMovies(new String[]{"F","age",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testPossibleAges(){
        try{
            Main.getMovies(new String[]{"F","1",""});
            Main.getMovies(new String[]{"F","18",""});
            Main.getMovies(new String[]{"F","25",""});
            Main.getMovies(new String[]{"F","35",""});
            Main.getMovies(new String[]{"F","45",""});
            Main.getMovies(new String[]{"F","55",""});
            Main.getMovies(new String[]{"F","56",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyGender(){
        try{
            Main.getMovies(new String[]{"","25",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongGender(){
        try{
            Main.getMovies(new String[]{"akfjaskfljflsakfjasflkaj","25",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLessMovie(){
        try{
            Main.getMovies(new String[]{"M","1","Doctor", "Horror"});
        }catch (Exception e){

        }
    }
    @Test
    public void testGenresSizeZero(){
        try{
            Main.getMovies(new String[]{"M","1","Doctor", "|"});
        }catch (Exception e){

        }
    }
    @Test
    public void testTwoArgs(){
        try{
            Main.getMovies(new String[]{"",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testNonexistingMovie(){
        try{
            Main.getMovies(new String[]{"M","1","Doctor", "lolita"});
        }catch (Exception e){

        }
    }
    @Test
    public void testMoreGenres(){
        try{
            Main.getMovies(new String[]{"M","1","Doctor", "Horror"});
        }catch (Exception e){

        }
    }
    public void argsThree(){
        try{
            Main.getMovies(new String[]{"M", "25", "Doctor"});
        }catch (Exception e){

        }
    }
    @Test
    public void emptyGenre(){
        try{
            Main.getMovies(new String[]{"M", "25", "Doctor", "||Adventure| ||" });
        }catch (Exception e){

        }
    }
    @Test
    public void manyGenres() {
        try {
            Main.getMovies(new String[]{"M", "25", "Doctor", "Adventure|Romance|Action|Comedy|Horror|Fantasy|Musical"});
        } catch (Exception e) {

        }
    }
    @Test
    public void sameGenres() {
        try {
            Main.getMovies(new String[]{"M", "25", "Doctor", "Adventure|Adventure"});
        } catch (Exception e) {

        }
    }
}
