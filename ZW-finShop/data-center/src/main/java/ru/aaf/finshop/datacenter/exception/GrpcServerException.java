package ru.aaf.finshop.datacenter.exception;

public class GrpcServerException extends RuntimeException {
    public GrpcServerException(String message) {
        super(message);
    }

    public GrpcServerException(Throwable cause) {
        super(cause);
    }
}
