package br.bkraujo.core;

import br.bkraujo.game.GraphicsConfiguration;
import br.bkraujo.game.PlatformConfiguration;

public abstract class GameConfiguration {
    private GameConfiguration(){}

    public static String company;
    public static String name;
    public static PlatformConfiguration platform;
    public static GraphicsConfiguration graphics;

}
