package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewportSuppBoolean;

public class ViewportGetWindowFocusFunction extends ImPlatformFuncViewportSuppBoolean {

    public boolean get(final ImGuiViewport viewport) {
        final var data = (ViewportData) viewport.getPlatformUserData();
        return Platform.window.get(data.window).focused;
    }
}
