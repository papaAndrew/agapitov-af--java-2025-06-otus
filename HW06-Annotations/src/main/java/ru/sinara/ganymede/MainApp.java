package ru.sinara.ganymede;

import ru.sinara.ganymede.engine.GanymedeTester;

// @SuppressWarnings("java:S112")
public class MainApp {

    public static void main(String[] args) {
        // Let it called with parameter
        String classRef = "ru.sinara.ganymede.demo.FailTestMethodTest";

        GanymedeTester tester = GanymedeTester.getInstance();
        try {
            tester.runTests(classRef);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
