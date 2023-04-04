package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewportSuppBoolean;

import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;

public class ViewportGetWindowFocusFunction extends ImPlatformFuncViewportSuppBoolean {

    public boolean get(final ImGuiViewport vp) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        return glfwGetWindowAttrib(data.window, GLFW_FOCUSED) != 0;
    }
}
