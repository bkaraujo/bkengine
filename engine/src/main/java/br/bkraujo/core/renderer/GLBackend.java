package br.bkraujo.core.renderer;

import br.bkraujo.engine.graphics.intrinsics.VertexArray;

import static org.lwjgl.opengl.GL11.*;

public class GLBackend implements RendererBackend {

    public void drawIndexed(VertexArray array) {
        glDrawElements(GL_TRIANGLES, array.getIndex().getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void draw(VertexArray array) {
        glDrawArrays(GL_TRIANGLE_FAN, 0, array.getVertexCount());
    }

}
