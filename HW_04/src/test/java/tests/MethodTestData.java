package tests;

import annotationFramework.annotations.After;
import annotationFramework.annotations.Before;
import annotationFramework.annotations.Ignore;
import annotationFramework.annotations.Test;

public class MethodTestData {

    @Before
    public void beforeMethod() {
        System.out.println("Method beforeMethod() was launched...");
    }

    @After
    public void afterMethod() {
        System.out.println("Method afterMethod() was launched...");
    }

    @Test
    public void test1() {
        System.out.println("Method test1() was launched...");
    }

    @Test
    public void test2() {
        System.out.println("Method test2() was launched...");
    }

    @Ignore
    @Test
    public void test3() {
        System.out.println("This method test3() should not be launched...");
    }
    @Test
    public void test4(){
        throw new IllegalStateException();
    }
}
