package ru.sinara.ganymede;

import ru.sinara.ganymede.engine.GanymedeTester;
import ru.sinara.ganymede.engine.Summary;

public class MainApp {

    public static void main(String[] args) {
        // Let it called with parameter
        String classRef = "ru.sinara.ganymede.demo.TestClass";

        GanymedeTester tester = GanymedeTester.getInstance(classRef);
        tester.runTests();
    }
}
