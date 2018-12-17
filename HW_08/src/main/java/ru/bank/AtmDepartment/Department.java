package ru.bank.AtmDepartment;

import ru.bank.ATM.AtmState.WaitingActiveStatusState;
import ru.bank.ATM.RuBankAtm;
import ru.bank.AtmObserver.AtmObserver;

import java.util.*;

public class Department {
    static Set<AtmObserver> atmObservers = new HashSet<>();

    public static void main(String[] args) throws InterruptedException {

        int capacity = 10;

        //Создание объектов ATM
        RuBankAtm atm1RedSquare = new RuBankAtm("1 Red square", capacity);
        RuBankAtm atm2Hermitage = new RuBankAtm("2 Hermitage", capacity);
        RuBankAtm atm3Baikal = new RuBankAtm("3 Baikal", capacity);

        //Запуск потоков банкоматово
        new Thread(atm1RedSquare).start();
        new Thread(atm2Hermitage).start();
        new Thread(atm3Baikal).start();

        //Добавление наблюдателей каждому банкомату
        atmObservers.add(new AtmObserver(atm1RedSquare));
        atmObservers.add(new AtmObserver(atm2Hermitage));
        atmObservers.add(new AtmObserver(atm3Baikal));

        //Вывод в консоль суммы остатков с всех банкоматов до начала работы с ними
        getSummaryAtmDeposit();

        /*
        В цикле перебираем каждый банкомат, выводя из состояния ожидания в статус активного.
        В активном статусе можно снять или внести в банкомат наличность, изменив баланс его ячеек
        При переходе в статус ожидания из статуса активного наблюдатель извещает об этом остальные банкоматы
        и можно продолжить работать с следующим банкоматом
        */
        for (AtmObserver observer : atmObservers) {
            notifyAllObservers(observer.getBankAtm());
            while (observer.getBankAtm().getContext().getState().getClass() != WaitingActiveStatusState.class) {
                Thread.sleep(1);
            }
        }

        //Вывод в консоль суммы остатков с всех банкоматов после работы с ними
        getSummaryAtmDeposit();

        //Восстанавливает состояние всех банкоматов до начального
        restoreAtmDepartmentBalance();

        //Вывод в консоль суммы остатков с всех банкоматов после восстановления (должна быть равна сумму до начала работы)
        getSummaryAtmDeposit();
    }

    private static void restoreAtmDepartmentBalance() {
        for (AtmObserver observer : atmObservers) {
            observer.getBankAtm().restore();
        }
    }

    private static void getSummaryAtmDeposit() {
        int summ = 0;
        for (AtmObserver observer : atmObservers) {
            summ += observer.getBankAtm().getAvailableSumm();
        }
        System.out.println("ATM department has, rubles: " + summ);
    }


    public static void notifyAllObservers(RuBankAtm activeAtm) {
        for (AtmObserver observer : atmObservers) {
            observer.update(activeAtm);
        }
    }
}