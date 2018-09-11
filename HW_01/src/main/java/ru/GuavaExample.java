package ru;

import com.google.common.io.CharStreams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GuavaExample {
    public static void main(String[] args) throws IOException {
        System.out.println("Введите путь к файлу");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        FileReader fileReader = new FileReader(bufferedReader.readLine());
        String result = CharStreams.toString(fileReader);
        System.out.println(result);
        fileReader.close();
        bufferedReader.close();
    }
}

