package br.bkraujo.engine.core.platform.glfw;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.glfw.callback.Callbacks;
import br.bkraujo.engine.graphics.GraphicsApi;
import br.bkraujo.game.GraphicsConfiguration;
import br.bkraujo.game.PlatformConfiguration;
import br.bkraujo.utils.Vectors;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static br.bkraujo.engine.Logger.error;
import static org.lwjgl.glfw.GLFW.*;

public class GLFWWindow implements br.bkraujo.engine.platform.Window {
    private long handle;
    private final String title;
    private final PlatformConfiguration platform;
    private final GraphicsConfiguration graphics;

    public GLFWWindow(String title, PlatformConfiguration platform, GraphicsConfiguration graphics) {
        this.title = title;
        this.platform = platform;
        this.graphics = graphics;
    }

    public boolean initialize() {
        final var resolution = platform.getWindowResolution();
        final var ratio = platform.getWindowAspectRation();
        final var size = Vectors.of(0, 0);
        size.set(resolution.compute(ratio), resolution.pixels);

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
        glfwWindowHint(GLFW_RESIZABLE, platform.getWindowResizeable() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, platform.getWindowMaximized() ? GLFW_TRUE : GLFW_FALSE);

        if (graphics.getGraphicsApi() == GraphicsApi.OPENGL) {
            glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

            final var conf = graphics.getGLVersion();
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, conf.x());
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, conf.y());
        }

        handle = glfwCreateWindow(size.x, size.y, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (handle == 0) { error("GLFW failed to create window"); return false; }

        Platform.Window.main = handle;
        Platform.window.put(handle, new Platform.Window(handle));
        Platform.window.get(handle).title = title;
        Platform.window.get(handle).ratio = platform.getWindowAspectRation();
        Platform.window.get(handle).size.set(size.x, size.y);
        Platform.window.get(handle).maximized = platform.getWindowMaximized();

        try (var stack = MemoryStack.stackPush()) {
            final var xPtr = stack.mallocInt(1);
            final var yPtr = stack.mallocInt(1);
            glfwGetFramebufferSize(handle, xPtr, yPtr);
            Platform.window.get(handle).framebuffer.set(xPtr.get(), yPtr.get());
        }

        if (platform.getWindowMaximized()) return true;

        final var monitor = glfwGetPrimaryMonitor();
        final var mode = glfwGetVideoMode(monitor);
        if (mode == null) {
            error("GLFW failed to get Video Mode");

        } else {
            final var position = Vectors.of((mode.width() - size.x) / 2, (mode.height() - size.y) / 2);
            Platform.window.get(handle).position.set(position);
            glfwSetWindowPos(handle, position.x, position.y);
        }

        // Keyboard Callbacks
        glfwSetKeyCallback(handle, Callbacks.KEYBOARD);
        glfwSetCharCallback(handle, Callbacks.CHARACTER);

        // Mouse Callbacks
        glfwSetScrollCallback(handle, Callbacks.CURSOR_SCROLL);
        glfwSetMouseButtonCallback(handle, Callbacks.CURSOR_BUTTON);
        glfwSetCursorPosCallback(handle, Callbacks.CURSOR_POSITION);
        glfwSetCursorEnterCallback(handle, Callbacks.CURSOR_ENTER);

        // Window callbacks
        glfwSetWindowPosCallback(handle, Callbacks.WINDOW_POSITION);
        glfwSetWindowSizeCallback(handle, Callbacks.WINDOW_SIZE);
        glfwSetWindowFocusCallback(handle, Callbacks.WINDOW_FOCUS);
        glfwSetWindowIconifyCallback(handle, Callbacks.WINDOW_ICONIFY);
        glfwSetWindowMaximizeCallback(handle, Callbacks.WINDOW_MAXIMIZE);
        glfwSetWindowCloseCallback(handle, Callbacks.WINDOW_CLOSE);

        // Other callbacks
        Callbacks.MONITOR.invoke(glfwGetPrimaryMonitor(), GLFW_DONT_CARE);

        glfwSetMonitorCallback(Callbacks.MONITOR);
        glfwSetFramebufferSizeCallback(handle, Callbacks.FRAMEBUFFER_SIZE);

        return true;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().length() == 0) { error("Title is null or empty"); return; }

        Platform.window.get(handle).title = title;
        glfwSetWindowTitle(handle, title);
    }

    public void appendTitle(String title) {
        glfwSetWindowTitle(handle, String.format("%s %s", Platform.window.get(handle).title, title));
    }

    public void show() {
        glfwShowWindow(handle);
    }

    public void hide() {
        glfwHideWindow(handle);
    }

    public void terminate() {
        glfwDestroyWindow(handle);
    }
}
