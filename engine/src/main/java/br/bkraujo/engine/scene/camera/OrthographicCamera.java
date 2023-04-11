package br.bkraujo.engine.scene.camera;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.scene.camera.AbstractCamera;
import br.bkraujo.utils.Vectors;
import org.joml.Vector4f;
import org.joml.Vector4fc;

public final class OrthographicCamera extends AbstractCamera {
    protected float rotation;
    private final Vector4fc viewport;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        this(Vectors.of(left, right, bottom, top));
    }

    public OrthographicCamera(Vector4f area) {
        final var ratio = Platform.window.get(Platform.Window.main).ratio;

        viewport = Vectors.of(area.x,
                area.y,
                (area.z * ratio.vertical) / ratio.horizontal,
                (area.w * ratio.vertical) / ratio.horizontal
        );

        projection.setOrtho(viewport.x(), viewport.y(), viewport.z(), viewport.w(), zBounds.x, zBounds.y);
        calculateViewMatrix();
    }


    public final Vector4fc getViewport() {
        return viewport;
    }

    public float getRotation() { return rotation; }
    public void setRotation(float x) { rotation = x; calculateViewMatrix(); }

    protected void doAfterSetPosition() { calculateViewMatrix(); }
    private void calculateViewMatrix() {
        view.identity().translate(position).rotateZ(rotation).invert();
        viewProjection.set(projection).mul(view);
    }
}
