package br.bkraujo.engine.core.platform.imgui.functions;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;

public class ViewportGetWindowPosFunction extends ImPlatformFuncViewportSuppImVec2 {
    public void get(final ImGuiViewport viewport, final ImVec2 dstImVec2) {
        final var data = (ViewportData) viewport.getPlatformUserData();
        final var position = Platform.window.get(data.window).position;
        dstImVec2.set(position.x, position.y);
    }
}
