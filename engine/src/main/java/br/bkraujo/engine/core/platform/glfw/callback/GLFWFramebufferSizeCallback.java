package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.FramebufferResizedEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public final class GLFWFramebufferSizeCallback implements GLFWFramebufferSizeCallbackI {
    GLFWFramebufferSizeCallback(){}

    public void invoke(long window, int width, int height) {
        Platform.window.get(window).framebuffer.set(width, height);
        Application.onEvent(new FramebufferResizedEvent(window, Vectors.of(width, height)));
    }
}
