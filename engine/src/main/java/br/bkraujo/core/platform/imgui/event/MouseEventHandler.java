package br.bkraujo.core.platform.imgui.event;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.*;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiMouseButton;

import static org.lwjgl.glfw.GLFW.*;

public class MouseEventHandler implements OnEvent {

    private final ImGuiIO io;
    private final ImGuiPlatformIO platform;
    private final MouseMoveHandler moveHandler;

    public MouseEventHandler(ImGuiIO io, ImGuiPlatformIO platform) {
        this.io = io;
        this.platform = platform;
        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable))
            moveHandler = new MoseMoveWithViewport();
        else
            moveHandler = new MouseMoveWithoutViewport();
    }

    @Override
    public void onEvent(Event event) {
        if (glfwGetInputMode(event.getWindow(), GLFW_CURSOR) == GLFW_CURSOR_DISABLED) return;

        if (event.is(MouseMovedEvent.class)) moveHandler.onMouseMove((MouseMovedEvent) event);
        if (event.is(MouseButtonEvent.class)) onMouseButton((MouseButtonEvent) event);
        if (event.is(MouseScrolledEvent.class)) onMouseScroll((MouseScrolledEvent) event);
        if (event.is(MouseEnteredEvent.class)) onMouseEnter((MouseEnteredEvent) event);
        if (event.is(MouseExitedEvent.class)) onMouseExit((MouseExitedEvent) event);
    }

    private void onMouseButton(MouseButtonEvent event) {
        final var button = event.getButton();
        if (button > ImGuiMouseButton.COUNT) return;
        io.setMouseDown(button, event.is(MousePressedEvent.class));
    }

    private void onMouseScroll(MouseScrolledEvent event) {
        final var offset = event.getOffset();
        io.setMouseWheelH(io.getMouseWheelH() + (float) offset.x());
        io.setMouseWheel(io.getMouseWheel() + (float) offset.y());
    }

    private void onMouseEnter(MouseEnteredEvent event) {
        final var viewport = ImGui.findViewportByPlatformHandle(event.getWindow());
        io.setMouseHoveredViewport(viewport.getID());
    }

    private void onMouseExit(MouseExitedEvent event) {
        io.setMouseHoveredViewport(0);
    }

    // Simple Function pointer to avoid the IF statement on every frame
    private interface MouseMoveHandler { void onMouseMove(MouseMovedEvent event); }

    private class MoseMoveWithViewport implements MouseMoveHandler {
        public void onMouseMove(MouseMovedEvent event) {
            final var position = event.getPosition();
            final var wPos = Platform.window.get(event.getWindow()).position;
            io.setMousePos(position.x() + wPos.x, position.y() + wPos.y);
        }
    }

    private class MouseMoveWithoutViewport implements MouseMoveHandler {
        public void onMouseMove(MouseMovedEvent event) {
            final var position = event.getPosition();
            io.setMousePos(position.x(), position.y());
        }
    }
}
