package ru.bank.AtmDepartment;

import ru.bank.ATM.RuBankAtm;
import ru.bank.AtmObserver.AtmObserver;

import java.util.*;

public class RuBankDepartment implements Department, Runnable {

    private final Set<AtmObserver> atmObservers = new HashSet<>();

    public Set<AtmObserver> getAtmObservers() {
        return atmObservers;
    }

    @Override
    public void addAtms(List<RuBankAtm> bankAtms) {
        bankAtms.forEach((ATM) -> atmObservers.add(ATM.getObserver()));
    }

    @Override
    public int getSummaryAtmDeposit() {
        int summ = 0;
        for (AtmObserver observer : atmObservers) {
            summ += observer.getAvailableSumm();
        }
        return summ;

    }

    @Override
    public void notifyObservers(AtmObserver observerOfActiveAtm) {
        for (AtmObserver observer : atmObservers) {
            observer.update(observerOfActiveAtm);
        }
    }

    @Override
    public void run() {
        int prevDeposit = 0;
        //Вывод в консоль остатков с всех банкоматов при изменении суммы
        while (true) {
            if (getSummaryAtmDeposit() != prevDeposit) {
                prevDeposit = getSummaryAtmDeposit();
                System.out.println(String.format("-- ATM department has, rubles: %s --", prevDeposit));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}