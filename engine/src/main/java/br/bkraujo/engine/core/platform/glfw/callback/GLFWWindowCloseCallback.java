package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowClosedEvent;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public final class GLFWWindowCloseCallback implements GLFWWindowCloseCallbackI {
    GLFWWindowCloseCallback() {}

    public void invoke(long window) {
        glfwHideWindow(window);
        glfwSetWindowShouldClose(window, true);

        Application.onEvent(new WindowClosedEvent(window));
        if (window == Platform.Window.main) Application.setShouldStop();
    }
}
