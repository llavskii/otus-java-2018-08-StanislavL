package ru.bank.cacheBySoftReference;

import ru.bank.clients.Client;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Кэш банка для получения данных по клиентам
 */
public class BankClientCache {
    private final ConcurrentHashMap<Integer, SoftReference<Client>> clientsMap = new ConcurrentHashMap<>();
    private int hitCount = 0;
    private int missCount = 0;
    private int nulledClients = 0;

    public ConcurrentHashMap<Integer, SoftReference<Client>> getClientsMap() {
        return clientsMap;
    }

    public BankClientCache() {
    }

    public void put(Client client) {
        clientsMap.put(client.hashCode(), new SoftReference<>(client));
    }

    public Client get(Integer key) {
        SoftReference softRef = clientsMap.get(key);
        if (softRef == null) {
            missCount++;
            return null;
        } else hitCount++;
        Client client = (Client) softRef.get();
        if (client == null) {
            nulledClients++;
        }
        if (hitCount % 100 == 0 & hitCount > 0) {
            System.out.println(String.format("попаданий в кэш: %1$s, промахов в кэш: %2$s, размер кэша: %3$s клиентов",
                    hitCount, missCount, clientsMap.size()));
        }
        if (nulledClients % 100 == 0 & nulledClients > 0) {
            System.out.println(nulledClients + " клиентов были в кэше, но были удалены Garbage collector!!!");
        }
        return client;
    }

    public SoftReference<Client> remove(Client client) {
        return clientsMap.remove(client.hashCode());
    }

}
