package ru.otus.processor.homework;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class OddTimeProcessor implements Processor {

    @Override
    public Message process(Message message) {
        boolean isEven = LocalDateTime.now().getSecond() % 2 == 0;
        if (isEven) {
            throw new InputMismatchException("Process unavailable");
        }

        return message;
    }
}
