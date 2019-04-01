package ru.anotherJGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONObject;
import ru.Person;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static String filePathGson = "HW_09\\src\\main\\resources\\testGson.json";
    static String filePathGsonAnotherJGson = "HW_09\\src\\main\\resources\\testGson2.json";

    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        writeGson();
        readJson();

        AnotherJGson anotherJGson = new AnotherJGson();

        System.out.println("----------------------------");
        Person person = getPersons()[0];
        System.out.println(anotherJGson.toJson(person));
        System.out.println("----------------------------");
        System.out.println(anotherJGson.toJson(5));
        System.out.println(anotherJGson.toJson(true));
        System.out.println(anotherJGson.toJson(5.0));
        System.out.println(anotherJGson.toJson(new Integer(5)));
        System.out.println(anotherJGson.toJson('h'));
        System.out.println(anotherJGson.toJson("string"));
        System.out.println("----------------------------");


//        System.out.println(AnotherJGson.getAllFields(int.class).length);
//        System.out.println(AnotherJGson.getAllFields(Person.class).length);
//        System.out.println(AnotherJGson.getAllFields(HashMap.class).length);
//        System.out.println(AnotherJGson.getAllFields(ArrayList.class).length);
//        System.out.println(AnotherJGson.getAllFields(String.class).length);
//        System.out.println(AnotherJGson.getAllFields(Integer.class).length);
    }



    static Person[] getPersons() {
        Person personChildChild = new Person("personChildChild Name", "personChildChild surname", 33, 1980, null, null);
        Person personParentParent  = new Person("personParentParent name", "personParentParent surname", 33, 1985, null, null);
        Person personChild = new Person("personChild name", "personChild surname", 33, 1980, personParentParent, personChildChild);
        Person personParent = new Person("personParent name", "personParent surname", 33, 1985, personParentParent, personChildChild);
        Person person = new Person("Ivan", "Ivanov", 33, 1900, personParent, personChild);
        Person[] persons = new Person[]{person, person, person};
        return persons;
    }

    public static void writeGson() throws IOException {
        try (FileWriter fileWriterGson = new FileWriter(filePathGson)) {

            Gson gson = new Gson();
            Gson gsonByBuilder = new GsonBuilder().setPrettyPrinting().create();
            String s = "string";
            int a = 5;
            Integer b = 5;
            Map <String, String> map = new HashMap<>();
            map.put("s", "s");
            gsonByBuilder.toJson(getPersons(), fileWriterGson);

            Person[] personsDes = gson.fromJson(new FileReader(filePathGson), Person[].class);
        }
    }

    public static void readJson() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePathGsonAnotherJGson));
        Person person = gson.fromJson(reader, Person.class);

    }
}
