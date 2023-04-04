package br.bkraujo.engine;

import br.bkraujo.engine.core.platform.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Logger {
    public static LogLevel level = LogLevel.INFO;

    private Logger(){}

    public static void trace(String message, Object ... args) {
        console(LogLevel.TRACE, Color.CYAN, String.format(message, args));
    }

    public static void debug(String message, Object ... args) {
        if (Logger.level.value < LogLevel.DEBUG.value)
            return;

        console(LogLevel.DEBUG, Color.MAGENTA, String.format(message, args));
    }

    public static void info(String message, Object ... args) {
        if (Logger.level.value < LogLevel.INFO.value)
            return;

        console(LogLevel.INFO, Color.GREEN, String.format(message, args));
    }

    public static void warn(String message, Object ... args) {

        console(LogLevel.WARN, Color.YELLOW, String.format(message, args));
    }

    public static void error(String message, Object ... args) {
        console(LogLevel.ERROR, Color.RED, String.format(message, args));
    }

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

    private static final String FORMAT = "%s%s%s [%s] %-7s %s - %s%s";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSSSSS");
    private static void console(LogLevel level, Color background, Color foreground, String message) {
        if (Logger.level.value < level.value)
            return;

        final var thread = Thread.currentThread();
        Console.stdout(
                String.format(
                        FORMAT,
                        foreground.code,
                        background != null ? background.code : "",
                        FORMATTER.format(LocalDateTime.now()),
                        thread.getName(),
                        level.name(),
                        thread.getStackTrace()[4].getClassName(),
                        message,
                        Color.RESET.code
                )
        );
    }

    private enum Color {
        //Color end string, color reset
        RESET("\033[0m"),

        // Regular Colors. Normal color, no bold, background color etc.
        BLACK("\033[0;30m"),    // BLACK
        RED("\033[0;31m"),      // RED
        GREEN("\033[0;32m"),    // GREEN
        YELLOW("\033[0;33m"),   // YELLOW
        BLUE("\033[0;34m"),     // BLUE
        MAGENTA("\033[0;35m"),  // MAGENTA
        CYAN("\033[0;36m"),     // CYAN
        WHITE("\033[0;37m"),    // WHITE

        // Bold
        BLACK_BOLD("\033[1;30m"),   // BLACK
        RED_BOLD("\033[1;31m"),     // RED
        GREEN_BOLD("\033[1;32m"),   // GREEN
        YELLOW_BOLD("\033[1;33m"),  // YELLOW
        BLUE_BOLD("\033[1;34m"),    // BLUE
        MAGENTA_BOLD("\033[1;35m"), // MAGENTA
        CYAN_BOLD("\033[1;36m"),    // CYAN
        WHITE_BOLD("\033[1;37m"),   // WHITE

        // Underline
        BLACK_UNDERLINED("\033[4;30m"),     // BLACK
        RED_UNDERLINED("\033[4;31m"),       // RED
        GREEN_UNDERLINED("\033[4;32m"),     // GREEN
        YELLOW_UNDERLINED("\033[4;33m"),    // YELLOW
        BLUE_UNDERLINED("\033[4;34m"),      // BLUE
        MAGENTA_UNDERLINED("\033[4;35m"),   // MAGENTA
        CYAN_UNDERLINED("\033[4;36m"),      // CYAN
        WHITE_UNDERLINED("\033[4;37m"),     // WHITE

        // Background
        BLACK_BACKGROUND("\033[40m"),   // BLACK
        RED_BACKGROUND("\033[41m"),     // RED
        GREEN_BACKGROUND("\033[42m"),   // GREEN
        YELLOW_BACKGROUND("\033[43m"),  // YELLOW
        BLUE_BACKGROUND("\033[44m"),    // BLUE
        MAGENTA_BACKGROUND("\033[45m"), // MAGENTA
        CYAN_BACKGROUND("\033[46m"),    // CYAN
        WHITE_BACKGROUND("\033[47m"),   // WHITE

        // High Intensity
        BLACK_BRIGHT("\033[0;90m"),     // BLACK
        RED_BRIGHT("\033[0;91m"),       // RED
        GREEN_BRIGHT("\033[0;92m"),     // GREEN
        YELLOW_BRIGHT("\033[0;93m"),    // YELLOW
        BLUE_BRIGHT("\033[0;94m"),      // BLUE
        MAGENTA_BRIGHT("\033[0;95m"),   // MAGENTA
        CYAN_BRIGHT("\033[0;96m"),      // CYAN
        WHITE_BRIGHT("\033[0;97m"),     // WHITE

        // Bold High Intensity
        BLACK_BOLD_BRIGHT("\033[1;90m"),    // BLACK
        RED_BOLD_BRIGHT("\033[1;91m"),      // RED
        GREEN_BOLD_BRIGHT("\033[1;92m"),    // GREEN
        YELLOW_BOLD_BRIGHT("\033[1;93m"),   // YELLOW
        BLUE_BOLD_BRIGHT("\033[1;94m"),     // BLUE
        MAGENTA_BOLD_BRIGHT("\033[1;95m"),  // MAGENTA
        CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
        WHITE_BOLD_BRIGHT("\033[1;97m"),    // WHITE

        // High Intensity backgrounds
        BLACK_BACKGROUND_BRIGHT("\033[0;100m"),     // BLACK
        RED_BACKGROUND_BRIGHT("\033[0;101m"),       // RED
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"),     // GREEN
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),    // YELLOW
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"),      // BLUE
        MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),   // MAGENTA
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"),      // CYAN
        WHITE_BACKGROUND_BRIGHT("\033[0;107m");     // WHITE

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
