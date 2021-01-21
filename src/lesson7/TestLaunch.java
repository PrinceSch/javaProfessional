package lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestLaunch {

    public static void start(Class testClass) {
        int countBS = 0;
        int countAS = 0;

        List<Method> methodList = new ArrayList<>();
        Method[] classMethods = testClass.getDeclaredMethods();

        for (Method m : classMethods) {
            if (m.isAnnotationPresent(Test.class)) methodList.add(m);
        }

        methodList.sort(Comparator.comparingInt((Method i) -> i.getAnnotation(Test.class).priority()).reversed());

        for (Method m : classMethods) {
            if (countBS > 1) throw new RuntimeException("больше одного BeforeSuite");
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                countBS++;
                methodList.add(0, m);
            }
        }

        for (Method m : classMethods) {
            if (countAS > 1) throw new RuntimeException("больше одного AfterSuite");
            if (m.isAnnotationPresent(AfterSuite.class)) {
                countAS++;
                methodList.add(m);
            }
        }

        for (Method m : methodList) {
            try {
                m.invoke(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
