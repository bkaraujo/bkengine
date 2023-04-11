package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.event.KeyTypedEvent;
import org.lwjgl.glfw.GLFWCharCallbackI;

public final class GLFWCharCallback implements GLFWCharCallbackI {
    GLFWCharCallback(){}

    public void invoke(long window, int codepoint) {
        Application.onEvent(new KeyTypedEvent(window, codepoint));
    }
}
