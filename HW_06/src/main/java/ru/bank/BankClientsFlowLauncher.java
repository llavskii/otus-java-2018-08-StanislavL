package ru.bank;

import ru.bank.cacheBySoftReference.BankClientCache;
import ru.bank.clients.BankClientGenerator;
import ru.bank.clients.Client;

/**
 * Класс имитирующий наплыв клиентов для получения кредита
 */

public class BankClientsFlowLauncher extends Thread {
    private BankClientGenerator clientGenerator = new BankClientGenerator();
    private BankClientCache bankClientCache;


    @Override
    public void run() {
        while (true) {
            Client client = clientGenerator.getGeneratedClient();
            if (bankClientCache.get(client.hashCode()) == null) bankClientCache.put(client);
        }
    }

    public void setBankClientCache(BankClientCache bankClientCache) {
        this.bankClientCache = bankClientCache;
    }
}
