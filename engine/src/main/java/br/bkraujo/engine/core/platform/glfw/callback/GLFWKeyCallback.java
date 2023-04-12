package br.bkraujo.engine.core.platform.glfw.callback;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.KeyPressedEvent;
import br.bkraujo.engine.core.platform.event.KeyReleasedEvent;
import br.bkraujo.engine.core.platform.event.KeyRepeatedEvent;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public final class GLFWKeyCallback implements GLFWKeyCallbackI {
    GLFWKeyCallback(){}
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_UNKNOWN) return;

        System.arraycopy(Platform.Keyboard.current, 0, Platform.Keyboard.previous, 0, Platform.Keyboard.current.length);
        Platform.Keyboard.current[key] = action != GLFW_RELEASE;

        if (action == GLFW_PRESS) Application.onEvent(new KeyPressedEvent(window, key, mods));
        if (action == GLFW_REPEAT) Application.onEvent(new KeyRepeatedEvent(window, key, mods));
        if (action == GLFW_RELEASE) Application.onEvent(new KeyReleasedEvent(window, key, mods));
    }
}
