package br.pong.opengl;

import br.bkraujo.engine.physics.VelocityComponent;
import br.bkraujo.engine.platform.Input;
import br.bkraujo.engine.scene.actor.Behaviour;
import org.joml.Vector4fc;

public class PaddleController extends Behaviour {
    public int up;
    public int down;

    private float floor = 0f;
    private float ceiling = 0f;
    public Vector4fc viewport;
    private VelocityComponent velocityComponent;

    public boolean initialize() {
        if (viewport == null) return false;

        floor = viewport.z() + Geometry.PADDLE_HEIGHT;
        ceiling = viewport.w() - Geometry.PADDLE_HEIGHT;

        return (velocityComponent = getComponent(VelocityComponent.class)) != null;
    }

    public void onUpdate(float delta) {
        if (Input.isKeyActive(up)) {
            transform.translation.add(0, velocityComponent.velocity * delta, 0);
            if (transform.translation.get(1) >= ceiling)
                transform.translation.setComponent(1, ceiling);
            return;
        }

        if (Input.isKeyActive(down)) {
            transform.translation.add(0, -velocityComponent.velocity * delta, 0);
            if (transform.translation.get(1) <= floor)
                transform.translation.setComponent(1, floor);
        }
    }
}
