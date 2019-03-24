package example.gson;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        parse();
//        System.out.println("----------------");
//        serialize();
    }

    private static void serialize() {
        Car car = new Car();
        car.setBrand("Rover");
        car.setDoors(4);
        List<Wheel> wheels = Arrays.asList(new Wheel("A"), new Wheel("B"));
        car.setWheels(wheels);

        Gson gson = new Gson();

        String json = gson.toJson(car);
        System.out.println(json);
    }

    private static void parse() {
        String json = "{\"brand\":\"Rover\",\"doors\":4,\"wheels\":[{\"brand\":\"A\"},{\"brand\":\"B\"}]}";
        System.out.println(json);
        Gson gson = new Gson();
        Car car = gson.fromJson(json, Car.class);
        System.out.println(car.getBrand());
        System.out.println(car.getDoors());
        System.out.println(car.getWheels());
    }
}
