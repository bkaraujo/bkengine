package br.bkraujo.engine.core.graphics.opengl;

import br.bkraujo.engine.core.graphics.GraphicsContext;
import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.engine.graphics.GraphicsApi;
import br.bkraujo.engine.graphics.GraphicsFactory;
import br.bkraujo.utils.Reflections;
import org.lwjgl.opengl.GL;

import static br.bkraujo.engine.Logger.trace;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GLContext implements GraphicsContext {
    public boolean initialize() {
        glfwMakeContextCurrent(Platform.Window.handle);
        GL.createCapabilities();

        trace("GL_RENDERER: %s", glGetString(GL_RENDERER));
        trace("GL_VERSION: %s", glGetString(GL_VERSION));

        glfwSwapInterval(0);
        glClearColor(1f, 0, 1f, 1f);

        return Reflections.set(GraphicsFactory.class, "factory", new GLIntrinsicFactory());
    }

    public void clear() { glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); }
    public void swap() { glfwSwapBuffers(Platform.Window.handle); }
    public GraphicsApi getGraphicsApi() { return GraphicsApi.OPENGL; }
    public void terminate() { GL.destroy(); }
}
