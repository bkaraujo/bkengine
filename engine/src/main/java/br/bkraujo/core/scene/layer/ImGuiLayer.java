package br.bkraujo.core.scene.layer;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.Viewport;
import br.bkraujo.core.renderer.imgui.Renderer;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.scene.layer.LayerType;
import br.bkraujo.utils.FileUtils;
import br.bkraujo.utils.Reflections;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.internal.ImGuiContext;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Path;

import static br.bkraujo.engine.Logger.trace;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public final class ImGuiLayer extends AbstractLayer {
    private ImGuiContext context;
    private Viewport viewport;
    private Renderer renderer;

    public LayerType getType() { return LayerType.UI; }

    protected boolean doInitialize() {
        if (!copyLibrary()) return false;
        createContext();

        final var io = ImGui.getIO();
        final var platform = ImGui.getPlatformIO();

        viewport = new Viewport(io, platform);
        renderer = new Renderer(io, platform);

        if (!viewport.initialize()) return false;
        return renderer.initialize();
    }

    private boolean copyLibrary() {
        final var fileName = "imgui" + (Platform.IS_WINDOWS ? ".dll" : Platform.IS_APPLE ? ".dylib" : ".so");
        final var target = Path.of(Platform.IS_WINDOWS ? System.getenv("LOCALAPPDATA") + "/bkengine/": System.getProperty("java.io.tmpdir"), fileName);
        trace("Creating library %s", target.toString());

        // Points the loader to the external file
        System.setProperty("imgui.library.name", fileName);
        System.setProperty("imgui.library.path", target.getParent().toString());

        if (FileUtils.exists(target)) return true;
        if (!FileUtils.createDirectory(target.getParent())) return false;

        final var loader = Reflections.classLoader();
        final var stream = loader.getResourceAsStream("imgui/" + target.getFileName());
        if (!FileUtils.copyTo(stream, target)) return false;


        return true;
    }

    private void createContext() {
        trace("Creating ImGui Context");
        context = ImGui.createContext();
        ImGui.setCurrentContext(context);
    }

    protected void doAfterEvent(Event event) {
        viewport.onEvent(event);
    }

    protected void doBeforeGui() {
        viewport.updateDisplay();
        viewport.updateMonitors();
        viewport.updateDeltaTime();
        viewport.updateMouseCursor();

        ImGui.newFrame();
    }

    protected void doAfterGui() {
        ImGui.render();
        renderer.render(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final var context = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(context);
        }
    }

    protected void doTerminate() {
        if (renderer!= null) renderer.terminate();
        if (viewport != null) viewport.terminate();
        if (context != null) ImGui.destroyContext(context);
    }
}
