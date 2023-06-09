package br.bkraujo.game;

import br.bkraujo.core.GameConfiguration;
import br.bkraujo.core.graphics.Graphics;
import br.bkraujo.core.platform.Platform;
import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.event.Event;
import br.bkraujo.engine.platform.Window;
import br.bkraujo.utils.Reflections;

import static br.bkraujo.engine.Logger.*;

public final class Application implements Lifecycle {

    private static boolean shutdown;
    public static boolean isShutdown() { return shutdown; }

    public static void onEvent(Event event) {
        if (event == null) { error("Event is null"); return ; }
        if (event.isHandled()) { warn("Posting handled event"); return; }
        game.onEvent(event);
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

    private static Game game;
    private final Window window;
    private final Platform platform;

    public Application(Class<? extends Game> klass) {
        game = Reflections.instantiate(klass);
        if (game == null) fatal("Failed to create game instance");

        GameConfiguration.company = game.getCompany();
        GameConfiguration.name = game.getName();
        GameConfiguration.graphics = game.getGraphicsConfiguration();
        GameConfiguration.platform = game.getPlatformConfiguration();

        platform = new Platform();
        window = platform.create();
    }

    public boolean initialize() {
        debug("Initializing Platform");
        if (!platform.initialize()) return false;
        if (!window.initialize()) return false;

        debug("Initializing Graphics Engine");
        final var graphics = Graphics.create(GameConfiguration.graphics.getGraphicsApi());
        if (!graphics.initialize()) { error("Failed to initialize Graphics Context"); return false; }

        debug("Initializing Game");
        game.setPlatform(platform);
        game.setWindow(window);
        return game.initialize();
    }

    public void run() {
        debug("Game Running");
        game.run();
        debug("Game Finished");
    }

    public void terminate() {
        debug("Terminating Application");

        if (window != null) window.terminate();
        if (platform != null) platform.terminate();
        shutdown  = true;
    }
}
