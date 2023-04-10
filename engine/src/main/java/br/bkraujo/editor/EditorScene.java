package br.bkraujo.editor;

import br.bkraujo.editor.ui.RendererUI;
import br.bkraujo.engine.scene.Scene;
import br.bkraujo.engine.scene.camera.Camera;
import br.bkraujo.engine.scene.camera.OrthographicCamera;
import br.bkraujo.engine.scene.layer.LayerType;

public class EditorScene extends Scene {
    private final Camera camera = new OrthographicCamera(10, -10, 10, -10);
    public Camera getCamera() { return camera; }


    @Override
    protected boolean doInitialize() {
        final var layer = getLayer(LayerType.UI);
        layer.newActor(RendererUI.class.getSimpleName()).addBehaviour(RendererUI.class);

        return true;
    }
}
