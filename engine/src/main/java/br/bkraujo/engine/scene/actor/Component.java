package br.bkraujo.engine.scene.actor;

import br.bkraujo.engine.Lifecycle;

public interface Component extends Lifecycle {

    default boolean initialize() { return true; }
    default void terminate() {}

}
