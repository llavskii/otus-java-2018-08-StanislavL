package ru;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AgentTester {
    static List objectSizeList = new ArrayList<String>();
//для запуска java -javaagent:HW_02-1.0-SNAPSHOT.jar -jar HW_02-1.0-SNAPSHOT.jar
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        addObjectSizeToList(new Object());
        addObjectSizeToList(a);
        addObjectSizeToList(b);
        addObjectSizeToList(1);
        addObjectSizeToList(new Integer[100]);
        addObjectSizeToList(new String());
        addObjectSizeToList("new line");
        addObjectSizeToList(new BigDecimal(1));
        addObjectSizeToList(new BigDecimal("999999999999999.999"));
        addObjectSizeToList(new ArrayList<String>());
        addObjectSizeToList(Calendar.getInstance());
        addObjectSizeToList(new HashMap<String, Integer>(100));
        objectSizeList.forEach(System.out::println);

    }

    public static void addObjectSizeToList(Object obj){
        objectSizeList.add(String.format("Object type: %s, size=%s", obj.getClass(), JavaAgentClassAndMemoryCount.getSize(obj)));
    }
}
class A{
};
class B{
    int a;
    int b;
};
