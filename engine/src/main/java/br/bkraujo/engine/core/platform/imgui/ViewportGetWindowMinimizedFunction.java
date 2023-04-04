package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewportSuppBoolean;

import static org.lwjgl.glfw.GLFW.GLFW_ICONIFIED;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;

public class ViewportGetWindowMinimizedFunction extends ImPlatformFuncViewportSuppBoolean {

    public boolean get(final ImGuiViewport vp) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        return glfwGetWindowAttrib(data.window, GLFW_ICONIFIED) != 0;
    }
}
