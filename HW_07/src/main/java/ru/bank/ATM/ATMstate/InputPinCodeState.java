package ru.bank.ATM.ATMstate;

import ru.bank.ATM.ATMcontext.ATMcontext;
import ru.bank.ATM.MenuMessages;
import ru.bank.bankCard.BankCard;
import ru.bank.clients.BankDataBase;

import static ru.bank.ATM.MenuMessages.displayMenuMessage;

public class InputPinCodeState extends ATMstate {
    @Override
    public void doAction(ATMcontext context) {
        displayMenuMessage(MenuMessages.ENTER_PIN_CODE);
        String inputLine = getInputLineFromClient();
        //Проверка на наличие введенных символов, иначе - предлагается ввести заново номер карты
        if (inputLine == null) {
            displayMenuMessage(MenuMessages.CARD_RETURNED_AFTER_WAITING);
            context.setState(new InputCardState());
            return;
        }

        if (BankCard.validatePINnumber(inputLine)) {
            BankCard bankCard = new BankCard(context.getCurrentClientCardNumber(), inputLine);
            //Если это новый клиент, то добавляем карту данному клиенту с номером и пин кодом и вносим его в БД
            if (context.isNewClient()) {
                context.getCurrentClient().addCard(bankCard);
                BankDataBase.addClient(context.getCurrentClient());
            } //Если это не новый клиент, и у него есть карта с таким номером
            else if (context.getCurrentClient().getClientCards().contains(bankCard)) {
                //то проверяем валидность пин кода
                if (!context.getCurrentClient().getBankCardByNumber(context.getCurrentClientCardNumber()).getPIN().equals(inputLine)) {
                    displayMenuMessage(MenuMessages.PIN_CODE_IS_INCORRECT);
                    return;
                }
            } //Если это не новый клиент, и у него нет карты с таким номером, то добавляем ему новую карту
            else {
                context.getCurrentClient().addCard(bankCard);
            }
            context.setState(new ChooseOperationState());
        } else {
            displayMenuMessage(MenuMessages.PIN_CODE_IS_NOT_RECOGNIZED);
        }
    }
}
