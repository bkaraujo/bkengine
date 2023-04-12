package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;
import imgui.flag.ImGuiViewportFlags;
import imgui.lwjgl3.glfw.ImGuiImplGlfwNative;

import static org.lwjgl.glfw.GLFW.glfwShowWindow;

public class ViewportShowWindowFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport viewport) {
        if (Platform.IS_WINDOWS)
            if (viewport.hasFlags(ImGuiViewportFlags.NoTaskBarIcon))
                ImGuiImplGlfwNative.win32hideFromTaskBar(viewport.getPlatformHandleRaw());

        final var data = (ViewportData) viewport.getPlatformUserData();
        glfwShowWindow(data.window);
    }
}
