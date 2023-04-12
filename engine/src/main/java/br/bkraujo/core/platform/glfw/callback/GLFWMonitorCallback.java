package br.bkraujo.core.platform.glfw.callback;

import br.bkraujo.core.platform.Platform;
import br.bkraujo.core.platform.glfw.GLFWMonitor;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

public final class GLFWMonitorCallback implements GLFWMonitorCallbackI {
    GLFWMonitorCallback() {}
    public void invoke(long monitor, int event) {
        int found = -1;

        for (int i = 0; i < Platform.monitors.size() - 1 ; ++i){
            if (Platform.monitors.get(i).getHandle() == monitor){
                found = i;
                break;
            }
        }

        if (found != -1) Platform.monitors.remove(found);

        final var candidate = new GLFWMonitor(monitor);
        if (!candidate.initialize()) return;
        Platform.monitors.add(candidate);
    }
}
