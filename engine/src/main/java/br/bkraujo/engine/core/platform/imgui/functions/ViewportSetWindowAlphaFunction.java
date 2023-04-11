package br.bkraujo.engine.core.platform.imgui.functions;

import br.bkraujo.engine.core.platform.glfw.GLFWUtils;
import br.bkraujo.engine.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewportFloat;

import static org.lwjgl.glfw.GLFW.glfwSetWindowOpacity;

public class ViewportSetWindowAlphaFunction extends ImPlatformFuncViewportFloat {

    public void accept(final ImGuiViewport viewport, final float f) {
        if (!GLFWUtils.hasWindowAlpha) return;

        final var data = (ViewportData) viewport.getPlatformUserData();
        glfwSetWindowOpacity(data.window, f);
    }
}
