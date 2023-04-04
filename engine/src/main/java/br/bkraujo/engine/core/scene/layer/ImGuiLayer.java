package br.bkraujo.engine.core.scene.layer;

import br.bkraujo.engine.core.platform.imgui.Viewport;
import br.bkraujo.engine.core.renderer.imgui.Renderer;
import br.bkraujo.engine.event.Event;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.internal.ImGuiContext;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public final class ImGuiLayer extends AbstractLayer {
    private ImGuiContext context;
    private Viewport viewport;
    private Renderer renderer;

    protected boolean doInitialize() {
        context = ImGui.createContext();
        ImGui.setCurrentContext(context);

        final var io = ImGui.getIO();
        final var platform = ImGui.getPlatformIO();

        viewport = new Viewport(io, platform);
        renderer = new Renderer(io, platform);

        if (!viewport.initialize()) return false;
        return renderer.initialize();
    }

    protected void doAfterEvent(Event event) {
        viewport.onEvent(event);
    }

    protected void doBeforeGui() {
        viewport.updateGamepads();
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
