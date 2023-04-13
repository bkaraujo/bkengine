package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;
import org.lwjgl.glfw.Callbacks;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;

public class ViewportDestroyWindowFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport viewport) {
        final var data = (ViewportData) viewport.getPlatformUserData();

        viewport.setPlatformHandle(0);
        viewport.setPlatformHandleRaw(0);

        if (data == null) return;

        viewport.setPlatformUserData(null);

        if (data.parent != 0) {
            Callbacks.glfwFreeCallbacks(data.window);
            Platform.window.remove(data.window);
            glfwDestroyWindow(data.window);
        }
    }
}
