package ru.otus.crm.excheption;

public class SomethingWrongException extends RuntimeException {

    public SomethingWrongException(Throwable cause) {
        super(cause);
    }

    public SomethingWrongException(String message) {
        super(message);
    }

    public SomethingWrongException(String message, Throwable cause) {
        super(message, cause);
    }
}
