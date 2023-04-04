package br.pong.opengl;

import br.bkraujo.engine.platform.Input;
import br.bkraujo.engine.scene.actor.Behaviour;
import br.bkraujo.engine.scene.actor.component.VelocityComponent;

public class PaddleController extends Behaviour {

    public int up;
    public int down;
    private VelocityComponent velocityComponent;

    public boolean initialize() {
        return (velocityComponent = getComponent(VelocityComponent.class)) != null;
    }

    public void onUpdate(float delta) {
        if (Input.isKeyActive(up)) {
            transform.translation.add(0, velocityComponent.velocity * delta, 0);
            return;
        }

        if (Input.isKeyActive(down)) {
            transform.translation.add(0, -velocityComponent.velocity * delta, 0);
        }
    }
}
