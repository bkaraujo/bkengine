package br.bkraujo.engine.graphics.intrinsics;

import br.bkraujo.engine.Lifecycle;

public interface Buffer extends Lifecycle {

    default boolean initialize(){return true;}
    default void terminate(){}
}
