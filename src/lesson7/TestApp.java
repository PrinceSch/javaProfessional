package lesson7;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestApp {
    @BeforeSuite
    public static void testBefore(){ System.out.println("before suite"); }

    @Test
    public static void test1(){ System.out.println("test 1 default"); }
    @Test
    public static void test2(){ System.out.println("test 2 default"); }
    @Test (priority = 3)
    public static void test3(){ System.out.println("test 3 priority 3"); }
    @Test (priority = 9)
    public static void test4(){ System.out.println("test 4 priority 9"); }

    @AfterSuite
    public static void testAfter(){ System.out.println("after suite"); }

}
