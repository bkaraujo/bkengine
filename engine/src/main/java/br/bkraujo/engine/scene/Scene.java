package br.bkraujo.engine.scene;

import br.bkraujo.core.scene.layer.ImGuiLayer;
import br.bkraujo.core.scene.layer.WorldLayer;
import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import br.bkraujo.engine.renderer.OnGui;
import br.bkraujo.engine.renderer.OnRender;
import br.bkraujo.engine.renderer.Renderer;
import br.bkraujo.engine.scene.camera.Camera;
import br.bkraujo.engine.scene.layer.Layer;
import br.bkraujo.engine.scene.layer.LayerType;

import static br.bkraujo.engine.Logger.debug;

public abstract class Scene implements Lifecycle, OnUpdate, OnEvent, OnRender, OnGui {
    private final Layer world = new WorldLayer();
    private final Layer gui = new ImGuiLayer();

    protected final Layer getLayer(LayerType type) {
        if (type == LayerType.WORLD) return world;
        return gui;
    }

    public abstract Camera getCamera();

    /** Initialize the scene details */
    protected boolean doInitialize() { return true; }
    public final boolean initialize() {
        debug("Initializing Scene %s", getClass().getCanonicalName());
        if (!doInitialize()) return false;
        if (!world.initialize()) return false;
        return gui.initialize();
    }

    protected void doBeforeUpdate(float delta) {}
    protected void doAfterUpdate(float delta) {}
    public final void onUpdate(float delta) {
        doBeforeUpdate(delta);
        world.onUpdate(delta);
        gui.onUpdate(delta);
        doAfterUpdate(delta);
    }

    protected void doBeforeEvent(Event event) {}
    protected void doAfterEvent(Event event) {}
    public final void onEvent(Event event) {
        doBeforeEvent(event);
        gui.onEvent(event);
        if (event.isHandled()) return;
        world.onEvent(event);
        doAfterEvent(event);
    }

    public final void onRender() {
        Renderer.beginScene(getCamera());
        world.onRender();
        gui.onRender();
        Renderer.endScene();
        Renderer.flush();
    }

    public final void onGui() {
        gui.onGui();
    }

    /** Terminate the scene details */
    protected void doTerminate() {}
    public void terminate() {
        debug("Terminating Scene %s", getClass().getCanonicalName());
        gui.terminate();
        world.terminate();
        doTerminate();
    }
}
