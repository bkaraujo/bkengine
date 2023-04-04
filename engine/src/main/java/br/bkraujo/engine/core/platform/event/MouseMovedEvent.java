package br.bkraujo.engine.core.platform.event;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public final class MouseMovedEvent extends MouseEvent {
    private final Vector2fc position;

    public MouseMovedEvent(long window, Vector2fc position) {
        super(window);
        this.position = new Vector2f(position);
    }

    public Vector2fc getPosition() {
        return position;
    }
}
