package br.bkraujo.core.platform.event;

import br.bkraujo.engine.event.Event;

public abstract class InputEvent extends Event {
    public InputEvent(long window) {
        super(window);
    }
}
