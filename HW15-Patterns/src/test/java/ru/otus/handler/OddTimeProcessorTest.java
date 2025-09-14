package ru.otus.handler;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.OddTimeProcessor;
import ru.otus.processor.homework.TimeSecondProvider;

public class OddTimeProcessorTest {

    @Test
    @DisplayName("if processor called in odd second, so good. Else an Exception will be raised")
    public void testProcess() {

        TimeSecondProvider secondProvider = mock(TimeSecondProvider.class);
        when(secondProvider.getSecond()).thenReturn(1L);

        assertThatNoException()
                .isThrownBy(() -> new OddTimeProcessor(secondProvider).process(new Message.Builder(0).build()));

        when(secondProvider.getSecond()).thenReturn(2L);

        assertThatException()
                .isThrownBy(() -> new OddTimeProcessor(secondProvider).process(new Message.Builder(0).build()));
    }

    private static long getSecond() {
        return LocalDateTime.now().getSecond();
    }
}
