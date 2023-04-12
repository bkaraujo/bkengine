package br.bkraujo.core.platform.event;

public final class KeyRepeatedEvent extends KeyboardEvent {
    private final int mods;

    public KeyRepeatedEvent(long window, int key, int mods) {
        super(window, key);
        this.mods = mods;
    }
    public int getMods() {
        return mods;
    }
}
