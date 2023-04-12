package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.FramebufferResizedEvent;
import br.bkraujo.game.Application;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public final class GLFWFramebufferSizeCallback implements GLFWFramebufferSizeCallbackI {
    GLFWFramebufferSizeCallback(){}

    public void invoke(long window, int width, int height) {
        Platform.window.get(window).framebuffer.set(width, height);
        Application.onEvent(new FramebufferResizedEvent(window, Vectors.of(width, height)));
    }
}
