import org.junit.Test;
import org.junit.Assert;
public class MainTest {
    @Test
    public void testMoreArgs(){
        try{
            Main.main(new String[]{"null","null","null","null","null"});
        }catch (Exception e){

        }
    }

    @Test
    public void testNullNull(){
        try{
            Main.main(new String[]{null,null,null});
        }catch (Exception e){

        }
    }
    @Test
    public void testNullStringNull(){
        try{
            Main.main(new String[]{null,"Adventure",null});
        }
        catch (Exception e){
        }
    }
    @Test
    public void testStringNullNull(){
        try{
            Main.main(new String[]{"Adventure",null,null});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyEmptyEmpty(){
        try{
            Main.main(new String[]{"","",""});
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
            Main.main(new String[]{"F","25","gradstudent",""});
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
}
