package br.bkraujo.engine.core.platform;

import br.bkraujo.engine.Lifecycle;
import org.joml.Vector2f;
import org.joml.Vector4i;
import org.lwjgl.system.MemoryStack;

import static br.bkraujo.engine.Logger.error;
import static org.lwjgl.glfw.GLFW.*;

public final class Monitor implements Lifecycle {
    private final long handle;

    private final Vector2f size = new Vector2f();
    private final Vector2f position = new Vector2f();
    private final Vector2f scale = new Vector2f();
    private final Vector4i workArea = new Vector4i();

    public Monitor(long handle) {
        this.handle = handle;
    }

    public long getHandle() {
        return handle;
    }

    public boolean initialize() {
        try (var stack = MemoryStack.stackPush()) {
            final var posXPtr = stack.mallocInt(1);
            final var posYPtr = stack.mallocInt(1);
            glfwGetMonitorPos(handle, posXPtr, posYPtr);
            position.set(posXPtr.get(), posYPtr.get());
        }

        final var vidMode = glfwGetVideoMode(handle);
        if (vidMode == null) {
            error("GLFW failed to glfwGetVideoMode(...)");
            return false;
        }

        size.set(vidMode.width(), vidMode.height());

        if (GLFWUtils.hasMonitorWorkArea) {
            try (var stack = MemoryStack.stackPush()) {
                final var xPtr = stack.mallocInt(1);
                final var yPtr = stack.mallocInt(1);
                final var wPtr = stack.mallocInt(1);
                final var hPtr = stack.mallocInt(1);
                glfwGetMonitorWorkarea(handle, xPtr, yPtr, wPtr, hPtr);
                workArea.set(xPtr.get(), yPtr.get(), wPtr.get(), hPtr.get());
            }
        }

        // Workaround a small GLFW issue reporting zero on monitor changes: https://github.com/glfw/glfw/pull/1761
        if (GLFWUtils.hasMonitorWorkArea && workArea.z > 0 && workArea.w > 0) {
            position.x = workArea.x;
            position.y = workArea.y;
            size.x = workArea.z;
            size.y = workArea.w;
        }

        // Warning: the validity of monitor DPI information on Windows depends on the application DPI awareness settings,
        // which generally needs to be set in the manifest or at runtime.
        if (GLFWUtils.hasPerMonitorDpi) {
            try (var stack = MemoryStack.stackPush()) {
                final var xPtr = stack.mallocFloat(1);
                final var yPtr = stack.mallocFloat(1);

                glfwGetMonitorContentScale(handle, xPtr, yPtr);
                scale.set(xPtr.get(), yPtr.get());
            }
        }

        return true;
    }

    public Vector2f getSize() { return size; }
    public Vector2f getPosition() { return position; }
    public Vector2f getScale() { return scale; }

    public void terminate() {

    }
}
