package ru.sinara;

public class Main {
    public static void main(String[] args) {

        TestLogging testLogging = Ioc.patchTestLogging(new TestLoggingImpl());
        testLogging.calculate(6);
    }
}
