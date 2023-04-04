package br.bkraujo.engine.core.graphics;

import br.bkraujo.engine.core.graphics.opengl.GLContext;
import br.bkraujo.engine.graphics.GraphicsApi;

public class Graphics  {
    private static GraphicsContext instance;

    public static GraphicsContext create(GraphicsApi api) {
        if (instance == null) {
            if (api == GraphicsApi.OPENGL) instance = new GLContext();
            else throw new UnsupportedOperationException();
        }

        return instance;
    }

    public static GraphicsContext context() {
        return instance;
    }
}
