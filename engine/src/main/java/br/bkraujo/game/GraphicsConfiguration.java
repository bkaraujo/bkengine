package br.bkraujo.game;

import br.bkraujo.engine.graphics.GraphicsApi;
import br.bkraujo.utils.Vectors;
import org.joml.Vector2ic;

public class GraphicsConfiguration {

    public GraphicsApi getGraphicsApi() { return GraphicsApi.OPENGL; }

    /** Opengl version to use. Called if {@link #getGraphicsApi()} return {@link GraphicsApi#OPENGL} */
    public Vector2ic getGLVersion() { return Vectors.of(4, 6); }
}
