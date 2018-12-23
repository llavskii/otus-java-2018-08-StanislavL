package ru.bank.AtmObserver;

import ru.bank.ATM.AtmState.AtmState;
import ru.bank.ATM.RuBankAtm;

public class AtmObserver {
    private RuBankAtm bankAtm;

    public AtmObserver(RuBankAtm ruBankAtm) {
        this.bankAtm = ruBankAtm;
    }

    public void update(AtmObserver observerOfActiveAtm) {
        bankAtm.updateStatus(observerOfActiveAtm);
    }

    public void restore() {
        bankAtm.restore();
    }

    public int getAvailableSumm() {
        return bankAtm.getAvailableSumm();
    }

    public AtmState getAtmState() {
        return bankAtm.getContext().getState();
    }

}
