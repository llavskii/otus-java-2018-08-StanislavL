package ru.bank.ATM.command;

import ru.bank.ATM.RuBankAtm;

//The AtmCommand interface
public interface AtmCommand {
    void execute(RuBankAtm atm);
}
