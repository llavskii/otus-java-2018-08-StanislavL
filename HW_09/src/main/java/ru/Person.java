package ru;

public class Person {
    private String name;
    private String surname;
    private boolean isMan = true;
    private char flag = 'g';
    private int age;
    private Integer year;
    private Person parent;
    private Person child;

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Person(String name, String surname, int age, Integer year, Person parent, Person child) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.year = year;
        this.parent = parent;
        this.child = child;
    }
}
