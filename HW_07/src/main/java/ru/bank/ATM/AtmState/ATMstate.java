package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.ATMcontext;
import ru.bank.ATM.ConsoleInputReadTask;

import java.util.concurrent.*;

public abstract class ATMstate {
    public abstract void doAction(ATMcontext context);

    protected String getInputLineFromClient() {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = new ConsoleInputReadTask();
        Future<String> future = executor.submit(callable);
        while (this.getClass() != InputPinCodeState.class || System.currentTimeMillis() - start < 8_000) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (future.isDone()) {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        future.cancel(true);
        executor.shutdown();
        return null;

    }
}
