package br.pong.opengl;

import br.bkraujo.engine.LogLevel;
import br.bkraujo.engine.Logger;
import br.bkraujo.engine.scene.Scene;
import br.bkraujo.game.Application;
import br.bkraujo.game.Game;

public final class Pong extends Game {
    public static void main(String[] args) { Logger.level = LogLevel.TRACE; Application.run(Pong.class); }

    public String getCompany() { return "BKraujo"; }
    public String getName() { return "Pong"; }
    public Class<? extends Scene> getScene() { return GameScene.class; }
}
