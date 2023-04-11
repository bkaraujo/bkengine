package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowResizedEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

public final class GLFWWindowSizeCallback implements GLFWWindowSizeCallbackI {
    GLFWWindowSizeCallback(){}

    public void invoke(long window, int width, int height) {
        Platform.window.get(window).size.set(width, height);
        Application.onEvent(new WindowResizedEvent(window, Vectors.of(width, height)));
    }
}
