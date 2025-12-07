package ru.otus;

public class SomethingWrongException extends RuntimeException {
    public SomethingWrongException(String message) {
        super(message);
    }
}
