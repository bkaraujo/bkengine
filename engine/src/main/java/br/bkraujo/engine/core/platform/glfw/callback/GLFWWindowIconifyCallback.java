package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowIconifiedEvent;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public final class GLFWWindowIconifyCallback implements GLFWWindowIconifyCallbackI {
    GLFWWindowIconifyCallback(){}

    public void invoke(long window, boolean iconified) {
        Platform.window.get(window).iconified = iconified;
        Application.onEvent(new WindowIconifiedEvent(window));
    }
}
