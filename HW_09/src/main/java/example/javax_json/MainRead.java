package example.javax_json;

import javax.json.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainRead {

    public static final String JSON_FILE="source-javax-json.json";

    public static void main(String[] args) throws IOException {
        InputStream fis = new FileInputStream(JSON_FILE);

        //create JsonReader object
        JsonReader jsonReader = Json.createReader(fis);


        //get JsonObject from JsonReader
        JsonObject jsonObject = jsonReader.readObject();

        //we can close IO resource and JsonReader now
        jsonReader.close();
        fis.close();

        //Retrieve data from JsonObject and create Employee bean
        Employee emp = new Employee();

        emp.setId(jsonObject.getInt("id"));
        emp.setName(jsonObject.getString("name"));
        emp.setPermanent(jsonObject.getBoolean("permanent"));
        emp.setRole(jsonObject.getString("role"));

        //reading arrays from json
        JsonArray jsonArray = jsonObject.getJsonArray("phoneNumbers");
        long[] numbers = new long[jsonArray.size()];
        int index = 0;
        for(JsonValue value : jsonArray){
            numbers[index++] = Long.parseLong(value.toString());
        }
        emp.setPhoneNumbers(numbers);

        //reading inner object from json object
        JsonObject innerJsonObject = jsonObject.getJsonObject("address");
        Address address = new Address();
        address.setStreet(innerJsonObject.getString("street"));
        address.setCity(innerJsonObject.getString("city"));
        address.setZipcode(innerJsonObject.getInt("zipcode"));
        emp.setAddress(address);

        //print employee bean information
        System.out.println(emp);

    }

}
