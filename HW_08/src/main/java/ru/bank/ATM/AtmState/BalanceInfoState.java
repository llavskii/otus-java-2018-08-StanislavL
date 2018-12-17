package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.AtmContext;
import ru.bank.ATM.MenuMessages;

public class BalanceInfoState extends AtmState {
    @Override
    public void doAction(AtmContext context) {
        MenuMessages.displayMenuMessage(MenuMessages.YOUR_CARD_BALANCE,
                context.getATM().getAtmName(),
                String.valueOf(context.getCurrentClient().getBankCardByNumber
                        (context.getCurrentClientCardNumber()).getBalance()));
        context.setState(new ChooseOperationState());
    }
}
