package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class ViewportGetWindowSizeFunction extends ImPlatformFuncViewportSuppImVec2 {
    private final int[] width = new int[1];
    private final int[] height = new int[1];

    public void get(final ImGuiViewport vp, final ImVec2 dstImVec2) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwGetWindowSize(data.window, width, height);
        dstImVec2.x = width[0];
        dstImVec2.y = height[0];
    }
}
