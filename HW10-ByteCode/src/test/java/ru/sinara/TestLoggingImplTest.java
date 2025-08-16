package ru.sinara;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

public class TestLoggingImplTest {
    private static final Random random = Random.from(RandomGenerator.getDefault());
    private static final Logger logger = (Logger) LoggerFactory.getLogger(TestLogging.class.getPackageName());
    private static final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

    static {
        logger.addAppender(listAppender);
        listAppender.start();
    }
    //    private ListAppender<ILoggingEvent> listAppender;
    private TestLogging testLogging;

    @BeforeEach
    public void createTestLogging() {
        testLogging = Ioc.createTestLogging();
    }

    @Test
    @DisplayName("Method annotated @Log does log out the specified message")
    public void testCalculationAnnotated() {

        for (int i = 0; i < 3; i++) {
            int param = random.nextInt();
            testLogging.calculate(param);
            final String msg = "executed method: calculate, param: " + param;
            boolean found = listAppender.list.stream()
                    .anyMatch(event -> event.getFormattedMessage().contains(msg));

            assertTrue(found, "Expected log message found");
        }
    }

    @Test
    @DisplayName("Method annotated @Log does log out the specified message")
    public void testMultiParamCalculationAnnotated() {
        for (int i = 0; i < 3; i++) {
            int param1 = random.nextInt();
            String param2 = UUID.randomUUID().toString();
            testLogging.calculate(param1, param2);
            final String msg = "executed method: calculate, param: " + param1 + ", " + param2;
            boolean found = listAppender.list.stream()
                    .anyMatch(event -> event.getFormattedMessage().contains(msg));

            assertTrue(found, "Expected log message found");
        }
    }

    @Test
    @DisplayName("Method not annotated @Log doesn't log out the specified message")
    public void testCalculationNotAnnotated() {
        String param = UUID.randomUUID().toString();
        testLogging.calculate(param);
        final String msg = "executed method: calculate, param: " + param;

        boolean found = listAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage().contains(msg));

        assertFalse(found, "Expected log message not found");
    }
}
