package br.bkraujo.core.platform.imgui.functions;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.glfw.GLFWUtils;
import br.bkraujo.core.platform.glfw.callback.Callbacks;
import br.bkraujo.core.platform.imgui.ViewportData;
import imgui.ImGuiViewport;
import imgui.callback.ImPlatformFuncViewport;
import imgui.flag.ImGuiViewportFlags;
import org.lwjgl.glfw.GLFWNativeWin32;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class ViewportCreateWindowFunction extends ImPlatformFuncViewport {

    public void accept(final ImGuiViewport viewport) {
        final var data = new ViewportData();
        viewport.setPlatformUserData(data);

        createWindow(viewport, data);
        setUpCallbacks(data);
        setUpOpengl(data);
    }

    private void createWindow(ImGuiViewport viewport, ViewportData data) {
        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, viewport.hasFlags(ImGuiViewportFlags.NoDecoration) ? GLFW_FALSE : GLFW_TRUE);
        if (GLFWUtils.hasWindowTopmost) glfwWindowHint(GLFW_FLOATING, viewport.hasFlags(ImGuiViewportFlags.TopMost) ? GLFW_TRUE : GLFW_FALSE);
        if (GLFWUtils.hasFocusOnShow) glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_FALSE); else glfwWindowHint(GLFW_FOCUSED, GLFW_FALSE);

        data.window = glfwCreateWindow((int) viewport.getSizeX(), (int) viewport.getSizeY(), "No Title Yet", NULL, Platform.Window.main);
        data.parent = Platform.Window.main;
        viewport.setPlatformHandle(data.window);
        if (Platform.IS_WINDOWS) viewport.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window(data.window));
        glfwSetWindowPos(data.window, (int) viewport.getPosX(), (int) viewport.getPosY());

        Platform.window.put(data.window, new Platform.Window(data.window));
        Platform.window.get(data.window).title = "No Title Yet";
        Platform.window.get(data.window).size.set((int) viewport.getSizeX(), (int) viewport.getSizeY());
        Platform.window.get(data.window).position.set((int) viewport.getPosX(), (int) viewport.getPosY());
    }

    private void setUpCallbacks(ViewportData data) {
        // Keyboard Callbacks
        glfwSetKeyCallback(data.window, Callbacks.KEYBOARD);
        glfwSetCharCallback(data.window, Callbacks.CHARACTER);

        // Mouse Callbacks
        glfwSetScrollCallback(data.window, Callbacks.CURSOR_SCROLL);
        glfwSetMouseButtonCallback(data.window, Callbacks.CURSOR_BUTTON);
        glfwSetCursorPosCallback(data.window, Callbacks.CURSOR_POSITION);
        glfwSetCursorEnterCallback(data.window, Callbacks.CURSOR_ENTER);

        // Window callbacks
        glfwSetWindowPosCallback(data.window, Callbacks.WINDOW_POSITION);
        glfwSetWindowSizeCallback(data.window, Callbacks.WINDOW_SIZE);
        glfwSetWindowFocusCallback(data.window, Callbacks.WINDOW_FOCUS);
        glfwSetWindowIconifyCallback(data.window, Callbacks.WINDOW_ICONIFY);
        glfwSetWindowMaximizeCallback(data.window, Callbacks.WINDOW_MAXIMIZE);
        glfwSetWindowCloseCallback(data.window, Callbacks.WINDOW_CLOSE);

        // Framebuffer
        glfwSetFramebufferSizeCallback(data.window, Callbacks.FRAMEBUFFER_SIZE);
    }

    private void setUpOpengl(ViewportData data) {
        glfwMakeContextCurrent(data.window);
        glfwSwapInterval(0);
    }

}
