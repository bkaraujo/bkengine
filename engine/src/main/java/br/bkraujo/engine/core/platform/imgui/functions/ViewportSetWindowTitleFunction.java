package br.bkraujo.engine.core.platform.imgui.functions;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewportString;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

public final class ViewportSetWindowTitleFunction extends ImPlatformFuncViewportString {

    public void accept(final ImGuiViewport viewport, final String str) {
        final var data = (ViewportData) viewport.getPlatformUserData();
        glfwSetWindowTitle(data.window, str);
        Platform.window.get(data.window).title = str;
    }
}
