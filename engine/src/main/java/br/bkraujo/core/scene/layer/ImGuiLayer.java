package br.bkraujo.core.scene.layer;

import br.bkraujo.core.GameConfiguration;
import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.imgui.Viewport;
import br.bkraujo.core.renderer.imgui.Renderer;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.scene.layer.LayerType;
import br.bkraujo.utils.FileUtils;
import br.bkraujo.utils.Reflections;
import imgui.ImGui;
import imgui.internal.ImGuiContext;

import java.nio.file.Path;

import static br.bkraujo.engine.Logger.trace;

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
        final Path target;

        if (Platform.IS_WINDOWS) {
            target = Path.of(System.getenv("LOCALAPPDATA"), GameConfiguration.company, GameConfiguration.name, fileName);
        } else {
            target = Path.of(System.getProperty("user.home"), GameConfiguration.company, GameConfiguration.name, fileName);
        }

        trace("Creating library %s", target.toString());

        // Points the loader to the external file
        System.setProperty("imgui.library.name", fileName);
        System.setProperty("imgui.library.path", target.getParent().toString());

        if (FileUtils.exists(target)) return true;
        if (!FileUtils.createDirectory(target.getParent())) return false;

        final var loader = Reflections.classLoader();
        final var stream = loader.getResourceAsStream("imgui/" + target.getFileName());
        return FileUtils.copyTo(stream, target);
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
        viewport.beginFrame();
        ImGui.newFrame();
    }

    protected void doAfterGui() {
        ImGui.render();
        renderer.render(ImGui.getDrawData());
        viewport.endFrame();
    }

    protected void doTerminate() {
        if (renderer!= null) renderer.terminate();
        if (viewport != null) viewport.terminate();
        if (context != null) ImGui.destroyContext(context);
    }
}
