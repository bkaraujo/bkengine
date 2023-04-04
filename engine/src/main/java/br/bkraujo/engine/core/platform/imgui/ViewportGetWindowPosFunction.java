package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;

import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;

public class ViewportGetWindowPosFunction extends ImPlatformFuncViewportSuppImVec2 {
    private final int[] posX = new int[1];
    private final int[] posY = new int[1];

    public void get(final ImGuiViewport vp, final ImVec2 dstImVec2) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwGetWindowPos(data.window, posX, posY);
        dstImVec2.x = posX[0];
        dstImVec2.y = posY[0];
    }
}
