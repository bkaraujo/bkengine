package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.MouseScrollEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public final class GLFWScrollCallback implements GLFWScrollCallbackI {

    public void invoke(long window, double xoffset, double yoffset) {
        Platform.Mouse.scroll.set(xoffset, yoffset);
        Application.onEvent(new MouseScrollEvent(window, Vectors.of(xoffset, yoffset)));
    }

}
