package br.bkraujo.engine.core.platform.imgui;

import br.bkraujo.engine.core.platform.GLFWUtils;
import br.bkraujo.engine.core.platform.Platform;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;
import imgui.flag.ImGuiViewportFlags;
import org.lwjgl.glfw.GLFWNativeWin32;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ViewportCreateWindowFunction extends ImPlatformFuncViewport {
    public void accept(final ImGuiViewport vp) {
        final ViewportData data = new ViewportData();

        vp.setPlatformUserData(data);

        // GLFW 3.2 unfortunately always set focus on glfwCreateWindow() if GLFW_VISIBLE is set, regardless of GLFW_FOCUSED
        // With GLFW 3.3, the hint GLFW_FOCUS_ON_SHOW fixes this problem
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUSED, GLFW_FALSE);
        if (GLFWUtils.hasFocusOnShow()) {
            glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_FALSE);
        }
        glfwWindowHint(GLFW_DECORATED, vp.hasFlags(ImGuiViewportFlags.NoDecoration) ? GLFW_FALSE : GLFW_TRUE);
        if (GLFWUtils.hasWindowTopmost()) {
            glfwWindowHint(GLFW_FLOATING, vp.hasFlags(ImGuiViewportFlags.TopMost) ? GLFW_TRUE : GLFW_FALSE);
        }

        data.window = glfwCreateWindow((int) vp.getSizeX(), (int) vp.getSizeY(), "No Title Yet", NULL, Platform.Window.handle);
        data.windowOwned = true;

        vp.setPlatformHandle(data.window);

        if (Platform.IS_WINDOWS) {
            vp.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window(data.window));
        }

        glfwSetWindowPos(data.window, (int) vp.getPosX(), (int) vp.getPosY());

//        // Install GLFW callbacks for secondary viewports
//        glfwSetMouseButtonCallback(data.window, Viewport.this::mouseButtonCallback);
//        glfwSetScrollCallback(data.window, Viewport.this::scrollCallback);
//        glfwSetKeyCallback(data.window, Viewport.this::keyCallback);
//        glfwSetCharCallback(data.window, Viewport.this::charCallback);
//        glfwSetWindowCloseCallback(data.window, Viewport.this::windowCloseCallback);
//        glfwSetWindowPosCallback(data.window, Viewport.this::windowPosCallback);
//        glfwSetWindowSizeCallback(data.window, Viewport.this::windowSizeCallback);

        glfwMakeContextCurrent(data.window);
        glfwSwapInterval(0);
    }
}
