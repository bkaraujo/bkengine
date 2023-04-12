package br.bkraujo.core.graphics.opengl;

import br.bkraujo.engine.graphics.intrinsics.IndexBuffer;

import static br.bkraujo.engine.Logger.fatal;
import static org.lwjgl.opengl.GL15.*;

final class GLIndexBuffer extends GLObject implements IndexBuffer {

    final int handle;
    private final int count;

    public GLIndexBuffer(int ... indices) {
        if (indices == null) { fatal("Null or Empty indices"); }

        handle = glGenBuffers(); hasError();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle); hasError();
        //noinspection ConstantConditions
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW); hasError();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_NONE);
        count = indices.length;
    }

    public int getCount() { return count; }
    public void bind() { glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle); }
    public void unbind() { glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_NONE); }
    public void terminate() { glDeleteBuffers(handle); }

}
