package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.MousePressedEvent;
import br.bkraujo.engine.core.platform.event.MouseReleasedEvent;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class GLFWMouseButtonCallback implements GLFWMouseButtonCallbackI {

    public void invoke(long window, int button, int action, int mods) {
        Platform.Mouse.window = window;

        System.arraycopy(Platform.Mouse.current, 0, Platform.Mouse.previous, 0, Platform.Mouse.current.length);
        Platform.Mouse.current[button] = action != GLFW_RELEASE;

        if (action == GLFW_PRESS) Application.onEvent(new MousePressedEvent(window, button));
        if (action == GLFW_RELEASE) Application.onEvent(new MouseReleasedEvent(window, button));
    }

}
