package br.bkraujo.engine.core.platform.imgui.functions;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.glfw.GLFWUtils;
import br.bkraujo.engine.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;

import static org.lwjgl.glfw.GLFW.glfwFocusWindow;

public final class ViewportSetWindowFocusFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport viewport) {
        if (!GLFWUtils.hasFocusWindow) return;

        final var data = (ViewportData) viewport.getPlatformUserData();
        glfwFocusWindow(data.window);
        Platform.window.get(data.window).focused = true;
    }
}
