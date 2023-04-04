package br.bkraujo.engine.core.renderer.imgui;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.GL_VERTEX_ARRAY_BINDING;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class RendererState {
    RendererState(){}
    private int activeTexture;
    private int program;
    private int texture;
    private int arrayBuffer;
    private int vertexArrayObject;
    private final int[] viewport = new int[4];
    private final int[] scissorBox = new int[4];
    private int blendSrcRgb;
    private int blendDstRgb;
    private int blendSrcAlpha;
    private int blendDstAlpha;
    private int blendEquationRgb;
    private int blendEquationAlpha;
    private boolean enableBlend;
    private boolean enableCullFace;
    private boolean enableDepthTest;
    private boolean enableStencilTest;
    private boolean enableScissorTest;

    public void backup() {
        activeTexture = glGetInteger(GL_ACTIVE_TEXTURE);
        glActiveTexture(GL_TEXTURE0);
        program = glGetInteger(GL_CURRENT_PROGRAM);
        texture = glGetInteger(GL_TEXTURE_BINDING_2D);
        arrayBuffer = glGetInteger(GL_ARRAY_BUFFER_BINDING);
        vertexArrayObject = glGetInteger(GL_VERTEX_ARRAY_BINDING);
        glGetIntegerv(GL_VIEWPORT, viewport);
        glGetIntegerv(GL_SCISSOR_BOX, scissorBox);
        blendSrcRgb = glGetInteger(GL_BLEND_SRC_RGB);
        blendDstRgb = glGetInteger(GL_BLEND_DST_RGB);
        blendSrcAlpha = glGetInteger(GL_BLEND_SRC_ALPHA);
        blendDstAlpha = glGetInteger(GL_BLEND_DST_ALPHA);
        blendEquationRgb = glGetInteger(GL_BLEND_EQUATION_RGB);
        blendEquationAlpha = glGetInteger(GL_BLEND_EQUATION_ALPHA);
        enableBlend = glIsEnabled(GL_BLEND);
        enableCullFace = glIsEnabled(GL_CULL_FACE);
        enableDepthTest = glIsEnabled(GL_DEPTH_TEST);
        enableStencilTest = glIsEnabled(GL_STENCIL_TEST);
        enableScissorTest = glIsEnabled(GL_SCISSOR_TEST);
    }

    public void restore() {
        glUseProgram(program);
        glBindTexture(GL_TEXTURE_2D, texture);
        glActiveTexture(activeTexture);
        glBindVertexArray(vertexArrayObject);
        glBindBuffer(GL_ARRAY_BUFFER, arrayBuffer);
        glBlendEquationSeparate(blendEquationRgb, blendEquationAlpha);
        glBlendFuncSeparate(blendSrcRgb, blendDstRgb, blendSrcAlpha, blendDstAlpha);
        // @formatter:off CHECKSTYLE:OFF
        if (enableBlend) glEnable(GL_BLEND); else glDisable(GL_BLEND);
        if (enableCullFace) glEnable(GL_CULL_FACE); else glDisable(GL_CULL_FACE);
        if (enableDepthTest) glEnable(GL_DEPTH_TEST); else glDisable(GL_DEPTH_TEST);
        if (enableStencilTest) glEnable(GL_STENCIL_TEST); else glDisable(GL_STENCIL_TEST);
        if (enableScissorTest) glEnable(GL_SCISSOR_TEST); else glDisable(GL_SCISSOR_TEST);
        // @formatter:on CHECKSTYLE:ON
        glViewport(viewport[0], viewport[1], viewport[2], viewport[3]);
        glScissor(scissorBox[0], scissorBox[1], scissorBox[2], scissorBox[3]);
    }
}
