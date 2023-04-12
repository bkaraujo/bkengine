package br.bkraujo.core.temporal;

public abstract class Time {
    private Time(){}

    public static final float SECOND = 1_000.0f;
    public static final float MILLISECOND = 1_000_000.0f;
    public static final float NANOSECOND = 1_000_000_000.0f;

    public static long nanos() {return System.nanoTime(); }
    public static long millis() {return System.currentTimeMillis(); }
}
