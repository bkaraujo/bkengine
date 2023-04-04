package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewportString;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

public final class ViewportSetWindowTitleFunction extends ImPlatformFuncViewportString {

    public void accept(final ImGuiViewport vp, final String str) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwSetWindowTitle(data.window, str);
    }
}
