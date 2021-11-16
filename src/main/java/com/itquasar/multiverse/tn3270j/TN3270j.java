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

    void start();

    void connect(String url);

    void open(String url);

    boolean started();

    boolean connected();

    TN3270Status status();

    void disconnect();

    void stop();

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
     * @param row     Row starting with 1.
     * @param col     Column starting with 1.
     * @param length  Max length accepted by field. Throws exception if {@code content.length() > length}.
     * @param content Content to write.
     */
    default void write(int row, int col, int length, String content) {
        this.write(row, col, length, content, null);
    }


    /**
     * @param row     Row starting with 1.
     * @param col     Column starting with 1.
     * @param length  Max length accepted by field. Throws exception if {@code content.length() > length}.
     * @param content Content to write.
     * @param fieldName Name of field to return on length error.
     */
    default void write(int row, int col, int length, String content, String fieldName) {
        if (content.length() > length) {
            String fieldMessage = fieldName != null ? " on field " + fieldName : "";
            throw new IllegalArgumentException("Content [" + content + "]" +fieldMessage+ " has length greater than " + length);
        }
        this.write(row, col, content);
    }

    /**
     * Writes in the actual cursor position.
     *
     * @param contents Content to write.
     */
    default void write(String... contents) {
        for (String content : contents) {
            this.write(0, 0, content);
        }
    }

    /**
     * Writes in the actual cursor position.
     *
     * @param contents Content to write.
     */
    default void write(Number... contents) {
        for (Number content : contents) {
            this.write(0, 0, content.toString());
        }
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
        this.wait(0, waitMode);
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
     * <ul>
     * <li>If seconds equals 0, waits for event with no timeout.</li>
     * <li>If seconds equals 0 and wait mode is {@link WaitMode#Seconds}, waits 0 seconds. </li>
     * </ul>
     *
     * @param seconds  Seconds converted to float value using {@link Number#floatValue()}.
     * @param waitMode x3270 wait mode.
     */
    void wait(Number seconds, WaitMode waitMode);


    /**
     * Move cursor to given absolute position.
     *
     * @param row Row starting with 1.
     * @param col Column starting with 1.
     */
    void move(int row, int col);

}
