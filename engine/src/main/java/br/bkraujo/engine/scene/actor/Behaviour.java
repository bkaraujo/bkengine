package br.bkraujo.engine.scene.actor;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import br.bkraujo.engine.graphics.TransformComponent;
import br.bkraujo.engine.renderer.OnGui;
import br.bkraujo.engine.scene.OnUpdate;

public abstract class Behaviour implements Lifecycle, OnUpdate, OnEvent, OnGui {
    private final Actor actor = new Actor();
    protected final TransformComponent transform = new TransformComponent();

    public final String getName() { return getClass().toGenericString(); }
    protected final <T extends Component> T getComponent(Class<T> type) { return actor.getComponent(type); }

    public boolean initialize() { return true; }
    public void terminate() {}

    public void onGui(){}
    public void onEvent(Event event) {}
    public void onUpdate(float delta) {}
}
