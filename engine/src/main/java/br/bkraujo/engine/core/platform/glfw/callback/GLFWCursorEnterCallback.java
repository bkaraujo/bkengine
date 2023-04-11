package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.MouseEnteredEvent;
import br.bkraujo.engine.core.platform.event.MouseExitedEvent;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public final class GLFWCursorEnterCallback implements GLFWCursorEnterCallbackI {
    GLFWCursorEnterCallback(){}

    public void invoke(long window, boolean entered) {
        Platform.Mouse.window = entered ? window : 0;
        Application.onEvent(entered ? new MouseEnteredEvent(window) : new MouseExitedEvent(window));
    }
}
