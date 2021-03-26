import org.junit.Test;
import org.junit.Assert;
public class MainTest {
    @Test
    public void testNullNull(){
        try{
            Main.main(new String[]{null,null});
        }catch (Exception e){

        }
    }
    @Test
    public void testNullString(){
        try{
            Main.main(new String[]{null,"Adventure"});
        }
        catch (Exception e){
        }
    }
    @Test
    public void testStringNull(){
        try{
            Main.main(new String[]{"Adventure",null});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyEmpty(){
        try{
            Main.main(new String[]{"",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testEmptyString(){
        try{
            Main.main(new String[]{"","educator"});
        }catch (Exception e){

        }
    }
    @Test
    public void testStringEmpty(){
        try{
            Main.main(new String[]{"Adventure",""});
        }catch (Exception e){

        }
    }
    @Test
    public void testSplitSplit(){
        try{
            Main.main(new String[]{"|","|"});
        }catch (Exception e){

        }
    }
    @Test
    public void testSplitString(){
        try{
            Main.main(new String[]{"|","educator"});
        }catch (Exception e){

        }
    }
}
