package ru.sinara.ganymede.engine;

import static ru.otus.reflection.ReflectionHelper.callMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.reflection.ReflectionHelper;
import ru.sinara.ganymede.annotations.After;
import ru.sinara.ganymede.annotations.Before;
import ru.sinara.ganymede.annotations.Test;

public class GanymedeTester {
    private static final Logger LOG = LoggerFactory.getLogger(GanymedeTester.class);

    public static GanymedeTester getInstance() {
        return new GanymedeTester();
    }

    public Summary runTests(Class<?> testingClass) {

        LOG.info("Run Tests of class `{}`", testingClass.getName());

        List<Method> methodsBefore = collectMethodsAnnotated(testingClass, Before.class);
        List<Method> methodsTest = collectMethodsAnnotated(testingClass, Test.class);
        List<Method> methodsAfter = collectMethodsAnnotated(testingClass, After.class);

        Summary summary = Summary.getInstance(methodsTest.size());
        for (Method method : methodsTest) {
            // create object
            var app = ReflectionHelper.instantiate(testingClass);

            try {
                doBeforeEach(app, methodsBefore);
                callMethod(app, method.getName());
                LOG.info("Test executed `{}.{}`", app, method.getName());
                doAfterEach(app, methodsAfter);

                summary.addPassed(1);
            } catch (Exception e) {
                LOG.warn("Test failed `{}.{}`", app, method.getName());
                summary.addFailed(1);
            }
        }
        LOG.info("Tests are over.\n=== Summary ===\n{}", summary);

        return summary;
    }

    public Summary runTests(String classRef) throws ClassNotFoundException {
        return runTests(Class.forName(classRef));
    }

    private void doBeforeEach(Object app, List<Method> methods) {
        for (Method method : methods) {
            callMethod(app, method.getName());
            LOG.info("BeforeEach executed `{}.{}`", app, method.getName());
        }
    }

    private void doAfterEach(Object app, List<Method> methods) {
        for (Method method : methods) {
            callMethod(app, method.getName());
            LOG.info("AfterEach executed `{}.{}`", app, method.getName());
        }
    }

    private static List<Method> collectMethodsAnnotated(
            Class<?> hostClass, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(hostClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }
}
