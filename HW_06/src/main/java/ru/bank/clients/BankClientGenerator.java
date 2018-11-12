package ru.bank.clients;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Генератор псевдослучайных клиентов, которые могут совпадать, т.к. некоторые клиенты обращаются в банк много раз
 */

public class BankClientGenerator {
    private JSONArray names;
    private JSONArray lastNames;
    private JSONArray birthYears;
    private Random random = new Random();

    {
        String resourcesPath = "HW_06\\src\\main\\resources";
        String jsonFileName = "names.json";
        File file = new File(resourcesPath + File.separator + jsonFileName);
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(file.getCanonicalPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(content);

        names = jsonObject.getJSONArray("names");
        lastNames = jsonObject.getJSONArray("last names");
        birthYears = jsonObject.getJSONArray("birthYears");

        System.out.println();
    }

    public Client getGeneratedClient() {
        return new Client(
                names.getString(random.nextInt(names.length())),
                lastNames.getString(random.nextInt(lastNames.length())),
                birthYears.getString(random.nextInt(birthYears.length())),
                random.nextInt(1000) * 1000);

    }
}
