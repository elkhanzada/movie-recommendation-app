import org.junit.Test;
import org.junit.Assert;
public class MainTest {
    @Test
    public void testMainNullNull(){
        try{
            Main.main(new String[]{null,null});
            Assert.fail();
        }catch (Exception e){

        }
    }
    @Test
    public void testMainNullString(){
        try{
            Main.main(new String[]{null,"Adventure"});
            Assert.fail();
        }catch (Exception e){

        }
    }
    @Test
    public void testMainStringNull(){
        try{
            Main.main(new String[]{"Adventure",null});
            Assert.fail();
        }catch (Exception e){

        }
    }
}
