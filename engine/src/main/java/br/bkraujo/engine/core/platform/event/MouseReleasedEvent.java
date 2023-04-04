package br.bkraujo.engine.core.platform.event;

public final class MouseReleasedEvent extends MouseButtonEvent {

    public MouseReleasedEvent(long window, int button) {
        super(window, button);
    }
}
