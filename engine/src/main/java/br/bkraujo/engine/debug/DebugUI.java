package br.bkraujo.engine.debug;

import br.bkraujo.engine.scene.actor.Behaviour;
import imgui.ImGui;
import org.lwjgl.opengl.GL11;

public class DebugUI extends Behaviour {
    private boolean wireframe = true;

    @Override
    public void onGui() {
        if (ImGui.begin("Debugger")) {
            if (ImGui.button("Wireframe")) {
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, wireframe ? GL11.GL_LINE : GL11.GL_FILL);
                wireframe = !wireframe;
            }

            ImGui.end();
        }
    }
}
