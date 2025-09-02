package ru.otus.handler;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.OddTimeProcessor;

public class OddTimeProcessorTest {

    @Test
    @DisplayName("if processor was called in odd second, so good. Else an Exception will raised")
    public void testProcess() {
        int attempts = 8;

        for (int i = 0; i < attempts; i++) {
            boolean odd = OddTimeProcessorTest.getSecond() % 2 != 0;
            if (odd) {
                assertThatNoException()
                        .isThrownBy(() -> new OddTimeProcessor(OddTimeProcessorTest::getSecond).process(new Message.Builder(0).build()));
            } else {
                assertThatException().isThrownBy(() -> new OddTimeProcessor(OddTimeProcessorTest::getSecond).process(new Message.Builder(0).build()));
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static long getSecond() {
        return LocalDateTime.now().getSecond();
    }
}

