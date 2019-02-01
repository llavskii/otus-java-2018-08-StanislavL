package ru.bank.AtmObserver;

import ru.bank.ATM.AtmState.AtmState;
import ru.bank.ATM.RuBankAtm;
import ru.bank.ATM.command.AtmCommand;

public class AtmObserver {
    private RuBankAtm bankAtm;

    public AtmObserver(RuBankAtm bankAtm) {
        this.bankAtm = bankAtm;
    }

    public void update(AtmObserver observerOfActiveAtm) {
        bankAtm.updateStatus(observerOfActiveAtm);
    }

    public void sendCommand(AtmCommand atmCommand) {
        atmCommand.execute(bankAtm);
    }

    public int getAvailableSumm() {
        return bankAtm.getAvailableSumm();
    }

    public AtmState getAtmState() {
        return bankAtm.getContext().getState();
    }

    public static void sendCommand(AtmObserver observer) {
    }
}
