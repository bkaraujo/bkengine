package br.bkraujo.editor;

import br.bkraujo.engine.scene.Scene;
import br.bkraujo.engine.scene.camera.Camera;
import br.bkraujo.engine.scene.camera.OrthographicCamera;

public class EditorScene extends Scene {
    private final Camera camera = new OrthographicCamera(10, -10, 10, -10);
    public Camera getCamera() { return camera; }
}
