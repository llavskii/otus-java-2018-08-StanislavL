package ru.bank.clients;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Генератор псевдослучайных клиентских данных: Фамилия, Имя, без карты
 */

public class BankClientGenerator {
    private JSONArray names;
    private JSONArray lastNames;
    private Random random = new Random();

    public BankClientGenerator() {
        String resourcesPath = "HW_08\\src\\main\\resources";
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

    }

    public Client getGeneratedClient() {
        Client client = new Client(names.getString(random.nextInt(names.length())),
                lastNames.getString(random.nextInt(lastNames.length())));
        return client;
    }
}
