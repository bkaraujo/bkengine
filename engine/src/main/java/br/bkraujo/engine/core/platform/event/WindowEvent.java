package br.bkraujo.engine.core.platform.event;

import br.bkraujo.engine.event.Event;

public abstract class WindowEvent extends Event {
    public WindowEvent(long window) {
        super(window);
    }
}
