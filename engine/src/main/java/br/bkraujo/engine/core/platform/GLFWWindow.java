package br.bkraujo.engine.core.platform;

import br.bkraujo.engine.core.platform.callback.*;
import br.bkraujo.engine.graphics.GraphicsApi;
import br.bkraujo.game.GraphicsConfiguration;
import br.bkraujo.game.PlatformConfiguration;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static br.bkraujo.engine.Logger.error;
import static org.lwjgl.glfw.GLFW.*;

public class GLFWWindow implements br.bkraujo.engine.platform.Window {
    private final PlatformConfiguration platform;
    private final GraphicsConfiguration graphics;

    public GLFWWindow(String title, PlatformConfiguration platform, GraphicsConfiguration graphics) {
        Platform.Window.title = title;
        this.platform = platform;
        this.graphics = graphics;
    }

    public boolean initialize() {
        final var resolution = platform.getWindowResolution();
        Platform.Window.ratio = platform.getWindowAspectRation();
        Platform.Window.size.set(resolution.compute(Platform.Window.ratio), resolution.pixels);

        if (!create()) return false;
        if (!centralize()) return false;
        registerCallbacks();
        return true;
    }

    private boolean create() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
        glfwWindowHint(GLFW_RESIZABLE, platform.getWindowResizeable() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, platform.getWindowMaximized() ? GLFW_TRUE : GLFW_FALSE);

        Platform.Window.maximized = platform.getWindowMaximized();
        if (graphics.getGraphicsApi() == GraphicsApi.OPENGL) {
            glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

            final var conf = graphics.getGLVersion();
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, conf.x());
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, conf.y());
        }

        Platform.Window.handle = glfwCreateWindow(Platform.Window.size.x(), Platform.Window.size.y(), Platform.Window.title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (Platform.Window.handle == 0) { error("GLFW failed to create window"); return false; }

        try (var stack = MemoryStack.stackPush()) {
            final var xPtr = stack.mallocInt(1);
            final var yPtr = stack.mallocInt(1);
            glfwGetFramebufferSize(Platform.Window.handle, xPtr, yPtr);
            Platform.Window.framebuffer.set(xPtr.get(), yPtr.get());
        }

        return true;
    }

    private boolean centralize() {
        if (platform.getWindowMaximized()) return true;

        final var monitor = glfwGetPrimaryMonitor();
        final var mode = glfwGetVideoMode(monitor);
        if (mode == null) { error("GLFW failed to get Video Mode"); return false; }

        Platform.Window.position.set((mode.width() - Platform.Window.size.x()) / 2, (mode.height() - Platform.Window.size.y()) / 2);
        glfwSetWindowPos(Platform.Window.handle, Platform.Window.position.x, Platform.Window.position.y);

        return true;
    }

    private void registerCallbacks() {
        // Keyboard Callbacks
        glfwSetKeyCallback(Platform.Window.handle, new GLFWKeyCallback());
        glfwSetCharCallback(Platform.Window.handle, new GLFWCharCallback());

        // Mouse Callbacks
        glfwSetScrollCallback(Platform.Window.handle, new GLFWScrollCallback());
        glfwSetMouseButtonCallback(Platform.Window.handle, new GLFWMouseButtonCallback());
        glfwSetCursorPosCallback(Platform.Window.handle, new GLFWCursorPosCallback());
        glfwSetCursorEnterCallback(Platform.Window.handle, new GLFWCursorEnterCallback());

        // Window callbacks
        glfwSetWindowPosCallback(Platform.Window.handle, new GLFWWindowPosCallback());
        glfwSetWindowSizeCallback(Platform.Window.handle, new GLFWWindowSizeCallback());
        glfwSetWindowFocusCallback(Platform.Window.handle, new GLFWWindowFocusCallback());
        glfwSetWindowIconifyCallback(Platform.Window.handle, new GLFWWindowIconifyCallback());
        glfwSetWindowMaximizeCallback(Platform.Window.handle, new GLFWWindowMaximizeCallback());
        glfwSetWindowCloseCallback(Platform.Window.handle, new GLFWWindowCloseCallback());

        // Other callbacks
        glfwSetMonitorCallback(new GLFWMonitorCallback());
        glfwSetFramebufferSizeCallback(Platform.Window.handle, new GLFWFramebufferSizeCallback());
    }

    public void setTitle(String title) {
        if (title == null || title.trim().length() == 0) { error("Title is null or empty"); return; }

        Platform.Window.title = title;
        glfwSetWindowTitle(Platform.Window.handle, title);
    }

    public void appendTitle(String title) {
        glfwSetWindowTitle(Platform.Window.handle, String.format("%s %s", Platform.Window.title, title));
    }

    public void show() {
        glfwShowWindow(Platform.Window.handle);
    }

    public void hide() {
        glfwHideWindow(Platform.Window.handle);
    }

    public void terminate() {
        glfwDestroyWindow(Platform.Window.handle);
    }
}
