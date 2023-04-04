package br.bkraujo.engine.core.renderer;

import br.bkraujo.engine.core.graphics.Graphics;
import br.bkraujo.engine.graphics.GraphicsApi;
import br.bkraujo.engine.graphics.intrinsics.Shader;
import org.joml.Matrix4fc;

import java.util.ArrayList;
import java.util.List;

public final class RenderCommand {
    private static final RendererBackend backend = Graphics.context().getGraphicsApi() == GraphicsApi.OPENGL ? new GLBackend() : null;

    private final Matrix4fc viewProjection;
    private final Shader shader;
    private final List<Renderable> renderables = new ArrayList<>();

    public RenderCommand(Matrix4fc viewProjection, Shader shader, List<Renderable> renderables) {
        this.viewProjection = viewProjection;
        this.shader = shader;
        this.renderables.addAll(renderables);
    }

    public int run() {
        shader.bind();
        shader.setUniformMatrix("uViewProjection", false, viewProjection);

        var draws = 0;
        for(var renderable : renderables) {
            renderable.vertexArray.bind();
            shader.setUniformMatrix("uTransform", false, renderable.transform);
            backend.drawIndexed(renderable.vertexArray);
            draws++;
        }

        return draws;
    }
}
