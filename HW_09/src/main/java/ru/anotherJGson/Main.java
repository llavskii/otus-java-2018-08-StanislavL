package ru.anotherJGson;

import ru.Person;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        AnotherJGson anotherJGson = new AnotherJGson();
        Person person = new Person("Petr", "Shtirlitz", 20);
        String json = anotherJGson.toJson(person);
        Field[] fields = getAllFields(person.getClass());
    }

    public static Field[] getAllFields(Class klass) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(klass.getDeclaredFields()));
        if (klass.getSuperclass() != null) {
            fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
        }
        return fields.toArray(new Field[]{});
    }
}
