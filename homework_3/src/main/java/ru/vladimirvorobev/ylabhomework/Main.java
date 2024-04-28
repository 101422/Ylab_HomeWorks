package ru.vladimirvorobev.ylabhomework;

import ru.vladimirvorobev.ylabhomework.in.ConsoleInput;

import java.io.IOException;

//@Loggable
public class Main {
   // @AuthorizationLoggable
   //@Loggable
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
        ConsoleInput consoleInput = new ConsoleInput();

        consoleInput.input();
    }

}