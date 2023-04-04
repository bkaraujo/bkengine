package br.bkraujo.engine.core.platform.imgui;

import br.bkraujo.engine.core.platform.Platform;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;
import imgui.flag.ImGuiViewportFlags;
import imgui.lwjgl3.glfw.ImGuiImplGlfwNative;

import static org.lwjgl.glfw.GLFW.glfwShowWindow;

public class ViewportShowWindowFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport vp) {
        if (Platform.IS_WINDOWS) {
            if (vp.hasFlags(ImGuiViewportFlags.NoTaskBarIcon)) {
                ImGuiImplGlfwNative.win32hideFromTaskBar(vp.getPlatformHandleRaw());
            }
        }

        final ViewportData data = (ViewportData) vp.getPlatformUserData();
        glfwShowWindow(data.window);
    }
}
