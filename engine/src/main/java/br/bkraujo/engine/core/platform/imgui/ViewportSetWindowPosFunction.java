package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewportImVec2;

import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;

public class ViewportSetWindowPosFunction extends ImPlatformFuncViewportImVec2 {
    public void accept(final ImGuiViewport vp, final ImVec2 imVec2) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        data.ignoreWindowPosEventFrame = ImGui.getFrameCount();
        glfwSetWindowPos(data.window, (int) imVec2.x, (int) imVec2.y);
    }
}
