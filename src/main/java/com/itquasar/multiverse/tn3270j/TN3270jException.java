package com.itquasar.multiverse.tn3270j;

public class TN3270jException extends RuntimeException {
    public TN3270jException() {
    }

    public TN3270jException(String message) {
        super(message);
    }

    public TN3270jException(String message, Throwable cause) {
        super(message, cause);
    }

    public TN3270jException(Throwable cause) {
        super(cause);
    }

    public TN3270jException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
