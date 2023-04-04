package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public class ViewportSwapBuffersFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport vp) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwMakeContextCurrent(data.window);
        glfwSwapBuffers(data.window);
    }
}
