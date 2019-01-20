package ru.bank.ATM.command;

import ru.bank.ATM.RuBankAtm;

public class RestoreCommand implements Command {
    private RuBankAtm atm;

    public RestoreCommand(RuBankAtm atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.restore();
    }
}
