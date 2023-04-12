package br.bkraujo.core.platform.event;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public final class WindowMovedEvent extends WindowEvent {
    private final Vector2ic position;

    public WindowMovedEvent(long window, Vector2ic position) {
        super(window);
        this.position = new Vector2i(position);
    }

    public Vector2ic getPosition() {
        return position;
    }
}
