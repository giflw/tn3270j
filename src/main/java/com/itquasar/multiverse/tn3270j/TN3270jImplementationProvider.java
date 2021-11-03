package com.itquasar.multiverse.tn3270j;

import static com.itquasar.multiverse.tn3270j.Util.sneakyThrow;

public interface TN3270jImplementationProvider<T extends TN3270j> {

    default String getImplementationId() {
        return sneakyThrow(() -> getType().getDeclaredField("IMPLEMENTATION_ID").get(null).toString());
    }

    Class<T> getType();

    T provide(ProcessBuilder processBuilder, WaitMode defaultWaitMode);
}
