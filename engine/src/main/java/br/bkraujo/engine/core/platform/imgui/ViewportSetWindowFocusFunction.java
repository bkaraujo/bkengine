package br.bkraujo.engine.core.platform.imgui;

import br.bkraujo.engine.core.platform.GLFWUtils;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;

import static org.lwjgl.glfw.GLFW.glfwFocusWindow;

public final class ViewportSetWindowFocusFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport vp) {
        if (!GLFWUtils.hasFocusWindow) return;

        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwFocusWindow(data.window);
    }
}
