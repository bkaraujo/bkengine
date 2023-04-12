package br.bkraujo.core.platform.event;

public final class KeyTypedEvent extends KeyboardEvent {
    public KeyTypedEvent(long window, int character) {
        super(window, character);
    }
}
