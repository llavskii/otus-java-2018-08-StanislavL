package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.ATMcontext;
import ru.bank.ATM.MenuMessages;

public class BalanceInfoState extends ATMstate {
    @Override
    public void doAction(ATMcontext context) {
        MenuMessages.displayMenuMessage(MenuMessages.YOUR_CARD_BALANCE,
                String.valueOf(context.getCurrentClient().getBankCardByNumber
                        (context.getCurrentClientCardNumber()).getBalance()));
        context.setState(new ChooseOperationState());
    }
}
