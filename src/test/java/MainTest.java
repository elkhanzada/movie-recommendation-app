import org.junit.Test;
import org.junit.Assert;
public class MainTest {
    @Test
    public void testMoreArgs(){
        try{
            Main.main(new String[]{"1","2","3","4","5"});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyEmptyEmpty(){
        try{
            Main.main(new String[]{"","",""});
        }catch (Exception e){

        }
    }@Test
    public void testEmptyEmptyEmptyEmpty(){
        try{
            Main.main(new String[]{"","","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyEmptyEmptyString(){
        try{
            Main.main(new String[]{"","","","Adventure"});
        }catch (Exception e){
        }
    }
    @Test
    public void testStringEmptyEmpty(){
        try{
            Main.main(new String[]{"F","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testSplitSplitSplit(){
        try{
            Main.main(new String[]{"|","|","|"});
        }catch (Exception e){

        }
    }
    @Test
    public void testSplitStringString(){
        try{
            Main.main(new String[]{"|","25","gradstudent"});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyGenre(){
        try{
            Main.main(new String[]{"M","25","gradstudent",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongGenreList(){
        try{
            Main.main(new String[]{"F","25","gradstudent","a|b|c|d|e|f|g|h|i|j|k|l|m|n|o"});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongGenre(){
        try{
            Main.main(new String[]{"F","25","gradstudent","asdfasdfafafadasfsdasdsdfddfhffuhsdgfidfhiuhfiufhfiusahiasuhdaisuhdaihasduadiauhdasi"});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmpyFullGenre(){
        try{
            Main.main(new String[]{"M","25","gradstudent","|Horror|"});
        }catch (Exception e){

        }
    }
    @Test
    public void testFullGenreEmpty(){
        try{
            Main.main(new String[]{"M","25","gradstudent","Horror||"});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyOccup(){
        try{
            Main.main(new String[]{"F","25","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongOccup(){
        try{
            Main.main(new String[]{"F","25","sasklasjfasdsadasfsafsafasfsafsafsafsfasfaskjflfkjasfklsfjaslkfjasflkjaasd"});
        }catch (Exception e){

        }
    }
    @Test
    public void testUnknownOccup(){
        try{
            Main.main(new String[]{"F","25","aibar"});
        }catch (Exception e){

        }
    }
    @Test
    public void testNullOccup(){
        try{
            Main.main(new String[]{"F","25","sasklasjfaskjflfkjasfklsfjaslkfjasflkjaasd",null});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyAge(){
        try{
            Main.main(new String[]{"M","","",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongAge(){
        try{
            Main.main(new String[]{"F","2523212311234112312123121241231241231124",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testStringAge(){
        try{
            Main.main(new String[]{"F","age",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testPossibleAges(){
        try{
            Main.main(new String[]{"F","1",""});
            Main.main(new String[]{"F","18",""});
            Main.main(new String[]{"F","25",""});
            Main.main(new String[]{"F","35",""});
            Main.main(new String[]{"F","45",""});
            Main.main(new String[]{"F","55",""});
            Main.main(new String[]{"F","56",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyGender(){
        try{
            Main.main(new String[]{"","25",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLongGender(){
        try{
            Main.main(new String[]{"akfjaskfljflsakfjasflkaj","25",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testLessMovie(){
        try{
            Main.main(new String[]{"M","1","Doctor", "Horror"});
        }catch (Exception e){

        }
    }
    @Test
    public void testGenresSizeZero(){
        try{
            Main.main(new String[]{"M","1","Doctor", "|"});
        }catch (Exception e){

        }
    }
    @Test
    public void testTwoArgs(){
        try{
            Main.main(new String[]{"",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testNonexistingMovie(){
        try{
            Main.main(new String[]{"M","1","Doctor", "lolita"});
        }catch (Exception e){

        }
    }
    @Test
    public void testMoreGenres(){
        try{
            Main.main(new String[]{"M","1","Doctor", "Horror"});
        }catch (Exception e){

        }
    }
}
