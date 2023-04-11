package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.MouseScrolledEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public final class GLFWScrollCallback implements GLFWScrollCallbackI {
    GLFWScrollCallback(){}

    public void invoke(long window, double xoffset, double yoffset) {
        Platform.Mouse.scroll.set(xoffset, yoffset);
        Application.onEvent(new MouseScrolledEvent(window, Vectors.of(xoffset, yoffset)));
    }

}
