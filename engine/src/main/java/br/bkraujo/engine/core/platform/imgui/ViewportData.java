package br.bkraujo.engine.core.platform.imgui;

import br.bkraujo.engine.core.platform.Platform;

public final class ViewportData {
    public long window;
    public boolean windowOwned = false;
    public int ignoreWindowPosEventFrame = -1;
    public int ignoreWindowSizeEventFrame = -1;

    public ViewportData(){
        window = Platform.Window.handle;
    }
}
