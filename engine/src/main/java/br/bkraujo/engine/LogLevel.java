package br.bkraujo.engine;

public enum LogLevel {

    FATAL (0),
    ERROR (1),
    INFO  (2),
    WARN  (3),
    DEBUG (4),
    TRACE (5);

    public final int value;

    LogLevel(int value) {
        this.value = value;
    }
}
