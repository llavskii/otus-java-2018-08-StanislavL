package ru.bank.ATM.AtmContext;

import ru.bank.ATM.AtmState.AtmState;
import ru.bank.ATM.RuBankAtm;
import ru.bank.clients.Client;

public class AtmContext {
    private AtmState state;
    private RuBankAtm ATM;
    private Client currentClient = null;
    private String currentClientCardNumber = null;
    private boolean isNewClient;

    public void nullifyCurrentClientData() {
        this.currentClient = null;
        this.currentClientCardNumber = null;

    }

    public AtmContext(AtmState state) {
        this.state = state;
    }

    public void setState(AtmState state) {
        this.state = state;
    }

    public AtmState getState() {
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

    public void setATM(RuBankAtm ATM) {
        this.ATM = ATM;
    }

    public RuBankAtm getATM() {
        return ATM;
    }
}
