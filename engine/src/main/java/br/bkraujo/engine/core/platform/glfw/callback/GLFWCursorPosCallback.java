package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.MouseMovedEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public final class GLFWCursorPosCallback implements GLFWCursorPosCallbackI  {
    GLFWCursorPosCallback(){}

    public void invoke(long window, double xpos, double ypos) {
        Platform.Mouse.window = window;
        Platform.Mouse.position.set(xpos, ypos);

        Application.onEvent(new MouseMovedEvent(window, Vectors.of((float) xpos, (float) ypos)));
    }
}
