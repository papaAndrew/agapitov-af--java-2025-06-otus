package ru.sinara;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ioc {
    private static final Logger log = LoggerFactory.getLogger(Ioc.class);

    static {
    }

    static TestLogging patch(TestLogging testLogging) {
        InvocationHandler handler = new SpecInvocationHandler(testLogging);

        return (TestLogging)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[] {TestLogging.class}, handler);
    }

    static class SpecInvocationHandler implements InvocationHandler {
        private final Logger log;

        private final TestLogging handledObject;

        private final Set<String> annotatedSignatures;

        SpecInvocationHandler(TestLogging testLogging) {
            this.handledObject = testLogging;
            this.log = LoggerFactory.getLogger(testLogging.getClass());
            this.annotatedSignatures = Arrays.stream(testLogging.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .map(SpecInvocationHandler::getMethodSignature)
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodSignature = getMethodSignature(method);
            if (annotatedSignatures.contains(methodSignature)) {
                String argList = String.join(
                        ", ",
                        Arrays.stream(args)
                                .map(obj -> obj != null ? obj.toString() : "null")
                                .toArray(String[]::new));
                log.info("executed method: {}, param: {}", method.getName(), argList);
            }
            return method.invoke(handledObject, args);
        }

        @Override
        public String toString() {
            return String.format("%s{handledObject=%s}", SpecInvocationHandler.class.getSimpleName(), handledObject);
        }

        private static String getMethodSignature(Method method) {
            return method.getName() + Arrays.toString(method.getParameterTypes());
        }
    }
}
