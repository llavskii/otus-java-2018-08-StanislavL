package ru.anotherGson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import ru.Person;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class anotherGson {
    public static void main(String[] args) throws IOException {
        String filePathGson = "HW_09\\src\\main\\resources\\person for Gson.json";
        String filePathSimple = "HW_09\\src\\main\\resources\\person for Simple.json";


        try (FileWriter fileWriterGson = new FileWriter(filePathGson)) {
            Person person = new Person("Ivan", "Ivanov", 33);
            Person[] persons = new Person[]{person, person, person};

            Gson gson = new Gson();
            //gson.toJson(persons, fileWriter);

            Gson gsonByBuilder = new GsonBuilder().setPrettyPrinting().create();

            gsonByBuilder.toJson(persons, fileWriterGson);
            Person[] personsDes = gson.fromJson(new FileReader(filePathGson), Person[].class);
        }



        try (FileWriter fileWriterSimple = new FileWriter(filePathSimple)){
            JSONObject jsonObjectSimple = new JSONObject();
            jsonObjectSimple.put("name", "iakov");
            jsonObjectSimple.put("surname", "isaakovich");
            jsonObjectSimple.put("age", "30");

            fileWriterSimple.write(jsonObjectSimple.toJSONString());
        }

        System.out.println();
    }
}
