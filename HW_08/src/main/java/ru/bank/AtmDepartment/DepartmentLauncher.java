package ru.bank.AtmDepartment;

import ru.bank.ATM.AtmState.WaitingActiveStatusState;
import ru.bank.ATM.RuBankAtm;
import ru.bank.ATM.command.RestoreAtmCommand;
import ru.bank.AtmObserver.AtmObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DepartmentLauncher {
    public static void main(String[] args) throws InterruptedException {
        //Создание списка из объектов ATM
        int capacity = 10;
        List<RuBankAtm> bankAtms = new ArrayList<>();
        bankAtms.add(new RuBankAtm("1 Red square", capacity));
        bankAtms.add(new RuBankAtm("2 Hermitage", capacity));
        bankAtms.add(new RuBankAtm("3 Baikal", capacity));

        //Запуск потоков банкоматов
        bankAtms.forEach((ATM) -> new Thread(ATM).start());

        //Создание объекта Department
        Department department = new RuBankDepartment();

        //Добавление банкоматов в Department
        department.addAtms(bankAtms);

        new Thread((Runnable) department).start();

        final Set<AtmObserver> observers = department.getAtmObservers();

        /*
        В цикле перебираем каждый банкомат, выводя из состояния ожидания в статус активного.
        В активном статусе можно снять или внести в банкомат наличность, изменив баланс его ячеек
        При переходе в статус ожидания из статуса активного наблюдатель извещает об этом остальные банкоматы
        и можно продолжить работать с следующим банкоматом
        */
        for (AtmObserver observer : observers) {
            department.notifyObservers(observer);
            while (observer.getAtmState().getClass() != WaitingActiveStatusState.class) {
                Thread.sleep(1);
            }
        }

        //Восстанавление состояние всех банкоматов до начального через метод sendCommand для наблюдателей, которые передают команду банкоматам
        observers.forEach(observer -> observer.sendCommand(new RestoreAtmCommand()));

        System.exit(0);
    }
}
