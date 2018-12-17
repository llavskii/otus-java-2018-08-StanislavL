package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.ATMcontext;
import ru.bank.ATM.MenuMessages;

public class ChooseOperationState extends ATMstate {
    @Override
    public void doAction(ATMcontext context) {
        MenuMessages.displayMenuMessage(MenuMessages.CHOOSE_OPERATION);
        String inputLine = getInputLineFromClient();
        switch (inputLine.toUpperCase()) {
            case "D":
                context.setState(new InsertingBanknotesState());
                break;
            case "W":
                context.setState(new WithdrawState());
                break;
            case "K":
                context.setState(new BalanceInfoState());
                break;
            case "R":
                context.setState(new ReturnCardState());
                break;
        }
    }
}
