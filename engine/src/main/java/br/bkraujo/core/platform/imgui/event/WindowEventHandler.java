package br.bkraujo.core.platform.imgui.event;

import br.bkraujo.core.platform.event.*;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;

public class WindowEventHandler implements OnEvent {

    private final ImGuiIO io;
    private final ImGuiPlatformIO platform;

    public WindowEventHandler(ImGuiIO io, ImGuiPlatformIO platform) {
        this.io = io;
        this.platform = platform;
    }

    @Override
    public void onEvent(Event e) {
        final var event = (WindowEvent) e;

        if (event.is(WindowFocusEvent.class)) onWindowFocus((WindowFocusEvent) event);
        if (event.is(WindowClosedEvent.class)) onWindowClose((WindowClosedEvent) event);
        if (event.is(WindowMovedEvent.class)) onWindowPosition((WindowMovedEvent) event);
        if (event.is(WindowResizedEvent.class))  onWindowResize((WindowResizedEvent) event);
    }

    private void onWindowClose(WindowClosedEvent event) {
        ImGui
                .findViewportByPlatformHandle(event.getWindow())
                .setPlatformRequestClose(true);
    }

    private void onWindowFocus(WindowFocusEvent event) {
        io.addFocusEvent(event.is(WindowFocusGainedEvent.class));
    }

    private void onWindowPosition(WindowMovedEvent event) {
        ImGui
                .findViewportByPlatformHandle(event.getWindow())
                .setPlatformRequestMove(true);
    }

    private void onWindowResize(WindowResizedEvent event) {
        final var size = event.getSize();
        io.setDisplaySize(size.x(), size.y());

        ImGui
                .findViewportByPlatformHandle(event.getWindow())
                .setPlatformRequestResize(true);
    }

}
