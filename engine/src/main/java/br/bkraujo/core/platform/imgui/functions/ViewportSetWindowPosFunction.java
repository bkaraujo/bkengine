package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewportImVec2;

import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;

public class ViewportSetWindowPosFunction extends ImPlatformFuncViewportImVec2 {

    public void accept(final ImGuiViewport viewport, final ImVec2 imVec2) {
        final var data = (ViewportData) viewport.getPlatformUserData();

        data.ignoreWindowPosEventFrame = ImGui.getFrameCount();
        glfwSetWindowPos(data.window, (int) imVec2.x, (int) imVec2.y);
        Platform.window.get(data.window).position.set((int) imVec2.x, (int) imVec2.y);
    }
}
