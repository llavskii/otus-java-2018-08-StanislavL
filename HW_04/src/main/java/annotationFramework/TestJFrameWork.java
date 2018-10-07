package annotationFramework;

import annotationFramework.annotations.After;
import annotationFramework.annotations.Before;
import annotationFramework.annotations.Ignore;
import annotationFramework.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestJFrameWork {
    public static void test(Class<?> clazz) throws Exception {
        List<Method> methods = new ArrayList<>(Arrays.asList(clazz.getMethods()));
        methods.sort(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        int pass = 0;
        int fail = 0;
        int ignore = 0;
        Method methodBefore = null;
        Method methodAfter = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) methodBefore = method;
            else if (method.isAnnotationPresent(After.class)) methodAfter = method;
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(Ignore.class)) {
                ignore++;
                continue;
            }
            if (method.isAnnotationPresent(Test.class)) {
                Object obj = clazz.getDeclaredConstructor().newInstance();
                try {
                    if (methodBefore != null) methodBefore.invoke(obj);
                    method.invoke(obj);
                    System.out.println("Annotated \"@Test\" method " + method.getName() + " completed its work...");
                    if (methodAfter != null) methodAfter.invoke(obj);
                    pass++;
                } catch (Exception e) {
                    System.err.println(String.format("Exception would be thrown during annotated \"@Test\" method %s worked: %s",
                            method.getName(), e.toString()));
                    fail++;
                }
            }
        }
        String result = (fail == 0) ? "Passed" : "Failed";
        System.out.println(String.format("Tests %s: %s passed, %s failed, %s ignored", result, pass, fail, ignore));

    }
}
