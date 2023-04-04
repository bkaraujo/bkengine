package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowPositionEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public final class GLFWWindowPosCallback implements GLFWWindowPosCallbackI {

    public void invoke(long window, int xpos, int ypos) {
        if (window == Platform.Window.handle) Platform.Window.position.set(xpos, ypos);
        Application.onEvent(new WindowPositionEvent(window, Vectors.of(xpos, ypos)));
    }
}
