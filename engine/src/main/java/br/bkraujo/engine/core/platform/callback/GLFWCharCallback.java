package br.bkraujo.engine.core.platform.callback;


import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.event.KeyTypedEvent;
import org.lwjgl.glfw.GLFWCharCallbackI;

public final class GLFWCharCallback implements GLFWCharCallbackI {

    public void invoke(long window, int codepoint) {
        Application.onEvent(new KeyTypedEvent(window, codepoint));
    }
}
