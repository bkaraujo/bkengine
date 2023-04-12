package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.MouseScrolledEvent;
import br.bkraujo.game.Application;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public final class GLFWScrollCallback implements GLFWScrollCallbackI {
    GLFWScrollCallback(){}

    public void invoke(long window, double xoffset, double yoffset) {
        Platform.Mouse.scroll.set(xoffset, yoffset);
        Application.onEvent(new MouseScrolledEvent(window, Vectors.of(xoffset, yoffset)));
    }

}
