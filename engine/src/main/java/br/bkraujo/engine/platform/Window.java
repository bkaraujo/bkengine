package br.bkraujo.engine.platform;

import br.bkraujo.engine.Lifecycle;

public interface Window extends Lifecycle {
    void setTitle(String title);
    void appendTitle(String title);

    void show();
    void hide();
}
