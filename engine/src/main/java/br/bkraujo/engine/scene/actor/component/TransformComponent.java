package br.bkraujo.engine.scene.actor.component;

import br.bkraujo.engine.scene.actor.Component;
import br.bkraujo.utils.Vectors;
import org.joml.Vector3f;

public final class TransformComponent implements Component {
    public final Vector3f scale = Vectors.of(0f,0,0);
    public final Vector3f rotation = Vectors.of(0f,0,0);
    public final Vector3f translation = Vectors.of(0f,0,0);
}
