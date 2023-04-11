package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowFocusGainedEvent;
import br.bkraujo.engine.core.platform.event.WindowFocusLostEvent;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

public final class GLFWWindowFocusCallback implements GLFWWindowFocusCallbackI {
    GLFWWindowFocusCallback(){}

    public void invoke(long window, boolean focused) {
        Platform.window.get(window).focused = focused;
        Application.onEvent(focused ? new WindowFocusGainedEvent(window) : new WindowFocusLostEvent(window));
    }
}
