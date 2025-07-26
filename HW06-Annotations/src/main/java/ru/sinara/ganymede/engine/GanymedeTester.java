package ru.sinara.ganymede.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.reflection.ReflectionHelper;
import ru.sinara.ganymede.MainApp;
import ru.sinara.ganymede.annotations.After;
import ru.sinara.ganymede.annotations.Before;
import ru.sinara.ganymede.annotations.Test;

import static ru.otus.reflection.ReflectionHelper.callMethod;
import static ru.otus.reflection.ReflectionHelper.instantiate;

public class GanymedeTester {
    private static final Logger LOG = LoggerFactory.getLogger(GanymedeTester.class);

    private final Class<?> testingClass;
    /**
     *
     * @param testingClass - class to test
     */
    public GanymedeTester(Class<?> testingClass) {
        this.testingClass = testingClass;
    }


    /**
    /**
     *
     * @param classRef - class name
     * @return  instance of GanymedeTester
     */
    public static GanymedeTester getInstance(String classRef) {
        Class<?> testClass;
        try {
            testClass = Class.forName(classRef);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class " + classRef + " not found");
        }
        return instantiate(GanymedeTester.class, testClass);
    }

    public Summary runTests() {
        List<Method> methodsTest = collectMethodsAnnotated(testingClass, Test.class);
        Summary summary = Summary.getInstance(methodsTest.size());

        for (Method method : methodsTest) {
            // create object
            var app = ReflectionHelper.instantiate(testingClass);

            try {
                doBeforeEach(app);
                callMethod(app, method.getName());
                doAfterEach(app);

                summary.addPassed(1);
            } catch (Exception e) {
                summary.addFailed(1);
            }
        }

        return summary;
    }

    private void doBeforeEach(Object app) {
        List<Method> methods = collectMethodsAnnotated(testingClass, Before.class);

        for (Method method : methods) {
            callMethod(app, method.getName());
            LOG.info("Before Method executed: {}", method.getName());
        }
    }

    private void doAfterEach(Object app) {
        List<Method> methods = collectMethodsAnnotated(testingClass, After.class);

        for (Method method : methods) {
            callMethod(app, method.getName());
            LOG.info("After Method executed: {}", method.getName());
        }
    }

    private static List<Method> collectMethodsAnnotated(
            Class<?> testClass, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

}
