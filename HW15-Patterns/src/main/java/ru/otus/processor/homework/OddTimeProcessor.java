package ru.otus.processor.homework;

import java.util.InputMismatchException;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class OddTimeProcessor implements Processor {

    private final TimeSecondProvider oddSecondProvider;

    public OddTimeProcessor(TimeSecondProvider oddSecondProvider) {
        this.oddSecondProvider = oddSecondProvider;
    }

    @Override
    public Message process(Message message) {
        boolean even = oddSecondProvider.getSecond() % 2 == 0;
        if (even) {
            throw new InputMismatchException("Process unavailable");
        }

        return message;
    }
}
