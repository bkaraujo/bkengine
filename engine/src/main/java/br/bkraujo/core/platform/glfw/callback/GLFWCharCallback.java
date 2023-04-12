package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.event.KeyTypedEvent;
import br.bkraujo.game.Application;
import org.lwjgl.glfw.GLFWCharCallbackI;

public final class GLFWCharCallback implements GLFWCharCallbackI {
    GLFWCharCallback(){}

    public void invoke(long window, int codepoint) {
        Application.onEvent(new KeyTypedEvent(window, codepoint));
    }
}
