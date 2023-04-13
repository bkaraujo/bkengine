package br.bkraujo.core.platform.imgui.event;

import br.bkraujo.core.platform.event.*;
import br.bkraujo.core.platform.imgui.ViewportData;
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
    public void onEvent(Event event) {
        if (event.is(WindowFocusEvent.class)) onWindowFocus((WindowFocusEvent) event);
        if (event.is(WindowClosedEvent.class)) onWindowClose((WindowClosedEvent) event);
        if (event.is(WindowMovedEvent.class)) onWindowPosition((WindowMovedEvent) event);
        if (event.is(WindowResizedEvent.class))  onWindowResize((WindowResizedEvent) event);
    }

    private void onWindowClose(WindowClosedEvent event) {
        final var viewport = ImGui.findViewportByPlatformHandle(event.getWindow());
        viewport.setPlatformRequestClose(true);
    }

    private void onWindowFocus(WindowFocusEvent event) {
        io.addFocusEvent(event.is(WindowFocusGainedEvent.class));
    }

    private void onWindowPosition(WindowMovedEvent event) {
        final var viewport = ImGui.findViewportByPlatformHandle(event.getWindow());
        final var data = (ViewportData) viewport.getPlatformUserData();
        if (ImGui.getFrameCount() <= data.ignoreWindowSizeEventFrame + 1) return;

        viewport.setPlatformRequestMove(true);
    }

    private void onWindowResize(WindowResizedEvent event) {
        final var viewport = ImGui.findViewportByPlatformHandle(event.getWindow());
        final var data = (ViewportData) viewport.getPlatformUserData();
        if (ImGui.getFrameCount() <= data.ignoreWindowSizeEventFrame + 1) return;

        viewport.setPlatformRequestResize(true);
    }
}
