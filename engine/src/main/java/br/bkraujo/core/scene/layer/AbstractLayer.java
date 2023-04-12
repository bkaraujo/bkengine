package br.bkraujo.core.scene.layer;

import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.scene.actor.Actor;
import br.bkraujo.engine.scene.layer.Layer;

import java.util.ArrayList;
import java.util.List;

import static br.bkraujo.engine.Logger.trace;
import static br.bkraujo.engine.Logger.warn;

public abstract class AbstractLayer implements Layer {
    protected final List<Actor> actors = new ArrayList<>();

    protected boolean doInitialize() { return true; }
    public boolean initialize() {
        trace("%s :: Initializing", getName());
        for (var actor : actors) {
            trace("%s :: Initializing actor \"%s\"", getName(), actor.getName());
            if (!actor.initialize())
                return false;
        }

        return doInitialize();
    }

    protected void doTerminate() {}
    public final void terminate() {
        trace("%s :: Terminating", getName());
        doTerminate();
        for(var actor : actors) {
            trace("%s :: Terminating actor \"%s\"", getName(), actor.getName());
            actor.terminate();
        }
    }

    public Actor newActor() {
        return newActor("Actor");
    }

    public Actor newActor(String name) {
        final var actor = new Actor(name);
        actors.add(actor);
        return actor;
    }

    public void remove(Actor actor) {
        if (actor == null) { warn("[%s] Cannot remove null actor", getName()); return; }
        actor.terminate();
        actors.remove(actor);
    }

    protected void doBeforeUpdate(float delta){}
    protected void doUpdate(float delta) {
        for (var actor : actors)
            actor.onUpdate(delta);
    }
    protected void doAfterUpdate(float delta){}
    public final void onUpdate(float delta) {
        doBeforeUpdate(delta);
        doUpdate(delta);
        doAfterUpdate(delta);
    }

    protected void doBeforeEvent(Event event) {}
    protected void doAfterEvent(Event event) {}
    public final void onEvent(Event event) {
        doBeforeEvent(event);
        for (var actor : actors) {
            actor.onEvent(event);
            if (event.isHandled()) return;
        }
        doAfterEvent(event);
    }

    protected void doBeforeRender(){}
    protected void doAfterRender(){}
    public final void onRender() {
        doBeforeRender();
        for (var actor : actors)
            actor.onRender();
        doAfterRender();
    }

    protected void doBeforeGui(){}
    protected void doAfterGui(){}
    public void onGui() {
        doBeforeGui();
        for (var actor : actors)
            actor.onGui();
        doAfterGui();
    }
}
