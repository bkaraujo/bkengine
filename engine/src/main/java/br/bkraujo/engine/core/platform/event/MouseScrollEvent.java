package br.bkraujo.engine.core.platform.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;

public class MouseScrollEvent extends MouseEvent {

    private final Vector2d offset = new Vector2d();
    public MouseScrollEvent(long window, Vector2dc offset) {
        super(window);
        this.offset.set(offset);
    }

    public Vector2dc getOffset() {
        return offset;
    }
}
