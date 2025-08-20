package ru.sinara;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoggingImpl implements TestLogging {
    private static final Logger log = LoggerFactory.getLogger(TestLoggingImpl.class);

    @Log
    @Override
    public void calculate(int param) {
        log.info("Some calculations processing with int parameter `{}`", param);
    }

    @Override
    public void calculate(String param) {
        log.info("Some calculations processing with String parameter `{}`", param);
    }

    @Log
    @Override
    public void calculate(int param1, String param2) {
        log.info("Some calculations processing with two parameters `{}, {}`", param1, param2);
    }
}
