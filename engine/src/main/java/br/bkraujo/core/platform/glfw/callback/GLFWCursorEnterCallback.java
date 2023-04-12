package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.MouseEnteredEvent;
import br.bkraujo.core.platform.event.MouseExitedEvent;
import br.bkraujo.game.Application;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public final class GLFWCursorEnterCallback implements GLFWCursorEnterCallbackI {
    GLFWCursorEnterCallback(){}

    public void invoke(long window, boolean entered) {
        Platform.Mouse.window = entered ? window : 0;
        Application.onEvent(entered ? new MouseEnteredEvent(window) : new MouseExitedEvent(window));
    }
}
