package ru.bank.ATM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ConsoleInputReadTask implements Callable<String> {
    public String call() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        do {
            try {
                while (!br.ready()) {
                    Thread.sleep(10);
                }
                input = br.readLine();
            } catch (InterruptedException e) {
                return null;
            }
        } while ("".equals(input));
        return input;
    }
}