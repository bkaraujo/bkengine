package br.bkraujo.game;

import br.bkraujo.engine.platform.AspectRatio;
import br.bkraujo.engine.platform.Resolution;

public class PlatformConfiguration {

    public boolean getWindowResizeable() { return false; }
    public boolean getWindowMaximized() { return false; }
    public Resolution getWindowResolution() { return Resolution.HD; }
    public AspectRatio getWindowAspectRation() { return AspectRatio.R16x9; }

}
