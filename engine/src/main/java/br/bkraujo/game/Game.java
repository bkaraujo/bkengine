package br.bkraujo.game;

import br.bkraujo.core.graphics.Graphics;
import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.temporal.Time;
import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.event.OnEvent;
import br.bkraujo.engine.platform.Window;
import br.bkraujo.engine.scene.Scene;
import br.bkraujo.utils.Reflections;

import static br.bkraujo.engine.Logger.debug;
import static br.bkraujo.engine.Logger.error;

public abstract class Game implements Lifecycle, OnEvent {
    public static int fps = 0;
    public static int ups = 0;
    public static float frameTime = Float.MIN_VALUE;
    public static long frameCount;

    private static boolean running = true;
    public static boolean isRunning() { return running; }
    public static void setShouldStop() { running = false; }

    private Scene scene;
    private Window window;
    private Platform platform;

    public abstract String getCompany();
    public abstract String getName();

    protected boolean doInitialize() { return true; }
    public final boolean initialize() {
        scene = Reflections.instantiate(getScene());
        if (scene == null) { error("Failed to create game scene"); return false; }
        if (!scene.initialize()) { error("Scene failed to initialize"); return false; }
        if (!doInitialize()) { error("Game failed to initialize"); return false; }
        return true;
    }

    /** Game starting scene */
    public abstract Class<? extends Scene> getScene();
    public PlatformConfiguration getPlatformConfiguration() { return new PlatformConfiguration(); }
    public GraphicsConfiguration getGraphicsConfiguration() { return new GraphicsConfiguration(); }
    public final void onEvent(Event event) { scene.onEvent(event); }

    void setWindow(Window window) { this.window = window; }
    final void setPlatform(Platform platform) { this.platform = platform; }

    final void run() {
        final var graphics = Graphics.context();

        final var slice = Time.NANOSECOND / 75;

        var accumulator = 0f;

        var fps = 0;
        var ups = 0;

        var lastTime = Time.nanos();
        long timer = Time.millis();
        window.show();
        debug("Starting Game Loop");
        while (running) {
            if (Platform.window.get(Platform.Window.main).maximized) { platform.pollEvents(); continue; }

            final var now = Time.nanos();
            final var delta = now - lastTime;
            lastTime += delta;

            accumulator += delta;
            while ((accumulator) > slice) {
                scene.onUpdate(slice / Time.NANOSECOND);
                accumulator -= slice;
                ups++;
            }

            graphics.clear();
            scene.onRender();
            scene.onGui();
            graphics.swap();

            fps++;
            frameCount++;

            final var current = Time.millis();
            if (current - timer > Time.SECOND) {
                timer = current;

                Game.fps = fps;
                Game.ups = ups;
                ups = fps = 0;
            }

            frameTime = (Time.nanos() - now) / Time.MILLISECOND;
            platform.pollEvents();
        }

        window.hide();
    }

    protected void doTerminate(){}
    public final void terminate() {
        if (scene != null) scene.terminate();
        doTerminate();
    }
}
