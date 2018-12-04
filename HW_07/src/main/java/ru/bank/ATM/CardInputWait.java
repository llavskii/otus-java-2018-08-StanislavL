package ru.bank.ATM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

class CardInputWait implements Runnable {
    private volatile String inputLine = "";

    CardInputWait() {
    }

    @Override
    public void run() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String tmp = null;
//        try {
//            tmp = reader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.nextLine();
        synchronized (this) {
            System.out.println("в блоке synch");
            inputLine = tmp;
        }
    }

    public String getInputLine() {
//        if (inputLine.contains("1111")) System.out.println("содержит 1111");
//        if (!inputLine.isEmpty()) System.out.println("перед возвращением: " + inputLine);
        return inputLine;
    }
}
