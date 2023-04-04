package br.bkraujo.engine.core.platform.event;

public final class MousePressedEvent extends MouseButtonEvent {

    public MousePressedEvent(long window, int button) {
        super(window, button);
    }

}
