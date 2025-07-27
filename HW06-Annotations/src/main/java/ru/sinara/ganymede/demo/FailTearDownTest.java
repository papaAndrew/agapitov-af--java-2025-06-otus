package ru.sinara.ganymede.demo;

import ru.sinara.ganymede.annotations.After;
import ru.sinara.ganymede.annotations.Before;
import ru.sinara.ganymede.annotations.Test;

@SuppressWarnings({"java:S106", "java:S112"})
public class FailTearDownTest {

    @Before
    void setUp1() {
        System.out.println("Set Up #1 processing");
    }

    @Before
    void setUp2() {
        System.out.println("Set Up #2 processing");
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
        throw new RuntimeException("Tear Down Error");
    }
}
