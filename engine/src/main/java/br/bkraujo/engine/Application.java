package br.bkraujo.engine;

import br.bkraujo.engine.core.graphics.Graphics;
import br.bkraujo.engine.core.graphics.GraphicsContext;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.core.temporal.Time;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.platform.Window;
import br.bkraujo.engine.scene.Scene;
import br.bkraujo.game.Game;
import br.bkraujo.utils.Reflections;

import java.util.concurrent.atomic.AtomicBoolean;

import static br.bkraujo.engine.Logger.*;

public final class Application implements Lifecycle {
    private static GraphicsContext graphics;
    private static boolean running = true;
    private static AtomicBoolean shutdown = new AtomicBoolean();
    private static Scene scene;
    private static Game game;

    public static int fps = 0;
    public static int ups = 0;
    public static float frameTime = Float.MIN_VALUE;

    public static long frameCount;

    public static boolean isRunning() { return running; }
    public static boolean isShutdown() { return shutdown.getAcquire(); }

    public static void setShouldStop() { running = false; }

    public static void onEvent(Event event) {
        if (event == null) { error("Event is null"); return ; }
        if (event.isHandled()) { warn("Posting handled event"); return; }

        scene.onEvent(event);
    }

    public static void run(Class<? extends Game> klass) {
        final var application = new Application(klass);

        try {
            if (application.initialize())
                application.run();
        } finally {
            application.terminate();
        }
    }

    private Window window;
    private Platform platform;

    public Application(Class<? extends Game> klass) {
        game = Reflections.instantiate(klass);
        if (game == null) fatal("Failed to create game instance");
    }

    public boolean initialize() {
        debug("Initializing Application");

        if (!initializePlatform()) return false;
        if (!initializeGraphicsEngine()) return false;
        return initializeGame();
    }

    private boolean initializePlatform() {
        debug("Initializing Platform");
        platform = new Platform();
        if (!platform.initialize()) return false;

        window = platform.create(game.getName(), game.getPlatformConfiguration(), game.getGraphicsConfiguration());
        return window.initialize();
    }

    private boolean initializeGraphicsEngine() {
        debug("Initializing Graphics Engine");
        final var graphicsConfiguration = game.getGraphicsConfiguration();

        graphics = Graphics.create(graphicsConfiguration.getGraphicsApi());
        if (!graphics.initialize()) { error("Failed to initialize Graphics Context"); return false; }
        return true;
    }

    private boolean initializeGame() {
        debug("Initializing Game");
        if (!game.initialize()) { error("Game failed to initialize"); return false; }

        scene = Reflections.instantiate(game.getScene());
        if (scene == null) { error("Failed to create game scene"); return false; }
        if (!scene.initialize()) { error("Scene failed to initialize"); return false; }

        return true;
    }

    public void run() {
        final var slice = Time.NANOSECOND / 75;

        var accumulator = 0f;

        var fps = 0;
        var ups = 0;

        var lastTime = Time.nanos();
        long timer = Time.millis();

        window.show();
        debug("Starting Game Loop");
        while(running) {
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

                Application.fps = fps;
                Application.ups = ups;
                ups = fps = 0;
            }

            Application.frameTime = (Time.nanos() - now) / Time.MILLISECOND;
            platform.pollEvents();
        }

        window.hide();
    }

    public void terminate() {
        debug("Terminating Application");

        if (scene != null) scene.terminate();
        if (window != null) window.terminate();
        if (platform != null) platform.terminate();
        shutdown.getAndSet(true);
    }
}
