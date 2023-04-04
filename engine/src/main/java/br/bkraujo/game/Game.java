package br.bkraujo.game;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.scene.Scene;

/** Game configuration */
public abstract class Game implements Lifecycle {

    public abstract String getName();

    public boolean initialize() { return true; }
    public void terminate() {}

    /** Game starting scene */
    public abstract Class<? extends Scene> getScene();
    public PlatformConfiguration getPlatformConfiguration() { return new PlatformConfiguration(); }
    public GraphicsConfiguration getGraphicsConfiguration() { return new GraphicsConfiguration(); }
}
