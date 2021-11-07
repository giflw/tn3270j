package com.itquasar.multiverse.tn3270j.status;

public final class CommandExecutionTime implements StatusCode {

    private static final String FORMAT = "%.3f";

    /**
     * Command execution time in seconds.
     */
    private final float time;

    private final boolean hostResponded;

    private final String code;

    /**
     *
     * @param time Command execution time in seconds.
     */
    private CommandExecutionTime(float time) {
        this(time, true, String.format(FORMAT, time));
    }

    public CommandExecutionTime(float time, boolean hostResponded, String code) {
        this.time = time;
        this.hostResponded = hostResponded;
        this.code = String.format(FORMAT, time);
    }

    public boolean hostResponded() {
        return this.hostResponded;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public static CommandExecutionTime from(com.j3270.base.Status.CommandExecutionTime other) {
        return new CommandExecutionTime(Float.valueOf(other.code()));
    }
}
