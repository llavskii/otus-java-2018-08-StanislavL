package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.AtmContext;
import ru.bank.ATM.MenuMessages;

public class ReturnCardState extends AtmState {
    @Override
    public void doAction(AtmContext context) {
        MenuMessages.displayMenuMessage(MenuMessages.GOODBYE, context.getATM().getAtmName());
        context.nullifyCurrentClientData();
        context.setState(new WaitingActiveStatusState());
    }
}
