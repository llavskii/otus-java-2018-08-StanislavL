package ru.bank.ATM.command;

import ru.bank.ATM.RuBankAtm;

public class RestoreAtmCommand implements AtmCommand {
//    private RuBankAtm atm;

//    public RestoreAtmCommand(RuBankAtm atm) {
//        this.atm = atm;
//    }

    @Override
    public void execute(RuBankAtm atm) {
        atm.restore();
    }
}
