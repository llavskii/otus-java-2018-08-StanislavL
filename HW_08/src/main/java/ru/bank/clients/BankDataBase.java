package ru.bank.clients;

import java.util.Set;
import java.util.TreeSet;

public class BankDataBase {
    private static final Set<Client> bankDBclients = new TreeSet<>();

    private BankDataBase() {
    }

    public static void addClient(Client client) {
        bankDBclients.add(client);
    }

    public static boolean availabilityCheckClient(Client client) {
        return bankDBclients.contains(client);
    }

    public static Client availabilityCheckClientByCardNumber(String cardNumber) {
        for (Client client : bankDBclients) {
            if (client.availabilityCheckCard(cardNumber)) return client;
        }
        return null;
    }

    public void changePINforClient(Client client, String PIN) {
        throw new UnsupportedOperationException();
    }
}
