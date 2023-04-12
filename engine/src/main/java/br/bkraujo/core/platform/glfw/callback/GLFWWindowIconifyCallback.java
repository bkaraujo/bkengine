package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.WindowIconifiedEvent;
import br.bkraujo.game.Application;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public final class GLFWWindowIconifyCallback implements GLFWWindowIconifyCallbackI {
    GLFWWindowIconifyCallback(){}

    public void invoke(long window, boolean iconified) {
        Platform.window.get(window).iconified = iconified;
        Application.onEvent(new WindowIconifiedEvent(window));
    }
}
