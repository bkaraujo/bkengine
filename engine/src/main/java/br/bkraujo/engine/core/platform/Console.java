package br.bkraujo.engine.core.platform;

public final class Console {
    private Console(){}

    public static void stdout(CharSequence message) {
        System.out.println(message);
    }
    public static void stderr(CharSequence message) {
        System.err.println(message);
    }
}