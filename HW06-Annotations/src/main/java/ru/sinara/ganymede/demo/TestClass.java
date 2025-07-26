package ru.sinara.ganymede.demo;

import ru.sinara.ganymede.annotations.After;
import ru.sinara.ganymede.annotations.Before;
import ru.sinara.ganymede.annotations.Test;

public class TestClass {

    @Before
    void setUp1() {
        System.out.println("Ganymede test set up #1");
    }

    @Before
    void setUp2() {
        System.out.println("Ganymede test set up #2");
    }

    @Test
    void test1() {
        System.out.println("Ganymede test1 method");
    }

    @Test
    void test2() {
        System.out.println("Ganymede test2 method");
    }

    @After
    void tearDown() {

        System.out.println("Ganymede test Tear down");
    }
}
