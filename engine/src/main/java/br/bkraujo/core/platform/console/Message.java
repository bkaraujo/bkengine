package br.bkraujo.core.platform.console;

import java.time.LocalDateTime;

public final class Message {

    public final Color background;
    public final Color foreground;
    public final CharSequence message;
    public final LocalDateTime time = LocalDateTime.now();

    public Message(Color background, Color foreground, CharSequence message) {
        this.background = background;
        this.foreground = foreground;
        this.message = message;
    }
}
