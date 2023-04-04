package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowCloseEvent;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public final class GLFWWindowCloseCallback implements GLFWWindowCloseCallbackI {

    public void invoke(long window) {
        glfwHideWindow(window);
        glfwSetWindowShouldClose(window, true);

        Application.onEvent(new WindowCloseEvent(window));
        if (window == Platform.Window.handle) Application.setShouldStop();
    }
}
