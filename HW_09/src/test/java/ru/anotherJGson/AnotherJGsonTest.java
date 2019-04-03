package ru.anotherJGson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AnotherJGsonTest {
    static final String FILE_PATH_JSON = "src\\test\\resources\\person.json";
    static final Gson GSON = new Gson();

    @Test
    public void singleValuesShouldBeAsFromGsonParsed() {
        assertAll("Single values should be equals string from GSON",
                () -> assertEquals(GSON.toJson(5), AnotherJGson.toJson(5)),
                () -> assertEquals(GSON.toJson(new Integer(5)), AnotherJGson.toJson(5)),
                () -> assertEquals(GSON.toJson(10.0), AnotherJGson.toJson(10.0)),
                () -> assertEquals(GSON.toJson("string"), AnotherJGson.toJson("string")),
                () -> assertNotEquals(GSON.toJson("string"), AnotherJGson.toJson("stri___________ng"))
        );
    }

    @Test
    public void personFromFileByGSONShouldBeEqualAfterAnotherJGson() {
        Person person = getPerson();
        writeJsonByAnotherJGsonToFile();
        assertEquals(person, getPersonFromFileByGson());
    }

    private static void writeJsonByAnotherJGsonToFile() {
        String json = AnotherJGson.toJson(getPerson());
        try (FileWriter file = new FileWriter(FILE_PATH_JSON)) {
            file.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Person getPerson() {
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


    private static Person getPersonFromFileByGson() {
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(FILE_PATH_JSON));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(reader, Person.class);
    }
}