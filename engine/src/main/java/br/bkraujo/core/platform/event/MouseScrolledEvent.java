package br.bkraujo.core.platform.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;

public class MouseScrolledEvent extends MouseEvent {

    private final Vector2d offset = new Vector2d();
    public MouseScrolledEvent(long window, Vector2dc offset) {
        super(window);
        this.offset.set(offset);
    }

    public Vector2dc getOffset() {
        return offset;
    }
}
