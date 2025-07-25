package ru.sinara.ganymede;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sinara.ganymede.annotations.After;
import ru.sinara.ganymede.annotations.Before;

public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        // Let it called with parameter
        String classRef = "ru.sinara.ganymede.demo.TestClass";

        Class<?> testClass;
        try {
            testClass = Class.forName(classRef);
        } catch (ClassNotFoundException e) {
            LOG.error("Class not found", e);
            return;
        }

        List<Method> methodsBefore = collectMethodsAnnotated(testClass, Before.class);
        LOG.info("MethodsBefore: {}", methodsBefore);
        //        runTests(testClass);

        List<Method> methodsAfter = collectMethodsAnnotated(testClass, After.class);
        LOG.info("MethodsAfter: {}", methodsAfter);
    }

    private static List<Method> collectMethodsAnnotated(Class<?> testClass, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }
}
