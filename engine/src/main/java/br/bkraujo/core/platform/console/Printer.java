package br.bkraujo.core.platform.console;

import br.bkraujo.game.Application;
import br.bkraujo.utils.ThreadUtils;

import java.time.format.DateTimeFormatter;
import java.util.Queue;

class Printer implements Runnable {
    private static final String FORMAT = "%s%s%s %s%s%n";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSSSSS");

    private final Queue<Message> queue;

    Printer(Queue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Application.isShutdown() || !queue.isEmpty()) {
            ThreadUtils.sleep(50);
            while (!queue.isEmpty()) {
                final var message = queue.poll();

                System.out.printf(FORMAT,
                        message.foreground.code,
                        message.background != null ? message.background.code : "",
                        FORMATTER.format(message.time),
                        message.message,
                        Color.RESET.code
                );
            }
        }
    }
}
