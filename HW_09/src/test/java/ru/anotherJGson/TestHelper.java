package ru.anotherJGson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestHelper {

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
}
