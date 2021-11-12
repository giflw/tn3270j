package com.itquasar.multiverse.tn3270j.status;

/**
 * If the keyboard is unlocked, the letter U.
 * If the keyboard is locked waiting for a response from the host, or if not connected to a host, the letter L.
 * If the keyboard is locked because of an operator error (field overflow, protected field, etc.), the letter E.
 *
 * @see <a href="http://x3270.bgp.nu/Unix/x3270-script.html#Status-Format">Status-Format</a>
 */
public enum KeyboardState implements StatusCode {
    Unlocked("U"),
    Locked("L"),
    Error("E");

    private final String code;

    KeyboardState(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

}
