package br.bkraujo.engine.core.platform.event;

import br.bkraujo.engine.event.Event;

public abstract class FramebufferEvent extends Event {
    protected FramebufferEvent(long window) {
        super(window);
    }
}
