package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowMovedEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public final class GLFWWindowPosCallback implements GLFWWindowPosCallbackI {
    GLFWWindowPosCallback(){}

    public void invoke(long window, int xpos, int ypos) {
        Platform.window.get(window).position.set(xpos, ypos);
        Application.onEvent(new WindowMovedEvent(window, Vectors.of(xpos, ypos)));
    }
}
