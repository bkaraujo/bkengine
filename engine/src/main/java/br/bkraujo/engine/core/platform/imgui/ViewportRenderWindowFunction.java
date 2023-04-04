package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class ViewportRenderWindowFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport vp) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwMakeContextCurrent(data.window);
    }
}
