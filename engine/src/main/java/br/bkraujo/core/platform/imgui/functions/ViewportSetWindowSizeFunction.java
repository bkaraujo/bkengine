package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewportImVec2;

import static org.lwjgl.glfw.GLFW.*;

public class ViewportSetWindowSizeFunction extends ImPlatformFuncViewportImVec2 {
    private final int[] x = new int[1];
    private final int[] y = new int[1];
    private final int[] width = new int[1];
    private final int[] height = new int[1];

    public void accept(final ImGuiViewport viewport, final ImVec2 imVec2) {
        final var data = (ViewportData) viewport.getPlatformUserData();
        // Native OS windows are positioned from the bottom-left corner on macOS, whereas on other platforms they are
        // positioned from the upper-left corner. GLFW makes an effort to convert macOS style coordinates, however it
        // doesn't handle it when changing size. We are manually moving the window in order for changes of size to be based
        // on the upper-left corner.
        if (Platform.IS_APPLE) {
            glfwGetWindowPos(data.window, x, y);
            glfwGetWindowSize(data.window, width, height);
            glfwSetWindowPos(data.window, x[0], y[0] - height[0] + (int) imVec2.y);
        }
        data.ignoreWindowSizeEventFrame = ImGui.getFrameCount();
        glfwSetWindowSize(data.window, (int) imVec2.x, (int) imVec2.y);
        Platform.window.get(data.window).size.set((int) imVec2.x, (int) imVec2.y);
    }
}
