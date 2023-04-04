package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowIconifiedEvent;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;

public final class GLFWWindowIconifyCallback implements GLFWWindowIconifyCallbackI {

    public void invoke(long window, boolean iconified) {
        if (window == Platform.Window.handle) Platform.Window.iconified = iconified;
        Application.onEvent(new WindowIconifiedEvent(window));
    }
}
