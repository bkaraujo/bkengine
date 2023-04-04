package br.bkraujo.engine.core.platform.event;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public final class WindowFramebufferSizeEvent extends WindowEvent {

    private final Vector2ic size;
    public WindowFramebufferSizeEvent(long window, Vector2ic size) {

        super(window);
        this.size = new Vector2i(size);
    }

    public Vector2ic getSize() {
        return size;
    }
}
