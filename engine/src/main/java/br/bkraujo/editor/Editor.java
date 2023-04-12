package br.bkraujo.editor;

import br.bkraujo.engine.LogLevel;
import br.bkraujo.engine.Logger;
import br.bkraujo.engine.scene.Scene;
import br.bkraujo.game.Application;
import br.bkraujo.game.Game;

public class Editor extends Game {
    public static void main(String[] args) { Logger.level = LogLevel.TRACE; Application.run(Editor.class); }

    public String getName() { return "Editor"; }
    public Class<? extends Scene> getScene() { return EditorScene.class; }

}
