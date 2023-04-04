package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.WindowFramebufferSizeEvent;
import br.bkraujo.utils.Vectors;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

public final class GLFWFramebufferSizeCallback implements GLFWFramebufferSizeCallbackI {

    public void invoke(long window, int width, int height) {
        if (window == Platform.Window.handle) Platform.Window.framebuffer.set(width, height);
        Application.onEvent(new WindowFramebufferSizeEvent(window, Vectors.of(width, height)));
    }
}
