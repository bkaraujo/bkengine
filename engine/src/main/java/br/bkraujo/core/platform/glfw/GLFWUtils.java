package br.bkraujo.core.platform.glfw;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.glfwGetVersion;

public abstract class GLFWUtils {
    private GLFWUtils() {
    }

    /** 3.2+ GLFW_FLOATING */
    public static final boolean hasWindowTopmost;
    /** 3.2+ glfwFocusWindow */
    public static final boolean hasFocusWindow;

    /** 3.3+ GLFW_HOVERED */
    public static final boolean hasWindowHovered;
    /** 3.3+ glfwSetWindowOpacity */
    public static final boolean hasWindowAlpha;
    /** 3.3+ glfwGetMonitorContentScale */
    public static final boolean hasPerMonitorDpi;
    /** 3.3+ GLFW_FOCUS_ON_SHOW */
    public static final boolean hasFocusOnShow;
    /** 3.3+ glfwGetMonitorWorkarea */
    public static final boolean hasMonitorWorkArea;
    /** 3.3.1+ Fixed: Resizing window repositions it on MacOS #1553 */
    public static final boolean hasOsxWindowPosFix;

    /** 3.4+ GLFW_RESIZE_ALL_CURSOR, GLFW_RESIZE_NESW_CURSOR, GLFW_RESIZE_NWSE_CURSOR, GLFW_NOT_ALLOWED_CURSOR */
    public static final boolean hasNewCursors;
    /** 3.4+ GLFW_MOUSE_PASSTHROUGH */
    public static final boolean hasMousePassThrough;

    static {
        int version;
        try (var stack = MemoryStack.stackPush()) {
            final var major = stack.mallocInt(1);
            final var minor = stack.mallocInt(1);
            final var rev = stack.mallocInt(1);

            glfwGetVersion(major, minor, rev);
            version = major.get() * 1000 + minor.get() * 100 + rev.get() * 10;
        }

        hasWindowTopmost = version >= 3200;
        hasFocusWindow = version >= 3200;

        hasWindowHovered = version >= 3300;
        hasWindowAlpha = version >= 3300;
        hasPerMonitorDpi = version >= 3300;
        hasFocusOnShow = version >= 3300;
        hasMonitorWorkArea = version >= 3300;
        hasOsxWindowPosFix = version >= 3310;

        hasNewCursors = version >= 3400;
        hasMousePassThrough = version >= 3400;
    }
}
