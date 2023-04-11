package br.bkraujo.engine.core.platform;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.core.platform.glfw.GLFWWindow;
import br.bkraujo.engine.platform.AspectRatio;
import br.bkraujo.game.GraphicsConfiguration;
import br.bkraujo.game.PlatformConfiguration;
import br.bkraujo.utils.Vectors;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static br.bkraujo.engine.Logger.error;
import static br.bkraujo.engine.Logger.trace;
import static org.lwjgl.glfw.GLFW.*;

public final class Platform implements Lifecycle {
    public static final String OS = System.getProperty("os.name", "generic").toLowerCase();
    public static final boolean IS_WINDOWS = OS.contains("win");
    public static final boolean IS_APPLE = OS.contains("mac") || OS.contains("darwin");

    public static final List<Monitor> monitors = new ArrayList<>();
    public boolean initialize() {
        trace("Initializing Platform");
        if (!GLFW.glfwInit()) { error("GLFW failed to initialize"); return false; }

        return true;
    }

    public br.bkraujo.engine.platform.Window create(String title, PlatformConfiguration platform,  GraphicsConfiguration graphics) {
        return new GLFWWindow(title, platform, graphics);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void terminate() {
        trace("Terminating Platform");

        final var cbk = glfwSetErrorCallback(null);
        if (cbk != null) cbk.free();

        glfwTerminate();
    }

    public static final Map<Long, Window> window = new TreeMap<>();

    /** Read view of the current state */
    public static class Window {
        public static long main;
        public final long handle;
        public String title;
        public final Vector2i size = Vectors.of(0,0);
        public final Vector2i position = Vectors.of(0,0);
        public final Vector2i framebuffer = Vectors.of(0,0);
        public AspectRatio ratio = AspectRatio.FREE;

        // Set in reflectively by glfwCallback
        public boolean iconified;
        public boolean maximized;
        public boolean focused;

        public Window(long handle) { this.handle = handle; }
    }

    /** Read view of the current state */
    public static abstract class Mouse {
        private Mouse(){}

        /** Mouse hoover window. */
        public static long window;

        /** The offset of the scroll */
        public static final Vector2d scroll = Vectors.of(0d,0d);

        /** Current position of the mouse */
        public static final Vector2f position = Vectors.of(0f,0f);
        /** Button state on current frame */
        public static final boolean[] current = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        /** Button state on previous frame */
        public static final boolean[] previous = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

        /** Button was released in this frame */
        public static boolean isReleased(int button) { return previous[button] && !current[button]; }
        /** Button was pressed in this frame */
        public static boolean isPressed(int button) { return !previous[button] && current[button]; }
    }

    /** A view of the current platform Keyboard information */
    public static abstract class Keyboard {
        private Keyboard(){}

        /** Key state on current frame */
        public static final boolean[] current = new boolean[GLFW.GLFW_KEY_LAST];
        /** Key state on previous frame */
        public static final boolean[] previous = new boolean[GLFW.GLFW_KEY_LAST];

        /** Key is pressed/held in this frame */
        public static boolean isActive(int button) { return current[button]; }
        /** Key was released in this frame */
        public static boolean isReleased(int button) { return previous[button] && !current[button]; }
        /** Key was pressed in this frame */
        public static boolean isPressed(int button) { return !previous[button] && current[button]; }
    }
}
