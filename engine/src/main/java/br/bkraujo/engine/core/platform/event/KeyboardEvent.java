package br.bkraujo.engine.core.platform.event;

public abstract class KeyboardEvent extends InputEvent {
    private final int key;
    protected KeyboardEvent(long window, int key) {
        super(window);
        this.key = key;
    }

    public final int getKey() {
        return key;
    }
}
