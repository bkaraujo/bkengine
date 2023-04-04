package br.bkraujo.engine.core.platform.callback;

import br.bkraujo.engine.core.platform.Monitor;
import br.bkraujo.engine.core.platform.Platform;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

public final class GLFWMonitorCallback implements GLFWMonitorCallbackI {
    public void invoke(long monitor, int event) {
        int found = -1;

        for (int i = 0; i < Platform.monitors.size() - 1 ; ++i){
            if (Platform.monitors.get(i).getHandle() == monitor){
                found = i;
                break;
            }
        }

        if (found != -1) Platform.monitors.remove(found);
        Platform.monitors.add(new Monitor(monitor));
    }
}
