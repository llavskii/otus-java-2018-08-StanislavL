package ru.bank.ATM;

public enum MenuMessages {

    DASHLINE("-------------------------------------------------------------------"),
    APPENDER_ATM_NAME("[Message in: %s] "),
    //00
    INSERT_BANK_CARD(APPENDER_ATM_NAME.getMessage() + "Please, insert a bank card into the card reader \n (Hint: 16 digits)"),
    //01
    CARD_IS_NOT_RECOGNIZED(APPENDER_ATM_NAME.getMessage() + "Your card is not recognized (invalid number)!\n" + DASHLINE.getMessage()),
    //10
    ENTER_PIN_CODE(APPENDER_ATM_NAME.getMessage() + "Please, enter PIN code \n (Hint: 4 digits. Or the card will be returned in 8 seconds)"),
    //11
    PIN_CODE_IS_NOT_RECOGNIZED(APPENDER_ATM_NAME.getMessage() + "Your PIN code is not recognized (invalid number)!\n" + DASHLINE.getMessage()),
    //12
    PIN_CODE_IS_INCORRECT(APPENDER_ATM_NAME.getMessage() + "Your PIN code is incorrect!\n" + DASHLINE.getMessage()),
    //20
    CHOOSE_OPERATION(APPENDER_ATM_NAME.getMessage() + "Select the operation you want to perform, enter:\n" +
            "   D - if you want to Deposit cash in the card account\n" +
            "   W - if you want to Withdraw from the card\n" +
            "   K - if you want to Know your card account balance\n" +
            "   R - if you want to Return your card\n" + DASHLINE.getMessage()),

    //D
    //30
    INSERT_BANKNOTES(APPENDER_ATM_NAME.getMessage() + "Insert banknotes in the bill acceptor\n" +
            "(Hint: enter through the space number of banknotes * denomination of banknotes in rubles, \n" +
            "for example 2*500 5*1000"),
    //31
    YOU_HAVE_CREDITED_YOUR_CARD(APPENDER_ATM_NAME.getMessage() + "You have credited your card to %s rubles\n" + DASHLINE.getMessage()),
    //32
    INVALID_BANKNOTES_INPUT(APPENDER_ATM_NAME.getMessage() + "Invalid input, banknotes not recognized: %s \n" +
            "Example of correct input: 1*100 2*200 3*500 4*1000 5*2000 6*5000\n" + DASHLINE.getMessage()),

    //W
    //40
    INPUT_WITHDRAW_AMOUNT(APPENDER_ATM_NAME.getMessage() + "Enter the amount you want to withdraw from your card account, but not less than 100 rubles, not more than 999900"),
    //41
    AMOUNT_CANNOT_BE_ISSUED(APPENDER_ATM_NAME.getMessage() + "This amount cannot be issued\n" + DASHLINE.getMessage()),
    //42
    YOU_GOT_THE_FOLLOWING(APPENDER_ATM_NAME.getMessage() + "You got the following: %s rubles\n" + DASHLINE.getMessage()),

    //K
    //50
    YOUR_CARD_BALANCE(APPENDER_ATM_NAME.getMessage() + "Your card balance: %s rubles"),

    //R
    //90
    GOODBYE(APPENDER_ATM_NAME.getMessage() + "Goodbye!\n" + DASHLINE.getMessage()),
    //92,
    CARD_RETURNED_AFTER_WAITING(APPENDER_ATM_NAME.getMessage() + "Your card has been returned after 8 seconds waiting!\n" + DASHLINE.getMessage()),
    //93
    WAITING_FOR_ACTIVATION_ATM(APPENDER_ATM_NAME.getMessage() + "Transferred to pending!\n" + DASHLINE.getMessage());

    public static String appenderAtmName(String AtmName) {
        return "[Message in: " + AtmName + "] ->";
    }

    private String message;

    MenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static void displayMenuMessage(MenuMessages menuMessage, String ... args) {
        System.out.println(String.format(menuMessage.message, args));
    }
}