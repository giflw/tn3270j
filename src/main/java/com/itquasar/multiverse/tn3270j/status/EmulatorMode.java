package com.itquasar.multiverse.tn3270j.status;

/**
 * If connected in 3270 mode, the letter I.
 * If connected in NVT line mode, the letter L.
 * If connected in NVT character mode, the letter C.
 * If connected in unnegotiated mode (no BIND active from the host), the letter P.
 * If not connected, the letter N.
 *
 * @see <a href="http://x3270.bgp.nu/Unix/x3270-script.html#Status-Format">Status-Format</a>
 */
public enum EmulatorMode implements StatusCode {
    _3270("I"),
    Line("L"),
    Character("C"),
    Unnegotiated("P"),
    NotConnected("N");

    private final String code;

    EmulatorMode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
