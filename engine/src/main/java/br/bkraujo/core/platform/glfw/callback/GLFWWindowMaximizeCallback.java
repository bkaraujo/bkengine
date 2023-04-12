package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.WindowMaximizedEvent;
import br.bkraujo.game.Application;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public final class GLFWWindowMaximizeCallback implements GLFWWindowMaximizeCallbackI {
    GLFWWindowMaximizeCallback(){}

    public void invoke(long window, boolean maximized) {
        Platform.window.get(window).maximized = maximized;
        Application.onEvent(new WindowMaximizedEvent(window));
    }
}
