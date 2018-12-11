package ru.bank.ATM.ATMstate;

import ru.bank.ATM.ATMcontext.ATMcontext;
import ru.bank.ATM.MenuMessages;

public class ReturnCardState extends ATMstate {
    @Override
    public void doAction(ATMcontext context) {
        MenuMessages.displayMenuMessage(MenuMessages.GOODBYE);
        context.nullifyCurrentClientData();
        context.setState(new InputCardState());
    }
}
