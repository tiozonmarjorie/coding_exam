package org.example;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleOutputCapturer {
    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public static void captureConsoleOutput() {
        System.setOut(new PrintStream(outputStream));
    }

    public static String getCapturedConsoleOutput() {
        return outputStream.toString();
    }
}