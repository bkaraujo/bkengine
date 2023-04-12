package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;

public class ViewportGetWindowSizeFunction extends ImPlatformFuncViewportSuppImVec2 {
    public void get(final ImGuiViewport viewport, final ImVec2 dstImVec2) {
        final var data = (ViewportData) viewport.getPlatformUserData();
        final var size = Platform.window.get(data.window).size;
        dstImVec2.set(size.x, size.y);
    }
}
