package ru.aaf.finshop.datacenter.exception;

public class GrpcServerException extends RuntimeException {

    public GrpcServerException(Throwable cause) {
        super(cause);
    }

    public GrpcServerException(String message) {
        super(message);
    }

    public GrpcServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
