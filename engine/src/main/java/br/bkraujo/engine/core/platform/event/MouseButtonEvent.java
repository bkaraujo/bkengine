package br.bkraujo.engine.core.platform.event;

public abstract class MouseButtonEvent extends MouseEvent {
    private final int button;

    public MouseButtonEvent(long window, int button) {
        super(window);
        this.button = button;
    }

    public final int getButton() {
        return button;
    }
}
