package ru.sinara;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ioc {
    private static final Logger log = LoggerFactory.getLogger(Ioc.class);

    static TestLogging createTestLogging() {
        InvocationHandler handler = new SpecInvocationHandler(new TestLoggingImpl());

        return (TestLogging)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[] {TestLogging.class}, handler);
    }

    static class SpecInvocationHandler implements InvocationHandler {
        private static final Logger log = LoggerFactory.getLogger(TestLoggingImpl.class);

        private final TestLogging handledObject;

        SpecInvocationHandler(TestLogging testLogging) {
            this.handledObject = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isMethodAnnotatedWithLog(method)) {
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

        private boolean isMethodAnnotatedWithLog(Method method) throws NoSuchMethodException {
            Method handledMethod = handledObject.getClass().getMethod(method.getName(), method.getParameterTypes());
            return handledMethod.getDeclaredAnnotation(Log.class) != null;
        }
    }
}
