package com.itquasar.multiverse.tn3270j;

import java.util.concurrent.Callable;

public interface Util {

    static <R> R sneakyThrow(Callable<R> callable) {
        try {
            return callable.call();
        } catch (Throwable ex) {
            throw new TN3270jException(ex);
        }
    }
}
