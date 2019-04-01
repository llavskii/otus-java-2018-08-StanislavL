package ru.anotherJGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.Person;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Main {
    static String filePath = "HW_09\\src\\main\\resources\\testGson.json";

    public static void main(String[] args) throws Exception {
        writeGson();

        AnotherJGson anotherJGson = new AnotherJGson();
        Person person = getPersons()[0];

        System.out.println(anotherJGson.toJson(person));
        System.out.println("----------------------------");

        System.out.println(anotherJGson.toJson(5));
        System.out.println(anotherJGson.toJson(true));
        System.out.println(anotherJGson.toJson(5.0));
        System.out.println(anotherJGson.toJson(new Integer(5)));
        System.out.println(anotherJGson.toJson('h'));
        System.out.println(anotherJGson.toJson("string"));

//        System.out.println(AnotherJGson.getAllFields(int.class).length);
//        System.out.println(AnotherJGson.getAllFields(Person.class).length);
//        System.out.println(AnotherJGson.getAllFields(HashMap.class).length);
//        System.out.println(AnotherJGson.getAllFields(ArrayList.class).length);
//        System.out.println(AnotherJGson.getAllFields(String.class).length);
//        System.out.println(AnotherJGson.getAllFields(Integer.class).length);
    }



    static Person[] getPersons() {
        Person personChild = new Person("Sofia", "Lenina", 33, 1980, null, null);
        Person personParent = new Person("Petr", "Ivanov", 33, 1985, null, null);
        Person person = new Person("Ivan", "Ivanov", 33, 1900, personParent, personChild);
        Person[] persons = new Person[]{person, person, person};
        return persons;
    }

    public static void writeGson() throws IOException {
        try (FileWriter fileWriterGson = new FileWriter(filePath)) {

            Gson gson = new Gson();
            Gson gsonByBuilder = new GsonBuilder().setPrettyPrinting().create();
            String s = "string";
            int a = 5;
            Integer b = 5;
            Map <String, String> map = new HashMap<>();
            map.put("s", "s");
            gsonByBuilder.toJson(getPersons(), fileWriterGson);

            Person[] personsDes = gson.fromJson(new FileReader(filePath), Person[].class);
        }
    }
}
