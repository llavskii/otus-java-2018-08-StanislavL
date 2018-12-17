package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.AtmContext;

public class WaitingActiveStatusState extends AtmState {
    @Override
    public void doAction(AtmContext context) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
