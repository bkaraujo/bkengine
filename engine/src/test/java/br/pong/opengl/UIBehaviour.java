package br.pong.opengl;

import br.bkraujo.engine.Application;
import br.bkraujo.engine.renderer.Renderer;
import br.bkraujo.engine.scene.actor.Behaviour;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;

public class UIBehaviour extends Behaviour {
    private static final String FPS = "FPS: %s";
    private static final String UPS = "UPS: %s";
    private static final String FRAME = "Frame: %s";
    private static final String FRAME_TIME = "Frame (Delta Time): %s ms";
    private static final String RENDERER_DRAW = "Frame (Draw Calls): %s";

    @Override
    public void onGui() {
        if (ImGui.begin("Renderer", ImGuiWindowFlags.AlwaysAutoResize)) {

            ImGui.textUnformatted(String.format(FPS, Application.fps));
            ImGui.textUnformatted(String.format(UPS, Application.ups));
            ImGui.spacing();
            ImGui.textUnformatted(String.format(FRAME, Application.frameCount));
            ImGui.textUnformatted(String.format(FRAME_TIME, Application.frameTime));
            ImGui.textUnformatted(String.format(RENDERER_DRAW, Renderer.drawCalls));
        }

        ImGui.end();

    }

}
