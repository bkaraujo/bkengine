package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowMaximizedEvent;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public final class GLFWWindowMaximizeCallback implements GLFWWindowMaximizeCallbackI {
    GLFWWindowMaximizeCallback(){}

    public void invoke(long window, boolean maximized) {
        Platform.window.get(window).maximized = maximized;
        Application.onEvent(new WindowMaximizedEvent(window));
    }
}
