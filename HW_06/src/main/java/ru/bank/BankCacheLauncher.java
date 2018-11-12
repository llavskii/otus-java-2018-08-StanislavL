package ru.bank;

import ru.bank.cacheBySoftReference.BankClientCache;
import ru.bank.clients.Client;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;

/**
 * launcher банковского кэша
 */

public class BankCacheLauncher extends Thread {
    private BankClientCache cache = new BankClientCache();
    private int nulledSoftReferences = 0;

    @Override
    public void run() {
        int prevNulledSoftReferences = 0;
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Iterator<Map.Entry<Integer, SoftReference<Client>>> iterator = cache.getClientsMap().entrySet().iterator();
            //очистка кэша от ссылок без клиентов
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                SoftReference<Client> softReference = (SoftReference<Client>) entry.getValue();
                if (softReference.get() == null) {
                    cache.getClientsMap().remove(entry.getKey());
                    nulledSoftReferences++;
                    if (nulledSoftReferences % 1000 == 0 & nulledSoftReferences != prevNulledSoftReferences) {
                        prevNulledSoftReferences = nulledSoftReferences;
                        System.out.println("удалено объектов SoftReference без ссылок на клиентов: " + nulledSoftReferences);
                    }
                }
            }
        }
    }

    public BankClientCache getCache() {
        return cache;
    }
}
