package ru.sinara.ganymede.demo;

import ru.sinara.ganymede.annotations.After;
import ru.sinara.ganymede.annotations.Before;
import ru.sinara.ganymede.annotations.Test;

public class FailSetupTest {

    @Before
    void setUp1() {
        System.out.println("Set Up #1 processing");
    }

    @Before
    void setUp2() {
        throw new RuntimeException("Set Up #2 Error");
    }

    @Test
    void test1() {
        System.out.println("Test #1 method");
    }

    @Test
    void test2() {
        System.out.println("Test #2 method");
    }

    @After
    void tearDown() {

        System.out.println("Test Tear down");
    }
}
