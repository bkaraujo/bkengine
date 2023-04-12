package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.WindowFocusGainedEvent;
import br.bkraujo.core.platform.event.WindowFocusLostEvent;
import br.bkraujo.game.Application;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

public final class GLFWWindowFocusCallback implements GLFWWindowFocusCallbackI {
    GLFWWindowFocusCallback(){}

    public void invoke(long window, boolean focused) {
        Platform.window.get(window).focused = focused;
        Application.onEvent(focused ? new WindowFocusGainedEvent(window) : new WindowFocusLostEvent(window));
    }
}
