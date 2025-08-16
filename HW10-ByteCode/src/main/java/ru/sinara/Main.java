package ru.sinara;

public class Main {
    public static void main(String[] args) {

        TestLogging testLogging = Ioc.createTestLogging();
        testLogging.calculate(6);
    }
}
