package br.bkraujo.core.platform.event;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public final class WindowResizedEvent extends WindowEvent {
    private final Vector2ic size;

    public WindowResizedEvent(long window, Vector2ic size) {
        super(window);
        this.size = new Vector2i(size);
    }

    public Vector2ic getSize() {
        return size;
    }
}
