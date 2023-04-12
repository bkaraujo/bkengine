package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class ViewportRenderWindowFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport viewport) {
        final var data = (ViewportData) viewport.getPlatformUserData();
        glfwMakeContextCurrent(data.window);
    }
}
