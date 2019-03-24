package ru;

public class Person {
    private String name;
    private String surname;
    private int age;
    private Person parent;
    private Person child;

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}
