package br.bkraujo.editor.ui;

import br.bkraujo.engine.renderer.Renderer;
import br.bkraujo.engine.scene.actor.Behaviour;
import br.bkraujo.game.Game;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;

public final class RendererUI extends Behaviour {
    private static final String FPS = "FPS: %s";
    private static final String UPS = "UPS: %s";
    private static final String FRAME = "Frame: %s";
    private static final String FRAME_TIME = "Frame (Delta Time): %s ms";
    private static final String RENDERER_DRAW = "Frame (Draw Calls): %s";

    public void onGui() {
        if (ImGui.begin("Renderer", ImGuiWindowFlags.AlwaysAutoResize)) {

            ImGui.textUnformatted(String.format(FPS, Game.fps));
            ImGui.textUnformatted(String.format(UPS, Game.ups));
            ImGui.spacing();
            ImGui.textUnformatted(String.format(FRAME, Game.frameCount));
            ImGui.textUnformatted(String.format(FRAME_TIME, Game.frameTime));
            ImGui.textUnformatted(String.format(RENDERER_DRAW, Renderer.drawCalls));
        }

        ImGui.end();

        ImGui.showDemoWindow();
    }

}
