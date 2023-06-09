package br.bkraujo.core.renderer;

import br.bkraujo.core.graphics.Graphics;
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
            shader.setUniformMatrix("uTransform", false, renderable.transform);
            renderable.material.bind();
            renderable.vertex.bind();

            if (renderable.vertex.getIndex() == null) backend.draw(renderable.vertex);
            else backend.drawIndexed(renderable.vertex);

            draws++;
        }

        return draws;
    }
}
