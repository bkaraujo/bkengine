package br.bkraujo.engine.core.platform;

import org.joml.Vector3i;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.GLFW.glfwGetVersion;

public abstract class GLFWUtils {
    private GLFWUtils(){}

    private static final Vector3i version = new Vector3i();
    private static void readVersion() {
        try (var stack = MemoryStack.stackPush()){
            final var major = stack.mallocInt(1);
            final var minor = stack.mallocInt(1);
            final var rev = stack.mallocInt(1);

            glfwGetVersion(major, minor, rev);
            version.x = major.get();
            version.y = minor.get();
            version.z = rev.get();
        }
    }

    public static boolean hasFocusWindow() {
        if (version.x == 0) readVersion();
        if (version.x != 3) return false;

        return version.y >= 2;
    }

    public static boolean hasWindowTopmost() {
        if (version.x == 0) readVersion();
        if (version.x != 3) return false;

        return version.y >= 2;
    }

    public static boolean hasMonitorWorkArea() {
        if (version.x == 0) readVersion();
        if (version.x != 3) return false;

        return version.y >= 3;
    }

    public static boolean hasFocusOnShow() {
        if (version.x == 0) readVersion();
        if (version.x != 3) return false;

        return version.y >= 3;
    }

    public static boolean hasPerMonitorDpi() {
        if (version.x == 0) readVersion();
        if (version.x != 3) return false;

        return version.y >= 3;
    }

    public static boolean hasWindowAlpha() {
        if (version.x == 0) readVersion();
        if (version.x != 3) return false;

        return version.y >= 3;
    }

}
