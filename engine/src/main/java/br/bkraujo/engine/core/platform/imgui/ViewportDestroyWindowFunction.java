package br.bkraujo.engine.core.platform.imgui;

import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;
import org.lwjgl.glfw.Callbacks;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;

public class ViewportDestroyWindowFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport vp) {
        final ViewportData data = (ViewportData) vp.getPlatformUserData();

        if (data != null && data.windowOwned) {
//            // Release any keys that were pressed in the window being destroyed and are still held down,
//            // because we will not receive any release events after window is destroyed.
//            for (int i = 0; i < keyOwnerWindows.length; i++) {
//                if (keyOwnerWindows[i] == data.window) {
//                    keyCallback(data.window, i, 0, GLFW_RELEASE, 0); // Later params are only used for main viewport, on which this function is never called.
//                }
//            }

            Callbacks.glfwFreeCallbacks(data.window);
            glfwDestroyWindow(data.window);
        }

        vp.setPlatformUserData(null);
        vp.setPlatformHandle(0);
    }
}
