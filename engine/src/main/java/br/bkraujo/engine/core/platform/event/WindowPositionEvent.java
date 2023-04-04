package br.bkraujo.engine.core.platform.event;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public final class WindowPositionEvent extends WindowEvent {
    private final Vector2ic position;

    public WindowPositionEvent(long window, Vector2ic position) {
        super(window);
        this.position = new Vector2i(position);
    }

    public Vector2ic getPosition() {
        return position;
    }
}
