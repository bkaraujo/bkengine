package br.bkraujo.core.platform.imgui;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.event.FramebufferEvent;
import br.bkraujo.core.platform.event.KeyboardEvent;
import br.bkraujo.core.platform.event.MouseEvent;
import br.bkraujo.core.platform.event.WindowEvent;
import br.bkraujo.core.platform.glfw.GLFWUtils;
import br.bkraujo.core.platform.imgui.event.FramebufferEventHandler;
import br.bkraujo.core.platform.imgui.event.KeyboardEventHandler;
import br.bkraujo.core.platform.imgui.event.MouseEventHandler;
import br.bkraujo.core.platform.imgui.event.WindowEventHandler;
import br.bkraujo.core.platform.imgui.functions.*;
import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiPlatformIO;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseCursor;

import static br.bkraujo.engine.Logger.trace;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWNativeWin32.glfwGetWin32Window;

public final class Viewport implements Lifecycle, OnEvent {
    private final ImGuiIO io;
    private final ImGuiPlatformIO platform;
    private final MouseEventHandler mouse;
    private final WindowEventHandler window;
    private final KeyboardEventHandler keyboard;
    private final FramebufferEventHandler framebuffer;

    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];

    private double time = 0.0;

    public Viewport(ImGuiIO io, ImGuiPlatformIO platform) {
        this.io = io;
        this.platform = platform;

        mouse = new MouseEventHandler(io, platform);
        window = new WindowEventHandler(io, platform);
        keyboard = new KeyboardEventHandler(io, platform);
        framebuffer = new FramebufferEventHandler(io, platform);
    }

    public boolean initialize() {
        trace("Initializing GLFW integration");
        io.setBackendPlatformName(getClass().getCanonicalName());
        io.setConfigFlags(ImGuiConfigFlags.ViewportsEnable | ImGuiConfigFlags.DockingEnable);
        io.addBackendFlags(
                // We can honor GetMouseCursor() values (optional)
                ImGuiBackendFlags.HasMouseCursors |
                        // We can honor io.WantSetMousePos requests (optional, rarely used)
                        ImGuiBackendFlags.HasSetMousePos |
                        // We can create multi-viewports on the Platform side (optional)
                        ImGuiBackendFlags.PlatformHasViewports |
                        // We can set io.MouseHoveredViewport correctly (optional, not easy)
                        (GLFWUtils.hasMousePassThrough ? ImGuiBackendFlags.HasMouseHoveredViewport : ImGuiBackendFlags.None)
        );

        initializeKeyboardMapping();
        initializeClipBoard();
        initializeCursors();
        initializeViewport();
        initializeDisplay();

        return true;
    }

    private void initializeKeyboardMapping() {
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
    }

    private void initializeClipBoard() {
        io.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                final String clipboardString = glfwGetClipboardString(Platform.Window.main);
                return clipboardString != null ? clipboardString : "";
            }
        });

        io.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String str) {
                glfwSetClipboardString(Platform.Window.main, str);
            }
        });
    }

    private void initializeCursors() {
        // Mouse cursors mapping. Disable errors whilst setting due to X11.
        final var prevErrorCallback = glfwSetErrorCallback(null);
        mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
        mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
        if (GLFWUtils.hasNewCursors) {
            mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_RESIZE_ALL_CURSOR);
            mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_RESIZE_NESW_CURSOR);
            mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_RESIZE_NWSE_CURSOR);
            mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_NOT_ALLOWED_CURSOR);
        } else {
            mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
            mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
            mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
            mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
        }
        glfwSetErrorCallback(prevErrorCallback);
    }

    private void initializeViewport() {
        // Our mouse update function expect PlatformHandle to be filled for the main viewport
        final var viewport = ImGui.getMainViewport();
        viewport.setPlatformHandle(Platform.Window.main);

        if (Platform.IS_WINDOWS) {
            viewport.setPlatformHandleRaw(glfwGetWin32Window(Platform.Window.main));
        }

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            viewport.setPlatformUserData(new ViewportData());
            ((ViewportData) viewport.getPlatformUserData()).window = Platform.Window.main;

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
    }

    private void initializeDisplay() {
        final var size = Platform.window.get(Platform.Window.main).size;
        final var framebuffer = Platform.window.get(Platform.Window.main).framebuffer;

        io.setDisplaySize(size.x, size.y);
        io.setDisplayFramebufferScale(framebuffer.x / (float) size.x, framebuffer.y / (float) size.y);
    }

    @Override
    public void onEvent(Event event) {
//        if (event.getWindow() == Platform.Window.main) return;

        if (event.is(MouseEvent.class)) mouse.onEvent(event);
        if (event.is(KeyboardEvent.class)) keyboard.onEvent(event);
        if (event.is(WindowEvent.class)) window.onEvent(event);
        if (event.is(FramebufferEvent.class)) framebuffer.onEvent(event);
    }

    public void updateDeltaTime() {
        final double currentTime = glfwGetTime();
        io.setDeltaTime(time > 0.0 ? (float) (currentTime - time) : 1.0f / 60.0f);
        time = currentTime;
    }

    public void updateMouseCursor() {
        final boolean noCursorChange = io.hasConfigFlags(ImGuiConfigFlags.NoMouseCursorChange);
        final boolean cursorDisabled = glfwGetInputMode(Platform.Window.main, GLFW_CURSOR) == GLFW_CURSOR_DISABLED;

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

    private int monitors = 0;
    public void updateMonitors() {
        if (Platform.monitors.size() == monitors) return;
        monitors = Platform.monitors.size();

        platform.resizeMonitors(0);
        for (var monitor : Platform.monitors) {
            final var position = monitor.getPosition();
            final var size = monitor.getSize();
            final var scale = monitor.getScale();
            platform.pushMonitors(
                    position.x(), position.y(), size.x(), size.y(),
                    position.x(), position.y(), size.x(), size.y(),
                    scale.x()
            );
        }
    }

    public void terminate() {
        trace("Terminating GLFW integration");
        for (int i = 0; i < ImGuiMouseCursor.COUNT; i++)
            glfwDestroyCursor(mouseCursors[i]);
    }
}
