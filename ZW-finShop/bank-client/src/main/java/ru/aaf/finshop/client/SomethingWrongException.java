package ru.aaf.finshop.client;

public class SomethingWrongException extends RuntimeException {
    public SomethingWrongException(String message) {
        super(message);
    }
}
