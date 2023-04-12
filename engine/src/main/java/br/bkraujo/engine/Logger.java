package br.bkraujo.engine;

import br.bkraujo.core.platform.console.Color;
import br.bkraujo.core.platform.console.Console;

public final class Logger {
    public static LogLevel level = LogLevel.INFO;

    private Logger(){}

    public static void trace(String message, Object ... args) { console(LogLevel.TRACE, Color.CYAN, String.format(message, args)); }
    public static void debug(String message, Object ... args) { console(LogLevel.DEBUG, Color.MAGENTA, String.format(message, args)); }
    public static void info(String message, Object ... args) { console(LogLevel.INFO, Color.GREEN, String.format(message, args)); }
    public static void warn(String message, Object ... args) { console(LogLevel.WARN, Color.YELLOW, String.format(message, args)); }
    public static void error(String message, Object ... args) { console(LogLevel.ERROR, Color.RED, String.format(message, args)); }
    public static void fatal(String message, Object ... args) {
        final var buffer = new StringBuilder();
        buffer.append("\n##############################\n");
        buffer.append(String.format(message, args));
        buffer.append("\n##############################\n");
        final var stack = Thread.currentThread().getStackTrace();
        for (int i = 2 ; i < stack.length; ++i) {
            final var element = stack[i];

            buffer.append("  at ");
            buffer.append(element.getClassName());
            buffer.append(" (");
            buffer.append(element.getFileName());
            buffer.append(":");
            buffer.append(element.getLineNumber());
            buffer.append(")");
            buffer.append("\n");
        }

        console(LogLevel.FATAL, Color.RED_BACKGROUND, Color.BLACK_BOLD, buffer.toString());
        System.exit(9);
    }

    private static void console(LogLevel level, Color foreground, String message) {
        console(level, null, foreground, message);
    }

    private static final String FORMAT = "[%s] %-7s %s - %s";
    private static void console(LogLevel level, Color background, Color foreground, String message) {
        if (Logger.level.value < level.value)
            return;

        final var thread = Thread.currentThread();
        Console.stdout(
                background,
                foreground,
                String.format(
                        FORMAT,
                        thread.getName(),
                        level.name(),
                        thread.getStackTrace()[4].getClassName(),
                        message
                )
        );
    }
}
