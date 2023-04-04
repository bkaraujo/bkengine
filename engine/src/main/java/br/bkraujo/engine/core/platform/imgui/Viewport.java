package br.bkraujo.engine.core.platform.imgui;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.platform.event.*;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.*;
import org.lwjgl.glfw.GLFWNativeWin32;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public final class Viewport implements Lifecycle, OnEvent {
    private final ImGuiIO io;
    private final ImGuiPlatformIO platform;

    // Mouse cursors provided by GLFW
    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];

    // Empty array to fill ImGuiIO.NavInputs with zeroes
    private final float[] navInputs = new float[ImGuiNavInput.COUNT];

    private double time = 0.0;

    public Viewport(ImGuiIO io, ImGuiPlatformIO platform) {
        this.io = io;
        this.platform = platform;
    }

    public boolean initialize() {
        io.setBackendPlatformName(getClass().getCanonicalName());
        io.addBackendFlags(ImGuiBackendFlags.HasMouseCursors | ImGuiBackendFlags.HasSetMousePos | ImGuiBackendFlags.PlatformHasViewports);

        io.setDisplaySize(Platform.Window.size.x, Platform.Window.size.y);
        io.setDisplayFramebufferScale(Platform.Window.framebuffer.x / (float) Platform.Window.size.x, Platform.Window.framebuffer.y / (float) Platform.Window.size.y);

        // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
        final int[] keyMap = new int[ImGuiKey.COUNT];
        keyMap[ImGuiKey.Tab] = GLFW_KEY_TAB;
        keyMap[ImGuiKey.LeftArrow] = GLFW_KEY_LEFT;
        keyMap[ImGuiKey.RightArrow] = GLFW_KEY_RIGHT;
        keyMap[ImGuiKey.UpArrow] = GLFW_KEY_UP;
        keyMap[ImGuiKey.DownArrow] = GLFW_KEY_DOWN;
        keyMap[ImGuiKey.PageUp] = GLFW_KEY_PAGE_UP;
        keyMap[ImGuiKey.PageDown] = GLFW_KEY_PAGE_DOWN;
        keyMap[ImGuiKey.Home] = GLFW_KEY_HOME;
        keyMap[ImGuiKey.End] = GLFW_KEY_END;
        keyMap[ImGuiKey.Insert] = GLFW_KEY_INSERT;
        keyMap[ImGuiKey.Delete] = GLFW_KEY_DELETE;
        keyMap[ImGuiKey.Backspace] = GLFW_KEY_BACKSPACE;
        keyMap[ImGuiKey.Space] = GLFW_KEY_SPACE;
        keyMap[ImGuiKey.Enter] = GLFW_KEY_ENTER;
        keyMap[ImGuiKey.Escape] = GLFW_KEY_ESCAPE;
        keyMap[ImGuiKey.KeyPadEnter] = GLFW_KEY_KP_ENTER;
        keyMap[ImGuiKey.A] = GLFW_KEY_A;
        keyMap[ImGuiKey.C] = GLFW_KEY_C;
        keyMap[ImGuiKey.V] = GLFW_KEY_V;
        keyMap[ImGuiKey.X] = GLFW_KEY_X;
        keyMap[ImGuiKey.Y] = GLFW_KEY_Y;
        keyMap[ImGuiKey.Z] = GLFW_KEY_Z;

        io.setKeyMap(keyMap);

        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = glfwGetClipboardString(Platform.Window.handle);
                return clipboardString != null ? clipboardString : "";
            }
        });

        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String str) {
                glfwSetClipboardString(Platform.Window.handle, str);
            }
        });

        // Mouse cursors mapping. Disable errors whilst setting due to X11.
        final var prevErrorCallback = glfwSetErrorCallback(null);
        mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
        mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        glfwSetErrorCallback(prevErrorCallback);

        // Our mouse update function expect PlatformHandle to be filled for the main viewport
        final var viewport = ImGui.getMainViewport();
        viewport.setPlatformHandle(Platform.Window.handle);

        if (Platform.IS_WINDOWS) {
            viewport.setPlatformHandleRaw(GLFWNativeWin32.glfwGetWin32Window(Platform.Window.handle));
        }

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            viewport.setPlatformUserData(new ViewportData());
            platform.setPlatformCreateWindow(new ViewportCreateWindowFunction());
            platform.setPlatformDestroyWindow(new ViewportDestroyWindowFunction());
            platform.setPlatformShowWindow(new ViewportShowWindowFunction());
            platform.setPlatformGetWindowPos(new ViewportGetWindowPosFunction());
            platform.setPlatformSetWindowPos(new ViewportSetWindowPosFunction());
            platform.setPlatformGetWindowSize(new ViewportGetWindowSizeFunction());
            platform.setPlatformSetWindowSize(new ViewportSetWindowSizeFunction());
            platform.setPlatformSetWindowTitle(new ViewportSetWindowTitleFunction());
            platform.setPlatformSetWindowFocus(new ViewportSetWindowFocusFunction());
            platform.setPlatformGetWindowFocus(new ViewportGetWindowFocusFunction());
            platform.setPlatformGetWindowMinimized(new ViewportGetWindowMinimizedFunction());
            platform.setPlatformSetWindowAlpha(new ViewportSetWindowAlphaFunction());
            platform.setPlatformRenderWindow(new ViewportRenderWindowFunction());
            platform.setPlatformSwapBuffers(new ViewportSwapBuffersFunction());
        }

        return true;
    }

    public void onEvent(Event event) {
        if (event.is(MouseEvent.class)) {
            if (event.is(MouseMovedEvent.class)) {
                final var position = ((MouseMovedEvent) event).getPosition();
                final var hasViewport = io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable);

                io.setMousePos(position.x() + (hasViewport ? Platform.Window.position.x : 0), position.y() + (hasViewport ? Platform.Window.position.y : 0));
                return;
            }

            if (event.is(MouseButtonEvent.class)) {
                final var button = ((MouseButtonEvent) event).getButton();
                if (button > ImGuiMouseButton.COUNT) return;
                io.setMouseDown(button, event.is(MousePressedEvent.class));
                event.setHandled();
                return;
            }

            if (event.is(MouseScrollEvent.class)) {
                final var offset = ((MouseScrollEvent) event).getOffset();
                io.setMouseWheelH(io.getMouseWheelH() + (float) offset.x());
                io.setMouseWheel(io.getMouseWheel() + (float) offset.y());
                event.setHandled();
                return;
            }
        }

        if (event.is(KeyboardEvent.class)) {
            io.setKeyCtrl(Platform.Keyboard.isActive(GLFW_KEY_LEFT_CONTROL) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_CONTROL));
            io.setKeyShift(Platform.Keyboard.isActive(GLFW_KEY_LEFT_SHIFT) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_SHIFT));
            io.setKeyAlt(Platform.Keyboard.isActive(GLFW_KEY_LEFT_ALT) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_ALT));
            io.setKeySuper(Platform.Keyboard.isActive(GLFW_KEY_LEFT_SUPER) || Platform.Keyboard.isActive(GLFW_KEY_RIGHT_SUPER));

            final var key = ((KeyboardEvent) event).getKey();

            if (event.is(KeyTypedEvent.class)) {
                io.addInputCharacter(key);
                event.setHandled();
                return;
            }

            io.setKeysDown(key, event.is(KeyPressedEvent.class));
            event.setHandled();
            return;
        }

        if (event.is(WindowEvent.class)) {
            if (event.is(WindowFocusEvent.class)) {
                io.addFocusEvent(event.is(WindowFocusGainedEvent.class));
                return;
            }

            final var viewport = ImGui.findViewportByPlatformHandle(event.getWindow());
            if (event.is(WindowCloseEvent.class)) {
                viewport.setPlatformRequestClose(true);
                return;
            }

            if (event.is(WindowPositionEvent.class)) {
                viewport.setPlatformRequestMove(true);
                return;
            }

            if (event.is(WindowSizeEvent.class)) {
                final var size = ((WindowSizeEvent) event).getSize();
                io.setDisplaySize(size.x(), size.y());
                viewport.setPlatformRequestResize(true);
                return;
            }

            if (event.is(WindowFramebufferSizeEvent.class)) {
                io.setDisplayFramebufferScale(
                        Platform.Window.framebuffer.x / (float) Platform.Window.size.x,
                        Platform.Window.framebuffer.y / (float) Platform.Window.size.y
                );
            }
        }
    }

    public void updateDeltaTime() {
        final double currentTime = glfwGetTime();
        io.setDeltaTime(time > 0.0 ? (float) (currentTime - time) : 1.0f / 60.0f);
        time = currentTime;
    }

    public void updateMouseCursor() {
        final boolean noCursorChange = io.hasConfigFlags(ImGuiConfigFlags.NoMouseCursorChange);
        final boolean cursorDisabled = glfwGetInputMode(Platform.Window.handle, GLFW_CURSOR) == GLFW_CURSOR_DISABLED;

        if (noCursorChange || cursorDisabled) {
            return;
        }

        final var imguiCursor = ImGui.getMouseCursor();

        for (int n = 0; n < platform.getViewportsSize(); n++) {
            final var windowPtr = platform.getViewports(n).getPlatformHandle();

            if (imguiCursor == ImGuiMouseCursor.None || io.getMouseDrawCursor()) {
                // Hide OS mouse cursor if imgui is drawing it or if it wants no cursor
                glfwSetInputMode(windowPtr, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            } else {
                // Show OS mouse cursor
                // FIXME-PLATFORM: Unfocused windows seems to fail changing the mouse cursor with GLFW 3.2, but 3.3 works here.
                glfwSetCursor(windowPtr, mouseCursors[imguiCursor] != 0 ? mouseCursors[imguiCursor] : mouseCursors[ImGuiMouseCursor.Arrow]);
                glfwSetInputMode(windowPtr, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            }
        }
    }

    public void updateGamepads() {
        if (!io.hasConfigFlags(ImGuiConfigFlags.NavEnableGamepad))
            return;

        io.setNavInputs(navInputs);

        final var buttons = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
        if (buttons == null) return;
        final var buttonsCount = buttons.limit();

        final var axis = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
        if (axis == null) return;
        final var axisCount = axis.limit();

        mapButton(ImGuiNavInput.Activate, 0, buttons, buttonsCount);   // Cross / A
        mapButton(ImGuiNavInput.Cancel, 1, buttons, buttonsCount);     // Circle / B
        mapButton(ImGuiNavInput.Menu, 2, buttons, buttonsCount);       // Square / X
        mapButton(ImGuiNavInput.Input, 3, buttons, buttonsCount);      // Triangle / Y
        mapButton(ImGuiNavInput.DpadLeft, 13, buttons, buttonsCount);  // D-Pad Left
        mapButton(ImGuiNavInput.DpadRight, 11, buttons, buttonsCount); // D-Pad Right
        mapButton(ImGuiNavInput.DpadUp, 10, buttons, buttonsCount);    // D-Pad Up
        mapButton(ImGuiNavInput.DpadDown, 12, buttons, buttonsCount);  // D-Pad Down
        mapButton(ImGuiNavInput.FocusPrev, 4, buttons, buttonsCount);  // L1 / LB
        mapButton(ImGuiNavInput.FocusNext, 5, buttons, buttonsCount);  // R1 / RB
        mapButton(ImGuiNavInput.TweakSlow, 4, buttons, buttonsCount);  // L1 / LB
        mapButton(ImGuiNavInput.TweakFast, 5, buttons, buttonsCount);  // R1 / RB

        mapAnalog(ImGuiNavInput.LStickLeft, 0, -0.3f, -0.9f, axis, axisCount);
        mapAnalog(ImGuiNavInput.LStickRight, 0, +0.3f, +0.9f, axis, axisCount);
        mapAnalog(ImGuiNavInput.LStickUp, 1, +0.3f, +0.9f, axis, axisCount);
        mapAnalog(ImGuiNavInput.LStickDown, 1, -0.3f, -0.9f, axis, axisCount);

        if (axisCount > 0 && buttonsCount > 0)  io.addBackendFlags(ImGuiBackendFlags.HasGamepad);
        else                                    io.removeBackendFlags(ImGuiBackendFlags.HasGamepad);
    }

    private void mapButton(final int navNo, final int buttonNo, final ByteBuffer buttons, final int buttonsCount) {
        if (buttonsCount > buttonNo && buttons.get(buttonNo) == GLFW_PRESS)
            io.setNavInputs(navNo, 1.0f);
    }

    private void mapAnalog(final int navNo, final int axisNo, final float v0, final float v1, final FloatBuffer axis, final int axisCount) {
        var v = axisCount > axisNo ? axis.get(axisNo) : v0;
        v = (v - v0) / (v1 - v0);

        if (v > 1.0f) v = 1.0f;
        if (io.getNavInputs(navNo) < v) io.setNavInputs(navNo, v);
    }

    public void terminate() {
        for (int i = 0; i < ImGuiMouseCursor.COUNT; i++)
            glfwDestroyCursor(mouseCursors[i]);
    }
}
