package br.bkraujo.core.platform.console;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public final class Console {
    private Console(){}

    private static final Queue<Message> queue = new ConcurrentLinkedQueue<>();

    static {
        Executors
                .newSingleThreadExecutor()
                .execute(new Printer(queue));
    }

    public static void stdout(Color background, Color foreground, CharSequence message) {
        queue.add(new Message(background, foreground, message));
    }

}