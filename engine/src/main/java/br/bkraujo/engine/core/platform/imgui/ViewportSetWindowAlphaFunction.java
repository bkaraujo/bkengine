package br.bkraujo.engine.core.platform.imgui;

import br.bkraujo.engine.core.platform.GLFWUtils;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewportFloat;

import static org.lwjgl.glfw.GLFW.glfwSetWindowOpacity;

public class ViewportSetWindowAlphaFunction extends ImPlatformFuncViewportFloat {

    public void accept(final ImGuiViewport vp, final float f) {
        if (!GLFWUtils.hasWindowAlpha) return;

        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwSetWindowOpacity(data.window, f);
    }
}
