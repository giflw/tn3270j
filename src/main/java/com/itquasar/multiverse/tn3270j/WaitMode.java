package com.itquasar.multiverse.tn3270j;

/**
 * @author Guilherme I F L Weizenmann
 * @see https://x3270.miraheze.org/wiki/Wait()_action
 */
public enum WaitMode {
    /**
     * Wait until the emulator is in 3270 mode:
     * <br />
     * <code>Wait(3270mode)</code>
     */
    Mode3270("3270mode"),
    /**
     * Wait until the host session disconnects.
     */
    Disconnect,
    /**
     * Wait until the host creates at least one modifiable field on the screen.
     */
    InputField,
    /**
     * Wait until the emulator is in NVT mode.
     */
    NVTMode,
    /**
     * Wait until the host modifies the screen. See Output synchronization, below, for details.
     * <p>
     * The Wait(output) action synchronizes with the screen-reading actions.
     * The full definition of Wait(output) is to wait for the host to modify
     * the screen since the last invocation of a screen-reading action.
     * This can be used by a script to minimize the need to poll the screen for changes.
     * For example, if the host periodically updates the screen with new information,
     * the script can just call Wait(output), then use a screen-reading action to get the new data.
     * <p>
     * </p>
     * Note that if the host disconnects while Wait(output) is pending, Wait(output) will return,
     * but it will not fail. This gives a script a chance to call a screen-reading action to read
     * whatever might have changed on the screen while the host was disconnecting.
     * A subsequent call to Wait(output) will fail, because there is no host session and no pending data.
     * This makes looping through host output very simple.
     * Here is a pseudo-code example that is guaranteed to capture all of the host data and exit when the session disconnects:
     * <br />
     * <code>
     * while (Wait(output)) { <br />
     * Ascii() <br />
     * }
     * </code>
     * </p>
     * <p>
     * Wait for the host to update the screen, or for 10 seconds to elapse:
     * <br />
     * <code>Wait(10,output)</code>
     * </p>
     */
    Output,
    /**
     * Wait for the specified timeout.
     * <p>
     * Wait for half a second:
     * <br />
     * <code>Wait(0.5,seconds)</code>
     * </p>
     */
    Seconds,
    /**
     * Wait until the host unlocks the keyboard.
     */
    Unlock;

    private final String code;

    WaitMode() {
        this.code = this.name().toLowerCase();
    }

    WaitMode(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    @Override
    public String toString() {
        return code();
    }

    public static WaitMode waitMode(String name) {
        for (WaitMode waitMode : values()) {
            if (waitMode.code().equalsIgnoreCase(name)) {
                return waitMode;
            }
        }
        throw new TN3270jException("WaitMode " + name + " does not exists in x3270 or is not supported.");
    }
}
