package ru.bank.AtmDepartment;

import ru.bank.ATM.RuBankAtm;
import ru.bank.AtmObserver.AtmObserver;

import java.util.List;
import java.util.Set;

public interface Department {
    //Извещает всех наблюдателей в департаменте об изменении статуса одним из банкоматов на активный
    void notifyObservers(AtmObserver observerOfActiveAtm);

    //Геттер для наблюдателей департамента
    Set<AtmObserver> getAtmObservers();

    //Добавляет банкоматы в департамент, но не напрямую, а только через их наблюдателей
    void addAtms(List<RuBankAtm> bankAtms);

    //Геттер для получения суммы наличности в всем департаменте
    int getSummaryAtmDeposit();

    //Восстановление состояния до первоначального
    void restore();
}
