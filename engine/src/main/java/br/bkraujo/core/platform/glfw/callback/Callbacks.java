package br.bkraujo.core.platform.glfw.callback;

import org.lwjgl.glfw.*;

public abstract class Callbacks {
    private Callbacks(){}

    // Mouse Callbacks
    public static final GLFWCursorEnterCallbackI CURSOR_ENTER = new GLFWCursorEnterCallback();
    public static final GLFWCursorPosCallbackI CURSOR_POSITION = new GLFWCursorPosCallback();
    public static final GLFWMouseButtonCallbackI CURSOR_BUTTON = new GLFWMouseButtonCallback();
    public static final GLFWScrollCallbackI CURSOR_SCROLL = new GLFWScrollCallback();

    // Keyboard Callbacks
    public static final GLFWKeyCallbackI KEYBOARD = new GLFWKeyCallback();
    public static final GLFWCharCallbackI CHARACTER = new GLFWCharCallback();

    // Window
    public static final GLFWWindowCloseCallbackI WINDOW_CLOSE = new GLFWWindowCloseCallback();
    public static final GLFWWindowFocusCallbackI WINDOW_FOCUS = new GLFWWindowFocusCallback();
    public static final GLFWWindowIconifyCallbackI WINDOW_ICONIFY = new GLFWWindowIconifyCallback();
    public static final GLFWWindowMaximizeCallbackI WINDOW_MAXIMIZE = new GLFWWindowMaximizeCallback();
    public static final GLFWWindowPosCallbackI WINDOW_POSITION = new GLFWWindowPosCallback();
    public static final GLFWWindowSizeCallbackI WINDOW_SIZE = new GLFWWindowSizeCallback();

    // Framebuffer
    public static final GLFWFramebufferSizeCallbackI FRAMEBUFFER_SIZE = new GLFWFramebufferSizeCallback();
    public static final GLFWMonitorCallbackI MONITOR = new GLFWMonitorCallback();
}
