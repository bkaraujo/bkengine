package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowSizeEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

public final class GLFWWindowSizeCallback implements GLFWWindowSizeCallbackI {

    public void invoke(long window, int width, int height) {
        if (window == Platform.Window.handle) Platform.Window.size.set(width, height);
        Application.onEvent(new WindowSizeEvent(window, Vectors.of(width, height)));
    }
}
