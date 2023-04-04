package br.bkraujo.engine.core.platform.event;

public abstract class WindowFocusEvent extends WindowEvent {
    public WindowFocusEvent(long window) {
        super(window);
    }
}
