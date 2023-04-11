package br.bkraujo.engine.core.platform;

import br.bkraujo.utils.ThreadUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public final class Console {
    private Console(){}

    private static final Queue<CharSequence> queue = new ConcurrentLinkedQueue<>();

    static { Executors.newSingleThreadExecutor().execute(new Printer()); }

    public static void stdout(CharSequence message) {
        queue.add(message);
    }

    private static class Printer implements Runnable {
        @Override
        public void run() {
            while (true) {
                while (!queue.isEmpty()) System.out.println(queue.poll());

                ThreadUtils.sleep(250);
            }
        }
    }
}