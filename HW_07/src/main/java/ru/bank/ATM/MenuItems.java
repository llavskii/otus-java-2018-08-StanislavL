package ru.bank.ATM;

import java.util.HashMap;
import java.util.Map;

public final class MenuItems {
    public static final Map<Integer, String> menuItems = new HashMap<>();

    static {
        String dashLine = "-------------------------------------------------------------------\n";
        menuItems.put(0, "Please, insert a bank card into the card reader \n (Hint: 16 digits)");
        menuItems.put(1, "Your card is not recognized (invalid number)!\n" + dashLine);

        menuItems.put(10, "Please, enter PIN code \n (Hint: 4 digits. Or the card will be returned in 8 seconds)");
        menuItems.put(11, "Your PIN code is not recognized (invalid number)!");
        menuItems.put(12, "Your PIN code is incorrect!");

        menuItems.put(20, "Select the operation you want to perform, enter:\n" +
                "   D - if you want to Deposit cash in the card account\n" +
                "   W - if you want to Withdraw from the card\n" +
                "   K - if you want to Know your card account balance\n" +
                "   R - if you want to Return your card");

        //D
        menuItems.put(30, "Insert banknotes in the bill acceptor\n" +
                "(Hint: enter through the space number of banknotes * denomination of banknotes in rubles, \n" +
                "for example 2*500 5*1000");
        menuItems.put(31, "You have credited your card to %s rubles");
        menuItems.put(32, "Invalid input, banknotes not recognized: %s \n" +
                "Example of correct input: 1*100 2*200 3*500 4*1000 5*2000 6*5000");

        //W
        menuItems.put(40, "Enter the amount you want to withdraw from your card account, but not less than 100 rubles, not more than 999900");
        menuItems.put(41, "This amount cannot be issued");
        menuItems.put(42, "You got the following: %s rubles");

        //K
        menuItems.put(50, "Your card balance: %s rubles");

        //R
        menuItems.put(90, "Goodbye!\n" + dashLine);
        menuItems.put(92, "Your card has been returned after 8 seconds waiting!\n" + dashLine);
    }

    public static void displayMenuItem(int itemNumber, String... args) {
        System.out.println(String.format(menuItems.get(itemNumber), args));
    }
}
