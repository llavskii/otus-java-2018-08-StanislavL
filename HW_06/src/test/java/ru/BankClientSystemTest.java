package ru;

import ru.bank.BankCacheLauncher;
import ru.bank.BankClientsFlowLauncher;

/**
 * Количество уникальных клиентов, полученных из json = 1000.
 * <p>
 * -Xmx2500m -Xms2500m - при данных настройках, когда размер heap значительно больше возможной занимаемой памяти,
 * сообщение что клиенты были удалены Garbage collector!!! не будет выведено, т.к. нет приближения OOM. Но будет увеличиваться
 * количество попаданий в кэш, при неизменном количестве промахов и неизменном количестве кэша
 * <p>
 * -Xmx500m -Xms500m - при этих настройках размер heap меньше чем размер всех клиентов в памяти, есть вероятность ООМ,
 * увеличивается количество попаданий в кэш при увеличении количества промахов, есть объекты SoftReference без ссылок на клиентов
 */

public class BankClientSystemTest {
    public static void main(String[] args) {
        BankCacheLauncher cacheLauncher = new BankCacheLauncher();
        BankClientsFlowLauncher clientsFlowLauncher = new BankClientsFlowLauncher();
        clientsFlowLauncher.setBankClientCache(cacheLauncher.getCache());
        //запуск кэша
        cacheLauncher.start();
        //запуск притока клиентов
        clientsFlowLauncher.start();
    }
}
