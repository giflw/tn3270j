package com.itquasar.multiverse.tn3270j.status;

/**
 * Some actions generate output;
 * some may delay completion until the certain external events occur, such as the host unlocking the keyboard.
 * The completion of every command is marked by a two-line message.
 * The first line is the current status of the emulator.
 * If the command is successful, the second line is the string "ok"; otherwise it is the string "error".
 *
 * @see http://x3270.bgp.nu/Unix/x3270-script.html#Description
 */
public enum Status implements StatusCode {
    Ok("ok"),
    Error("error");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return this.code;
    }

    public static Status from(com.j3270.base.Status.Code other) {
        return Status.valueOf(other.name());
    }
}
