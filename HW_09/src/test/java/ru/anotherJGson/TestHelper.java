package ru.anotherJGson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class TestHelper {

    static void writeJsonByAnotherJGsonToFile() {
        String json = AnotherJGson.toJson(getPerson());
        File file = new File(AnotherJGson.class.getClassLoader().getResource("person.json").getPath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Person getPerson() {
        String[] cards = new String[]{"1", "2", "3"};
        List<String> books = new ArrayList<String>() {{
            add("Sorokin");
            add("Pelevin");
        }};
        Map<String, String> phones = new HashMap<String, String>() {{
            put("new", "motorola");
            put("prev", "siemens");
        }};
        Person personChildChild = new Person("personChildChild Name", "personChildChild surname", true, 40, cards, books, phones, 1985, null, null);
        Person personParentParent = new Person("personParentParent name", "personParentParent surname", true, 30, cards, books, phones, 1970, null, null);
        Person personChild = new Person("personChild name", "personChild surname", true, 33, cards, books, phones, 1980, personParentParent, personChildChild);
        Person personParent = new Person("personParent name", "personParent surname", false, 33, cards, books, phones, 1985, personParentParent, personChildChild);
        return new Person("Ivan", "Ivanov", true, 33, cards, books, phones, 1900, personParent, personChild);
    }


    static Person getPersonFromFileByGson() {
        Gson gson = new Gson();
        InputStream is = AnotherJGsonTest.class.getClassLoader().getResourceAsStream("person.json");
        Person person = null;
        try (JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            person = gson.fromJson(reader, Person.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return person;
    }

}
