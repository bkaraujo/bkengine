package br.bkraujo.engine.core.graphics;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.graphics.GraphicsApi;

public interface GraphicsContext extends Lifecycle {

    void clear();
    void swap();

    GraphicsApi getGraphicsApi();

}
