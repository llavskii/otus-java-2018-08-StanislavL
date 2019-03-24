package example.javax_json;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class MainWrite {

    public static void main(String[] args) throws FileNotFoundException {

        Employee emp = createEmployee();

        JsonObjectBuilder empBuilder = Json.createObjectBuilder();
        JsonObjectBuilder addressBuilder = Json.createObjectBuilder();
        JsonArrayBuilder phoneNumBuilder = Json.createArrayBuilder();

        for (long phone : emp.getPhoneNumbers()) {
            phoneNumBuilder.add(phone);
        }

        addressBuilder.add("street", emp.getAddress().getStreet())
                .add("city", emp.getAddress().getCity())
                .add("zipcode", emp.getAddress().getZipcode());

        empBuilder.add("id", emp.getId())
                .add("name", emp.getName())
                .add("permanent", emp.isPermanent())
                .add("role", emp.getRole());

        empBuilder.add("phoneNumbers", phoneNumBuilder);
        empBuilder.add("address", addressBuilder);

        JsonObject empJsonObject = empBuilder.build();

        System.out.println("Employee JSON String\n" + empJsonObject);

        //write to file
        OutputStream os = new FileOutputStream("source-javax-json-output.json");
        JsonWriter jsonWriter = Json.createWriter(os);
        jsonWriter.writeObject(empJsonObject);
        jsonWriter.close();
    }

    public static Employee createEmployee() {

        Employee emp = new Employee();
        emp.setId(90118);
        emp.setName("Alexander");
        emp.setPermanent(false);
        emp.setPhoneNumbers(new long[]{79996005444L, 79673321332L});
        emp.setRole("Contractor");

        Address add = new Address();
        add.setCity("Astrakhan");
        add.setStreet("Start street");
        add.setZipcode(414022);
        emp.setAddress(add);

        return emp;
    }
}

