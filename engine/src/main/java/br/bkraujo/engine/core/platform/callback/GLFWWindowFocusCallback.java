package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowFocusGainedEvent;
import br.bkraujo.engine.core.platform.event.WindowFocusLostEvent;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;

public final class GLFWWindowFocusCallback implements GLFWWindowFocusCallbackI {

    public void invoke(long window, boolean focused) {
        if (window == Platform.Window.handle) Platform.Window.focused = focused;
        Application.onEvent(focused ? new WindowFocusGainedEvent(window) : new WindowFocusLostEvent(window));
    }
}
