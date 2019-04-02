package ru.anotherJGson;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Person {
    private String name;
    private String surname;
    private boolean isMan;
    private int age;
    private String[] cards;
    private List books;
    private Map phones;
    private Integer year;
    private Person parent;
    private Person child;

    public Person(String name, String surname, boolean isMan, int age, String[] cards, List books, Map phones, Integer year, Person parent, Person child) {
        this.name = name;
        this.surname = surname;
        this.isMan = isMan;
        this.age = age;
        this.cards = cards;
        this.books = books;
        this.phones = phones;
        this.year = year;
        this.parent = parent;
        this.child = child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (isMan != person.isMan) return false;
        if (age != person.age) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(cards, person.cards)) return false;
        if (books != null ? !books.equals(person.books) : person.books != null) return false;
        if (phones != null ? !phones.equals(person.phones) : person.phones != null) return false;
        if (year != null ? !year.equals(person.year) : person.year != null) return false;
        if (parent != null ? !parent.equals(person.parent) : person.parent != null) return false;
        return child != null ? child.equals(person.child) : person.child == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (isMan ? 1 : 0);
        result = 31 * result + age;
        result = 31 * result + Arrays.hashCode(cards);
        result = 31 * result + (books != null ? books.hashCode() : 0);
        result = 31 * result + (phones != null ? phones.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (child != null ? child.hashCode() : 0);
        return result;
    }
}
