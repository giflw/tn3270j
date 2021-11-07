package com.itquasar.multiverse.tn3270j.status;

public final class ConnectionStatus implements StatusCode {

    private final String hostname;
    private final String code;
    private final boolean connected;

    private ConnectionStatus(String connectionString) {
        this.code = connectionString.substring(0, 1);
        this.hostname = this.code.equals("C") ? connectionString.substring(connectionString.indexOf("(") + 1, connectionString.indexOf(")")) : "";
        this.connected = connectionString.startsWith("C");
    }

    public String hostname() {
        return this.hostname;
    }

    public boolean connected() {
        return this.connected;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public static ConnectionStatus from(com.j3270.base.Status.ConnectionState other) {
        return new ConnectionStatus(other.code());
    }

}
