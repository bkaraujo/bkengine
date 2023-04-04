package br.bkraujo.engine.scene.actor;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import br.bkraujo.engine.renderer.OnGui;
import br.bkraujo.engine.renderer.OnRender;
import br.bkraujo.engine.renderer.Renderer;
import br.bkraujo.engine.scene.OnUpdate;
import br.bkraujo.engine.scene.actor.component.MeshComponent;
import br.bkraujo.engine.scene.actor.component.TransformComponent;
import br.bkraujo.utils.Matrices;
import br.bkraujo.utils.Reflections;

import java.util.ArrayList;
import java.util.List;

import static br.bkraujo.engine.Logger.fatal;
import static br.bkraujo.engine.Logger.warn;

public final class Actor implements Lifecycle, OnEvent, OnUpdate, OnRender, OnGui {

    private final List<Component> components = new ArrayList<>();
    private final List<Behaviour> behaviours = new ArrayList<>();
    private final TransformComponent transform = new TransformComponent();

    private String name;

    public Actor() { this("Actor"); }
    public Actor(String name) { setName(name); }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public <T extends Component> T addComponent(Class<T> component) {
        final var instance = Reflections.instantiate(component);
        components.add(instance);

        return instance;
    }

    public <T extends Component> T getComponent(Class<T> klass) {
        for (var candidate : components)
            if (candidate.getClass().isAssignableFrom(klass))
                return klass.cast(candidate);

        return null;
    }

    public <T extends Behaviour> T addBehaviour(Class<T> behaviour) {
        final var instance = Reflections.instantiate(behaviour);
        Reflections.set(Behaviour.class, instance, "actor", this);
        Reflections.set(Behaviour.class, instance, "transform", transform);

        behaviours.add(instance);
        return instance;
    }

    public <T extends Behaviour> T getBehaviour(Class<T> klass) {
        for (var candidate : behaviours)
            if (candidate.getClass().isAssignableFrom(klass))
                return klass.cast(candidate);

        return null;
    }

    public boolean initialize() {
        for (var behaviour : behaviours)
            if (!behaviour.initialize())
                fatal("Failed to initialize Behaviour [%s]", behaviour.getClass().getCanonicalName());

        for (var component : components)
            if (!component.initialize())
                fatal("Failed to initialize Component [%s]", component.getClass().getCanonicalName());

        return true;
    }

    public void onUpdate(float delta) { for(var behaviour : behaviours) behaviour.onUpdate(delta); }

    public void onEvent(Event event) {
        for(var behaviour : behaviours) {
            behaviour.onEvent(event);

            if (event.isHandled()) return;
        }
    }

    public void onRender() {
        final var component = getComponent(MeshComponent.class);
        if (component == null) return;

        if (component.array.getVertexes().isEmpty()) {
            warn("[%s] MeshComponent has no vertex", getName());
            return;
        }

        final var matrix = Matrices.translate(transform.translation);
        Renderer.submit(component.shader, component.array, matrix);
    }

    public void onGui() { for(var behaviour : behaviours) behaviour.onGui(); }
    public void terminate() { for (var behaviour : behaviours) behaviour.terminate(); }
}
