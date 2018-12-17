package ru.bank.bankCard;

import java.util.Random;
import java.util.regex.Pattern;

public class BankCard {
    private String number;
    private String PIN;
    private int balance;
    private static Pattern NUMBER_PATTERN = Pattern.compile("^[1-9]\\d{3} *\\d{4} *\\d{4} *\\d{4}$");
    private static Pattern PIN_PATTERN = Pattern.compile("^\\d{4}$");
    private static Random random = new Random();


    public BankCard(String number, String PIN) {
        this.number = number;
        this.PIN = PIN;
        this.balance = getRandomStartBalance();
    }

    public String getNumber() {
        return number;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPIN() {
        return PIN;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    public static boolean validateCardNumber(String cardNumber) {
        return NUMBER_PATTERN.matcher(cardNumber).matches();
    }

    public static boolean validatePINnumber(String PINnumber) {
        return PIN_PATTERN.matcher(PINnumber).matches();
    }

    public static int getRandomStartBalance() {
        return random.nextInt(10) + random.nextInt(500) + random.nextInt(1000) * 1000;
    }

    public void replenishment(int summ) {
        balance += summ;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankCard bankCard = (BankCard) o;

        return number.equals(bankCard.number);
    }
}
