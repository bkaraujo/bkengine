package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.WindowClosedEvent;
import br.bkraujo.game.Application;
import br.bkraujo.game.Game;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public final class GLFWWindowCloseCallback implements GLFWWindowCloseCallbackI {
    GLFWWindowCloseCallback() {}

    public void invoke(long window) {
        glfwHideWindow(window);
        glfwSetWindowShouldClose(window, true);

        Application.onEvent(new WindowClosedEvent(window));
        if (window == Platform.Window.main) Game.setShouldStop();
    }
}
