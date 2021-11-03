package com.itquasar.multiverse.tn3270j.impl;

import com.itquasar.multiverse.tn3270j.TN3270jImplementationProvider;
import com.itquasar.multiverse.tn3270j.WaitMode;

public class J3270From3270Provider implements TN3270jImplementationProvider<J3270From3270> {

    @Override
    public String getImplementationId() {
        return J3270From3270.IMPLEMENTATION_ID;
    }

    @Override
    public Class<J3270From3270> getType() {
        return J3270From3270.class;
    }

    @Override
    public J3270From3270 provide(ProcessBuilder processBuilder, WaitMode defaultWaitMode) {
        return new J3270From3270(processBuilder, defaultWaitMode);
    }
}
