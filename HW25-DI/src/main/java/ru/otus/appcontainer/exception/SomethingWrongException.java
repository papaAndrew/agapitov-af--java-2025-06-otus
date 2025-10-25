package ru.otus.appcontainer.exception;

public class SomethingWrongException extends RuntimeException {
    public SomethingWrongException(String message) {
        super(message);
    }

    public SomethingWrongException(String message, Throwable cause) {
        super(message, cause);
    }
}
