package br.bkraujo.engine.scene.camera;

import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.joml.Vector4fc;

public interface Camera {

    Vector4fc getViewport();
    void setPosition(Vector3fc position);
    Matrix4fc getViewProjection();

}
