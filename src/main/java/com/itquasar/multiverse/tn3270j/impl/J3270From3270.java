package com.itquasar.multiverse.tn3270j.impl;

import com.itquasar.multiverse.tn3270j.TN3270Status;
import com.itquasar.multiverse.tn3270j.TN3270j;
import com.itquasar.multiverse.tn3270j.TN3270jException;
import com.itquasar.multiverse.tn3270j.WaitMode;
import com.j3270.base.J3270;
import com.j3270.base.ProcessPiper;
import com.j3270.base.Timeout;

import java.util.concurrent.TimeUnit;

import static com.itquasar.multiverse.tn3270j.Util.sneakyThrow;

public final class J3270From3270 implements TN3270j {

    public static final String IMPLEMENTATION_ID = "3270/j3270";

    private final ProcessBuilder processBuilder;
    private J3270 j3270;

    private final WaitMode defaultWaitMode;

    public J3270From3270(ProcessBuilder processBuilder, WaitMode defaultWaitMode) {
        this.processBuilder = processBuilder;
        this.defaultWaitMode = defaultWaitMode;
    }

    @Override
    public String getImplementationId() {
        return IMPLEMENTATION_ID;
    }

    @Override
    public WaitMode getDefaultWaitMode() {
        return this.defaultWaitMode;
    }

    @Override
    public void start() {
        if (this.j3270 == null) {
            this.j3270 = sneakyThrow(() -> new J3270(new ProcessPiper(this.processBuilder)));
        }
    }

    @Override
    public void connect(String url) {
        sneakyThrow(() -> {
            this.j3270.connect(url);
            return null;
        });
    }

    @Override
    public void open(String url) {
        this.start();
        this.connect(url);
    }

    @Override
    public boolean started() {
        return sneakyThrow(() -> j3270 != null && j3270.isRunning());
    }


    @Override
    public boolean connected() {
        return started() && status().getConnectionState() != null && status().getConnectionState().connected();
    }

    @Override
    public TN3270Status status() {
        return TN3270Status.from(j3270.getStatus());
    }

    @Override
    public void disconnect() {
        sneakyThrow(() -> {
            j3270.disconnect();
            return null;
        });
    }

    @Override
    public void stop() {
        this.j3270 = sneakyThrow(() -> {
            j3270.close();
            return null;
        });
    }

    @Override
    public void close() {
        this.disconnect();
        this.stop();
    }

    @Override
    public String screenshot() {
        return sneakyThrow(() -> j3270.ascii().stream().reduce("", (s, s2) -> s + '\n' + s2));
    }

    @Override
    public String read(int row, int col, int length) {
        return sneakyThrow(() -> j3270.ascii(row - 1, col - 1, length).stream().reduce("", (s, s2) -> s + '\n' + s2)).trim();
    }

    @Override
    public void write(int row, int col, String content) {
        sneakyThrow(() -> {
            if (row > 0 || col > 0) {
                j3270.moveCursor(row - 1, col - 1);
            }
            j3270.string(content);
            return null;
        });
    }

    @Override
    public void send(int key) {
        sneakyThrow(() -> {
            j3270.pf(key);
            return null;
        });
    }

    @Override
    public void send(String key) {
        sneakyThrow(() -> {
            if (key.matches("[fF]?[0-9]+")) {
                this.send(Integer.valueOf(key.replaceAll("[fF]", "")));
            } else {
                j3270.getClass().getMethod(key).invoke(j3270);
            }
            return null;
        });
    }

    @Override
    public void wait(Number seconds, WaitMode waitMode) {
        sneakyThrow(() -> {
            if (seconds.intValue() == 0) {
                j3270.wait(convertMode(waitMode));
            } else {
                long timeout = Float.valueOf(seconds.floatValue() * 1000).longValue();
                j3270.wait(new Timeout(timeout, TimeUnit.MILLISECONDS), convertMode(waitMode));
            }
            return null;
        });
    }

    @Override
    public void move(int row, int col) {
        sneakyThrow(() -> {
            j3270.moveCursor(row - 1, col - 1);
            return null;
        });
    }

    private com.j3270.base.WaitMode convertMode(WaitMode waitMode) {
        switch (waitMode) {
            case Disconnect:
                return com.j3270.base.WaitMode.Disconnect;
            case InputField:
                return com.j3270.base.WaitMode.InputField;
            case Mode3270:
                return com.j3270.base.WaitMode._3270Mode;
            case NVTMode:
                return com.j3270.base.WaitMode.NVTMode;
            case Output:
                return com.j3270.base.WaitMode.Output;
            case Seconds:
                return com.j3270.base.WaitMode.Seconds;
            case Unlock:
                return com.j3270.base.WaitMode.Unlock;
            default:
                throw new TN3270jException("Value " + waitMode + " is unknown on 3270/J3270");
        }
    }

}
