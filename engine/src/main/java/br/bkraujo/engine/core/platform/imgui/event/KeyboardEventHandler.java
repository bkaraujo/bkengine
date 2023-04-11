package br.bkraujo.engine.core.platform.imgui.event;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.*;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardEventHandler implements OnEvent {

    private final ImGuiIO io;
    private final ImGuiPlatformIO platform;

    public KeyboardEventHandler(ImGuiIO io, ImGuiPlatformIO platform) {
        this.io = io;
        this.platform = platform;
    }

    @Override
    public void onEvent(Event e) {
        final var event = (KeyboardEvent) e;

        io.setKeyCtrl(Platform.Keyboard.isActive(GLFW_KEY_LEFT_CONTROL) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_CONTROL));
        io.setKeyShift(Platform.Keyboard.isActive(GLFW_KEY_LEFT_SHIFT) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_SHIFT));
        io.setKeyAlt(Platform.Keyboard.isActive(GLFW_KEY_LEFT_ALT) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_ALT));
        io.setKeySuper(Platform.Keyboard.isActive(GLFW_KEY_LEFT_SUPER) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_SUPER));

        if (event.is(KeyTypedEvent.class)) onKeyTyped((KeyTypedEvent) event);
        if (event.is(KeyPressedEvent.class)) onKeyPressed((KeyPressedEvent) event);
        if (event.is(KeyReleasedEvent.class)) onKeyReleased((KeyReleasedEvent) event);
        if (event.is(KeyRepeatedEvent.class)) onKeyRepeated((KeyRepeatedEvent) event);
    }

    private void onKeyTyped(KeyTypedEvent event) {
        io.addInputCharacter(event.getKey());
    }

    private void onKeyPressed(KeyPressedEvent event) {
        io.setKeysDown(event.getKey(), true);
    }

    private void onKeyReleased(KeyReleasedEvent event) {
        io.setKeysDown(event.getKey(), false);
    }

    private void onKeyRepeated(KeyRepeatedEvent event) {
        io.setKeysDown(event.getKey(), true);

    }
}
