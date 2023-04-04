package br.bkraujo.engine.scene.layer;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.event.OnEvent;
import br.bkraujo.engine.renderer.OnGui;
import br.bkraujo.engine.renderer.OnRender;
import br.bkraujo.engine.scene.OnUpdate;
import br.bkraujo.engine.scene.actor.Actor;

public interface Layer extends Lifecycle, OnUpdate, OnEvent, OnRender, OnGui {

    default String getName() { return getType().name(); }
    LayerType getType();

    Actor newActor();
    Actor newActor(String name);
    void remove(Actor actor);
}
