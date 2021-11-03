package com.itquasar.multiverse.tn3270j;

import com.itquasar.multiverse.tn3270j.impl.J3270From3270;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface TN3270jFactory {

    enum KnownImplementation {
        J3270_3270(J3270From3270.IMPLEMENTATION_ID);

        private final String implementationId;

        KnownImplementation(String implementationId) {
            this.implementationId = implementationId;
        }

        public String getImplementationId() {
            return implementationId;
        }

        public KnownImplementation from(String implementationId) {
            for (KnownImplementation knownImplementation : values()) {
                if (knownImplementation.getImplementationId().equals(implementationId)) {
                    return knownImplementation;
                }
            }
            throw new TN3270jException("Implementation id " + implementationId + " is unknown.");
        }
    }

    static TN3270jImplementationProvider create(KnownImplementation knownImplementation) {
        return create(knownImplementation.getImplementationId());
    }

    static TN3270jImplementationProvider create(String implementationId) {
        Iterator<TN3270jImplementationProvider> iterator = getProviderIterator();
        while (iterator.hasNext()) {
            TN3270jImplementationProvider next = iterator.next();
            if (next.getImplementationId().equalsIgnoreCase(implementationId)) {
                return next;
            }
        }
        throw new TN3270jException("Provider with implementation id " + implementationId + " not found.");
    }

    static TN3270j create(KnownImplementation knownImplementation, ProcessBuilder processBuilder, WaitMode defaultWaitMode) {
        return create(knownImplementation.getImplementationId(), processBuilder, defaultWaitMode);
    }

    static TN3270j create(String implementationId, ProcessBuilder processBuilder, WaitMode defaultWaitMode) {
        return create(implementationId).provide(processBuilder, defaultWaitMode);
    }

    static Iterator<TN3270jImplementationProvider> getProviderIterator() {
        ServiceLoader<TN3270jImplementationProvider> loader = ServiceLoader.load(TN3270jImplementationProvider.class);
        Iterator<TN3270jImplementationProvider> iterator = loader.iterator();
        return iterator;
    }

}
