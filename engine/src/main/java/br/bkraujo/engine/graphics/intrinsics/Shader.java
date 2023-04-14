package br.bkraujo.engine.graphics.intrinsics;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.graphics.Bindable;
import br.bkraujo.engine.graphics.Uniform;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;

public interface Shader extends Lifecycle, Bindable, Uniform {
    enum Type {
        VERTEX (GL_VERTEX_SHADER),
        FRAGMENT(GL_FRAGMENT_SHADER),
        GEOMETRY(GL_GEOMETRY_SHADER),
        TESSELATION_CONTROL(GL_TESS_CONTROL_SHADER),
        TESSELATION_EVALUATION(GL_TESS_EVALUATION_SHADER);

        public final int value;
        Type(int value) {
            this.value = value;
        }
    }

    int getId();
    boolean addSource(Type type, String source);

    int getUniformLocation(String name);
    int getAttributeLocation(String name);

}
