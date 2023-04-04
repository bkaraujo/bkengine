package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.MouseEnteredEvent;
import br.bkraujo.engine.core.platform.event.MouseExitedEvent;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public final class GLFWCursorEnterCallback implements GLFWCursorEnterCallbackI {

    public void invoke(long window, boolean entered) {
        Platform.Mouse.window = window;
        Application.onEvent(entered ? new MouseEnteredEvent(window) : new MouseExitedEvent(window));
    }
}
