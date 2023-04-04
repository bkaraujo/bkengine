package br.bkraujo.engine.core.renderer;

import br.bkraujo.engine.graphics.intrinsics.VertexArray;

import static org.lwjgl.opengl.GL11.*;

public class GLBackend implements RendererBackend {

    public void drawIndexed(VertexArray array) {
        glDrawElements(GL_TRIANGLES, array.getIndex().getCount(), GL_UNSIGNED_INT, 0);
    }

}
