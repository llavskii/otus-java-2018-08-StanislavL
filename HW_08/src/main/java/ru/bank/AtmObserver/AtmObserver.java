package ru.bank.AtmObserver;

import ru.bank.ATM.RuBankAtm;

public class AtmObserver {
    private RuBankAtm bankAtm;

    public AtmObserver(RuBankAtm ruBankAtm) {
        this.bankAtm = ruBankAtm;
    }

    public void update(RuBankAtm activeAtm) {
        bankAtm.updateStatus(activeAtm);
    }

    public RuBankAtm getBankAtm() {
        return bankAtm;
    }

}
