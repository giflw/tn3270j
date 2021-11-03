package com.itquasar.multiverse.tn3270j;

public interface TN3270j extends AutoCloseable {

    /**
     * @return Id string for the underlying implementation.
     */
    String getImplementationId();

    /**
     * @return Default wait mode. Fallbacks to {@link WaitMode#Output}.
     */
    WaitMode getDefaultWaitMode();

    void open(String url);

    void close();

    /**
     * @return Screenshot of current screen on terminal.
     */
    String screenshot();

    /**
     * Trimmed value found.
     *
     * @param row    Row starting with 1.
     * @param col    Column starting with 1.
     * @param length Number of chars to read. If 0, read entire line starting in <code>col</code>.
     * @return Read content.
     */
    String read(int row, int col, int length);

    /**
     * @param row     Row starting with 1.
     * @param col     Column starting with 1.
     * @param content Content to write.
     */
    void write(int row, int col, String content);

    /**
     * Writes in the actual cursor position.
     * @param content Content to write.
     */
    default void write(String content) {
        write(0, 0, content);
    }

    /**
     * Send PF key (F1, F2, ...F12).
     *
     * @param key PF key number.
     */
    default void send(int key) {
        send("F" + key);
    }

    /**
     * Send enter, F?, etc.
     *
     * @param key PF key number.
     */
    void send(String key);

    /**
     * Wait for specific event according to wait mode.
     *
     * @param waitMode x3270 wait mode.
     */
    default void wait(WaitMode waitMode) {
        this.wait(-1, waitMode);
    }


    /**
     * Wait for given time in seconds using default wait mode {@link #getDefaultWaitMode()}.
     *
     * @param seconds Seconds to wait as float using {@link Number#floatValue()} .
     */
    default void wait(Number seconds) {
        this.wait(seconds.floatValue(), getDefaultWaitMode());
    }


    /**
     * Wait for given seconds or for the given event with seconds timeout.
     * If seconds equals 0, waits for event with no timeout.
     *
     * @param seconds  Seconds converted to float value using {@link Number#floatValue()}.
     * @param waitMode x3270 wait mode.
     */
    void wait(Number seconds, WaitMode waitMode);

}
