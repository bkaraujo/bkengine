package br.bkraujo.core.scene.camera;

import br.bkraujo.engine.scene.camera.Camera;
import br.bkraujo.utils.Vectors;
import org.joml.*;

public abstract class AbstractCamera implements Camera {
    /** Position and Direction multiplication matrix */
    protected final Matrix4f view = new Matrix4f();

    /** Field of View multiplication matrix */
    protected final Matrix4f projection = new Matrix4f();

    /** View Projection matrix */
    protected final Matrix4f viewProjection = new Matrix4f();

    /** position */
    protected final Vector3f position = new Vector3f();

    /** zNear (-1) and zFar (1) */
    protected final Vector2f zBounds = Vectors.of(-1f, 1f);

    protected AbstractCamera(){
        viewProjection.set(projection).mul(view);
    }

    protected void doAfterSetPosition(){}
    public final void setPosition(Vector3fc position) { this.position.set(position); doAfterSetPosition(); }
    public final Vector3f getPosition() { return position; }

    public final Matrix4fc getViewMatrix() { return new Matrix4f(view); }
    public final Matrix4fc getProjectionMatrix() { return new Matrix4f(projection); }
    public final Matrix4fc getViewProjection() { return new Matrix4f(viewProjection); }

}
