package com.bendeguz.usedcar.exception;

public class CommonServiceException extends RuntimeException {

    public CommonServiceException() {
        super();
    }

    public CommonServiceException(String message) {
        super(message);
    }

    public CommonServiceException(String message, Throwable cause) {
        super(message, cause);
    }


    public CommonServiceException(Throwable cause) {
        super(cause);
    }
}
