package com.itquasar.multiverse.tn3270j.status;

public enum FieldProtection implements StatusCode {
    Protected("P"), Unprotected("U");

    private final String code;

    FieldProtection(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
