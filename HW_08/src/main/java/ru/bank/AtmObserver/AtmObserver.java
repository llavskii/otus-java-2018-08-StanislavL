package ru.bank.AtmObserver;

import ru.bank.ATM.AtmState.AtmState;
import ru.bank.ATM.RuBankAtm;
import ru.bank.ATM.command.RestoreCommand;

public class AtmObserver {
    private RuBankAtm bankAtm;
    private RestoreCommand restoreCommand;

    public AtmObserver(RuBankAtm bankAtm) {
        this.bankAtm = bankAtm;
    }

    public void setRestoreCommand(RestoreCommand restoreCommand) {
        this.restoreCommand = restoreCommand;
    }

    public void update(AtmObserver observerOfActiveAtm) {
        bankAtm.updateStatus(observerOfActiveAtm);
    }

    public void restore() {
        restoreCommand.execute();
    }

    public int getAvailableSumm() {
        return bankAtm.getAvailableSumm();
    }

    public AtmState getAtmState() {
        return bankAtm.getContext().getState();
    }

}
