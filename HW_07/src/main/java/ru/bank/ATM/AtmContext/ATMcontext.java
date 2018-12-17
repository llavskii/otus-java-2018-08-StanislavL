package ru.bank.ATM.AtmContext;

import ru.bank.ATM.AtmState.ATMstate;
import ru.bank.ATM.RuBankATM;
import ru.bank.clients.Client;

public class ATMcontext {
    private ATMstate state;
    private RuBankATM ATM;
    private Client currentClient = null;
    private String currentClientCardNumber = null;
    private boolean isNewClient;

    public void nullifyCurrentClientData() {
        this.currentClient = null;
        this.currentClientCardNumber = null;

    }

    public ATMcontext(ATMstate state) {
        this.state = state;
    }

    public void setState(ATMstate state) {
        this.state = state;
    }

    public ATMstate getState() {
        return state;
    }

    public void doAction() {
        state.doAction(this);
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

    public String getCurrentClientCardNumber() {
        return currentClientCardNumber;
    }

    public void setCurrentClientCardNumber(String currentClientCardNumber) {
        this.currentClientCardNumber = currentClientCardNumber;
    }

    public boolean isNewClient() {
        return isNewClient;
    }

    public void setNewClient(boolean newClient) {
        isNewClient = newClient;
    }

    public void setATM(RuBankATM ATM) {
        this.ATM = ATM;
    }

    public RuBankATM getATM() {
        return ATM;
    }
}
