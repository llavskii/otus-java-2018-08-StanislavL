package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.ATMcontext;
import ru.bank.ATM.MenuMessages;

public class ReturnCardState extends ATMstate {
    @Override
    public void doAction(ATMcontext context) {
        MenuMessages.displayMenuMessage(MenuMessages.GOODBYE);
        context.nullifyCurrentClientData();
        context.setState(new InputCardState());
    }
}
