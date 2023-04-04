package br.bkraujo.engine.core.platform.event;

public final class KeyPressedEvent extends KeyboardEvent {
    private final int mods;

    public KeyPressedEvent(long window, int key, int mods) {
        super(window, key);
        this.mods = mods;
    }

    public int getMods() {
        return mods;
    }
}
