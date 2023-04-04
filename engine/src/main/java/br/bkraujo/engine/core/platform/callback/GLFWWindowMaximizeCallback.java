package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowMaximizedEvent;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public final class GLFWWindowMaximizeCallback implements GLFWWindowMaximizeCallbackI {

    public void invoke(long window, boolean maximized) {
        if (window == Platform.Window.handle) Platform.Window.maximized = maximized;
        Application.onEvent(new WindowMaximizedEvent(window));
    }
}
