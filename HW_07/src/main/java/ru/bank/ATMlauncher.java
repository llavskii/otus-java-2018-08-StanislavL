package ru.bank;

import ru.bank.ATM.RuBankATM;

public class ATMlauncher {
    public static void main(String[] args) {
        new Thread(new RuBankATM(10)).start();
    }
}

