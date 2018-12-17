package ru.bank.ATM;

import ru.bank.ATM.AtmContext.AtmContext;
import ru.bank.ATM.AtmState.InputCardState;
import ru.bank.ATM.AtmState.WaitingActiveStatusState;
import ru.bank.ATM.banknotes.Rubles;
import ru.bank.clients.BankClientGenerator;

import java.util.*;

public class RuBankAtm implements Runnable {
    private int initializeCapacity;
    private String AtmName;
    private BankClientGenerator clientGenerator = new BankClientGenerator();
    private final Map<Rubles, Integer> ATMrubleBoxes = new HashMap<>();

    private static Comparator<Integer> banknotesDescComparator = (o1, o2) -> o2.compareTo(o1);
    private AtmContext context = new AtmContext(new WaitingActiveStatusState());
    private int availableSumm;

    //Конструктор банкомата с параметром int имитирует загрузку одинаковым количеством банкнот в каждую ячейку
    public RuBankAtm(String AtmName, int boxCapacity) {
        //Сохраняем первоначальное значение в переменную initializeCapacity
        initializeCapacity = boxCapacity;
        this.AtmName = AtmName;
        arrangeBanknoteBoxes(boxCapacity);
    }

    private void arrangeBanknoteBoxes(int boxCapacity) {
        Rubles[] rubles = Rubles.values();
        for (int i = 0; i < rubles.length; i++) {
            ATMrubleBoxes.put(rubles[i], boxCapacity);
        }
    }


    @Override
    public void run() {
        context.setATM(this);
        while (true) {
            context.doAction();
        }
    }

    public String getAtmName() {
        return AtmName;
    }

    public AtmContext getContext() {
        return context;
    }

    public Map<Rubles, Integer> getATMrubleBoxes() {
        return ATMrubleBoxes;
    }

    public Map<Integer, Integer> checkAvailabilityBanknotesInATM(String stringRequestWithdrawSumm) {
        int requestWithdrawSumm = Integer.parseInt(stringRequestWithdrawSumm);
        Map<Integer, Integer> availableBanknotes = new TreeMap<>(banknotesDescComparator);
        Map<Integer, Integer> withdrawBanknotes = new TreeMap<>(banknotesDescComparator);
        ATMrubleBoxes.forEach((k, v) -> {
            if (v > 0) availableBanknotes.put(k.denomination(), v);
        });

        Iterator iterator = availableBanknotes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            //номинал секции
            int k = (int) pair.getKey();
            //количество купюр в секции
            int v = (int) pair.getValue();
            //проверка наличия купюр в секции
            if (v == 0) continue;
            int integerQuantity = requestWithdrawSumm / k;
            //ограничение в выдаче по достаточности купюр в выбранной секции
            if (integerQuantity > v) integerQuantity = v;
            if (integerQuantity == 0) continue;
            withdrawBanknotes.put(k, integerQuantity);
            requestWithdrawSumm -= k * integerQuantity;
            //если остаток от запрашиваемой суммы = 0, прерываем цикл
            if (requestWithdrawSumm == 0) break;
        }
        //если осталась какая либо невыданная сумма, возвращаем null
        if (requestWithdrawSumm > 0) return null;
        return withdrawBanknotes;

    }

    public boolean checkAvailabilityWithdrawSumm(String withdrawSumm) {
        return getAvailableSumm() > Integer.parseInt(withdrawSumm);
    }

    public void addBundleToBox(int quantity, int denomination) {
        Rubles[] boxes = Rubles.values();
        for (Rubles box : boxes) {
            if (box.denomination() == denomination) {
                int previous = ATMrubleBoxes.get(box);
                ATMrubleBoxes.put(box, quantity + previous);
            }
        }
    }

    public BankClientGenerator getClientGenerator() {
        return clientGenerator;
    }

    public void updateStatus(RuBankAtm activeAtm) {
        if (AtmName.equals(activeAtm.getAtmName())) {
            //Если имя банкомата - текущий банкомат, то его статус становится активным
            context.setState(new InputCardState());
        } else {
            //Если это не активный банкомат, то он будет переведен в состояние ожидания с выводом в консоль сообщения об этом
            context.setState(new WaitingActiveStatusState());
            MenuMessages.displayMenuMessage(MenuMessages.WAITING_FOR_ACTIVATION_ATM, context.getATM().getAtmName());
        }
    }

    public int getAvailableSumm() {
        Rubles[] boxes = Rubles.values();
        int quantity;
        int summ = 0;
        for (Rubles box : boxes) {
            quantity = ATMrubleBoxes.get(box);
            summ += quantity * box.denomination();
        }
        return summ;
    }

    //Восстановление емкости ячеек в банкомате до первоначального
    public void restore() {
        arrangeBanknoteBoxes(initializeCapacity);
    }
}