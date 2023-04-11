package br.bkraujo.engine.core.platform;

import org.joml.Vector2fc;

public interface Monitor {

    long getHandle();

    Vector2fc getSize();
    Vector2fc getScale();
    Vector2fc getPosition();
}
