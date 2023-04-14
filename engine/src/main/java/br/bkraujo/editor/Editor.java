package br.bkraujo.editor;

import br.bkraujo.engine.LogLevel;
import br.bkraujo.engine.Logger;
import br.bkraujo.engine.platform.Resolution;
import br.bkraujo.engine.scene.Scene;
import br.bkraujo.game.Application;
import br.bkraujo.game.Game;
import br.bkraujo.game.PlatformConfiguration;

public class Editor extends Game {
    public static void main(String[] args) { Logger.level = LogLevel.TRACE; Application.run(Editor.class); }

    public String getCompany() { return "BKraujo"; }
    public String getName() { return "Editor"; }
    public Class<? extends Scene> getScene() { return EditorScene.class; }

    public PlatformConfiguration getPlatformConfiguration() {
        return new PlatformConfiguration() {
            public boolean getWindowResizeable() { return true; }
            public Resolution getWindowResolution() { return Resolution.FHD; }
        };
    }
}
