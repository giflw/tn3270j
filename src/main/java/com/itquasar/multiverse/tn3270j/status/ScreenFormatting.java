package com.itquasar.multiverse.tn3270j.status;

public enum ScreenFormatting implements StatusCode {
    Formatted("F"), Unformatted("U");

    private final String code;

    ScreenFormatting(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

}
