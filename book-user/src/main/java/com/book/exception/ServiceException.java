package com.book.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -47L;

    public ServiceException() {
        super();
    }
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
