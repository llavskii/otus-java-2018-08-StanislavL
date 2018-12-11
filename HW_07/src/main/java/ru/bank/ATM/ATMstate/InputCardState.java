package ru.bank.ATM.ATMstate;

import ru.bank.ATM.ATMcontext.ATMcontext;
import ru.bank.ATM.MenuMessages;
import ru.bank.bankCard.BankCard;
import ru.bank.clients.BankClientGenerator;
import ru.bank.clients.BankDataBase;
import ru.bank.clients.Client;

import static ru.bank.ATM.MenuMessages.displayMenuMessage;

public class InputCardState extends ATMstate {
    @Override
    public void doAction(ATMcontext context) {
        displayMenuMessage(MenuMessages.INSERT_BANK_CARD);
        String inputLine = getInputLineFromClient();

        if (BankCard.validateCardNumber(inputLine)) {
            //Присваиваем введенный номер переменной currentClientCardNumber
            context.setCurrentClientCardNumber(inputLine.replaceAll("\\s", ""));
            //Поиск по клиентам в БД на наличие у них карты с введенным номером
            Client client = BankDataBase.availabilityCheckClientByCardNumber(context.getCurrentClientCardNumber());
            //Если клиент найден, то присваиваем его переменной currentClient
            if (client != null) {
                context.setCurrentClient(client);
                //Устанавливаем переменную = false, это один из активных клиентов
                context.setNewClient(false);
            } else {
                //Если клиент не найден, то генерим для него тестового клиента
                System.out.println("[ Сгенерирован тестовый клиент! ]");
                context.setCurrentClient(BankClientGenerator.getGeneratedClient());

                //Устанавлиаем переменную isNewClient, если этого сгененированного
                // клиента не было /или был в БД (у нас есть клиенты с несколькими картами!)
                context.setNewClient(!BankDataBase.availabilityCheckClient(context.getCurrentClient()));
            }
            context.setState(new InputPinCodeState());
        } else {
            displayMenuMessage(MenuMessages.CARD_IS_NOT_RECOGNIZED);
        }
    }
}
