package ru.anotherJGson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AnotherJGsonTest {

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
        Person person = TestHelper.getPerson();
        TestHelper.writeJsonByAnotherJGsonToFile();
        assertEquals(person, TestHelper.getPersonFromFileByGson());
    }

    @Test
    public void singleCustomValuesShouldBeAsFromGsonParsed() {
        assertAll("Single values should be equals string from GSON",
                () -> assertEquals(GSON.toJson(Collections.singletonList(1)), AnotherJGson.toJson(Collections.singletonList(1))),
                () -> assertEquals(GSON.toJson(new int[]{1, 2, 3}), AnotherJGson.toJson(new int[]{1, 2, 3})),
                () -> assertEquals(GSON.toJson(new String[]{"1", "2", "3"}), AnotherJGson.toJson(new String[]{"1", "2", "3"}))
        );
    }
}