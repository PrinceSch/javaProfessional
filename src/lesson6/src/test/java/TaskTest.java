import org.junit.*;

public class TaskTest {

    MainApp task;

    @Before
    public void init(){
        task = new MainApp();
    }

    @Test
    public void test11 (){Assert.assertArrayEquals(new int[]{7, 8, 1}, task.task1(new int[]{2, 3, 4, 1, 2, 4, 7, 8, 1}));}

    @Test
    public void test12 (){Assert.assertArrayEquals(new int[]{1, 2, 7, 8, 1}, task.task1(new int[]{2, 3, 4, 1, 2, 7, 8, 1}));}

    @Test (expected = RuntimeException.class)
    public void test13 (){task.task1(new int[]{2, 3, 1, 2, 7, 8, 1});}

    @Test
    public void test14 (){Assert.assertArrayEquals(new int[]{}, task.task1(new int[]{2, 3, 1, 2, 7, 8, 1,4}));}

    @Test
    public void test21 () {Assert.assertEquals(true, task.task2(new int[] {1,4,1,4,1,1,4}));}
    @Test
    public void test22 () {Assert.assertEquals(false, task.task2(new int[] {1,1,1,1}));}
    @Test
    public void test23 () {Assert.assertEquals(false, task.task2(new int[] {4,4,4,4}));}
    @Test
    public void test24 () {Assert.assertEquals(false, task.task2(new int[] {1,4,2,1,4}));}

}
