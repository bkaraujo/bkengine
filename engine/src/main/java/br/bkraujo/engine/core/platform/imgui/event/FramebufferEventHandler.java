package br.bkraujo.engine.core.platform.imgui.event;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.FramebufferResizedEvent;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;

public class FramebufferEventHandler implements OnEvent {

    private final ImGuiIO io;
    private final ImGuiPlatformIO platform;

    public FramebufferEventHandler(ImGuiIO io, ImGuiPlatformIO platform) {
        this.io = io;
        this.platform = platform;
    }

    @Override
    public void onEvent(Event event) {
        if (event.is(FramebufferResizedEvent.class)) {
            final var size = Platform.window.get(event.getWindow()).size;
            final var framebuffer = Platform.window.get(event.getWindow()).framebuffer;

            io.setDisplayFramebufferScale(
                    framebuffer.x / (float) size.x,
                    framebuffer.y / (float) size.y
            );
        }
    }
}
