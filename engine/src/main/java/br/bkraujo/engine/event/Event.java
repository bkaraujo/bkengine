package br.bkraujo.engine.event;

import java.time.LocalDateTime;

public abstract class Event {
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final long window;
    private boolean handled;

    protected Event(long window) {
        this.window = window;
    }

    public long getWindow() { return window; }
    public void setHandled() { handled = true; }
    public boolean isHandled() { return handled; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    /** Is this event assignable from? */
    public <T extends Event> boolean is(Class<T> klass) {
        return klass.isAssignableFrom(getClass());
    }
}
