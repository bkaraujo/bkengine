package br.bkraujo.engine.core.renderer.imgui;

import br.bkraujo.engine.Lifecycle;
import br.bkraujo.engine.graphics.GraphicsFactory;
import br.bkraujo.engine.graphics.intrinsics.Shader;
import imgui.*;
import imgui.callback.ImPlatformFuncViewport;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiViewportFlags;
import imgui.type.ImInt;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glDrawElementsBaseVertex;

public final class Renderer implements Lifecycle {

    private final ImGuiIO io;
    private final Shader shader;
    private final ImGuiPlatformIO platform;
    private final RendererState state = new RendererState();

    // OpenGL Data
    private int glVersion;
    private int gFontTexture;
    private int VAO;
    private int VBO;
    private int EBO;

    // Used to store tmp renderer data
    private final ImVec2 displaySize = new ImVec2();
    private final ImVec2 displayPos = new ImVec2();

    public Renderer(ImGuiIO io, ImGuiPlatformIO platform) {
        this.io = io;
        this.platform = platform;
        shader = GraphicsFactory.intrinsic().shader();
    }

    public boolean initialize() {
        io.setBackendRendererName(getClass().getCanonicalName());
        io.addBackendFlags(ImGuiBackendFlags.RendererHasViewports);

        glVersion = glGetInteger(GL_MAJOR_VERSION) * 100 + glGetInteger(GL_MINOR_VERSION) * 10;

        if (glVersion >= 320)
            io.addBackendFlags(ImGuiBackendFlags.RendererHasVtxOffset);

        createDeviceObjects();

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable))
            platform.setRendererRenderWindow(new ImPlatformFuncViewport() {
                public void accept(ImGuiViewport viewport) {
                    if (!viewport.hasFlags(ImGuiViewportFlags.NoRendererClear)) {
                        glClearColor(0, 0, 0, 0);
                        glClear(GL_COLOR_BUFFER_BIT);
                    }

                    render(viewport.getDrawData());
                }
            });

        if (glVersion < 130) {
            if (!shader.addSource(Shader.Type.VERTEX, Shaders.V120[1])) return false;
            if (!shader.addSource(Shader.Type.FRAGMENT, Shaders.V120[0])) return false;
        } else if (glVersion == 300) {
            if (!shader.addSource(Shader.Type.VERTEX, Shaders.V300[1])) return false;
            if (!shader.addSource(Shader.Type.FRAGMENT, Shaders.V300[0])) return false;
        } else if (glVersion >= 410) {
            if (!shader.addSource(Shader.Type.VERTEX, Shaders.V410[1])) return false;
            if (!shader.addSource(Shader.Type.FRAGMENT, Shaders.V410[0])) return false;
        } else {
            if (!shader.addSource(Shader.Type.VERTEX, Shaders.V130[1])) return false;
            if (!shader.addSource(Shader.Type.FRAGMENT, Shaders.V130[0])) return false;
        }

        return shader.initialize();
    }

    private void createDeviceObjects() {
        final var texture = glGetInteger(GL_TEXTURE_BINDING_2D);
        final var arrayBuffer = glGetInteger(GL_ARRAY_BUFFER_BINDING);
        final var vertexBuffer = glGetInteger(GL_VERTEX_ARRAY_BINDING);

        try {
            // Create buffers
            VBO = glGenBuffers();
            EBO = glGenBuffers();

            updateFontsTexture();
        } finally {
            glBindTexture(GL_TEXTURE_2D, texture);
            glBindBuffer(GL_ARRAY_BUFFER, arrayBuffer);
            glBindVertexArray(vertexBuffer);
        }
    }

    public void updateFontsTexture() {
        glDeleteTextures(gFontTexture);

        final var fontAtlas = io.getFonts();
        final var width = new ImInt();
        final var height = new ImInt();
        final var buffer = fontAtlas.getTexDataAsRGBA32(width, height);

        gFontTexture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, gFontTexture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        fontAtlas.setTexID(gFontTexture);
    }

    /**
     * Method to render {@link ImDrawData} into current OpenGL context.
     *
     * @param drawData draw data to render
     */
    public void render(ImDrawData drawData) {
        if (drawData.getCmdListsCount() <= 0) {
            return;
        }

        // Will project scissor/clipping rectangles into framebuffer space
        drawData.getDisplaySize(displaySize);           // (0,0) unless using multi-viewports
        drawData.getDisplayPos(displayPos);
        final var scale = drawData.getFramebufferScale(); // (1,1) unless using retina display which are often (2,2)

        final var clipOffX = displayPos.x;
        final var clipOffY = displayPos.y;
        final var clipScaleX = scale.x;
        final var clipScaleY = scale.y;

        // Avoid rendering when minimized, scale coordinates for retina displays (screen coordinates != framebuffer coordinates)
        final var fbWidth = (int) (displaySize.x * scale.x);
        final var fbHeight = (int) (displaySize.y * scale.y);

        if (fbWidth <= 0 || fbHeight <= 0) {
            return;
        }

        state.backup();
        try {
            if (!bind(fbWidth, fbHeight)) return;

            // Render command lists
            final var rectangle = new ImVec4();
            for (int cmdListIdx = 0; cmdListIdx < drawData.getCmdListsCount(); cmdListIdx++) {
                // Upload vertex/index buffers
                glBufferData(GL_ARRAY_BUFFER, drawData.getCmdListVtxBufferData(cmdListIdx), GL_STREAM_DRAW);
                glBufferData(GL_ELEMENT_ARRAY_BUFFER, drawData.getCmdListIdxBufferData(cmdListIdx), GL_STREAM_DRAW);

                for (int cmdBufferIdx = 0; cmdBufferIdx < drawData.getCmdListCmdBufferSize(cmdListIdx); cmdBufferIdx++) {
                    drawData.getCmdListCmdBufferClipRect(cmdListIdx, cmdBufferIdx, rectangle);

                    final var clipMinX = (rectangle.x - clipOffX) * clipScaleX;
                    final var clipMinY = (rectangle.y - clipOffY) * clipScaleY;
                    final var clipMaxX = (rectangle.z - clipOffX) * clipScaleX;
                    final var clipMaxY = (rectangle.w - clipOffY) * clipScaleY;

                    if (clipMaxX <= clipMinX || clipMaxY <= clipMinY) {
                        continue;
                    }

                    // Apply scissor/clipping rectangle (Y is inverted in OpenGL)
                    glScissor((int) clipMinX, (int) (fbHeight - clipMaxY), (int) (clipMaxX - clipMinX), (int) (clipMaxY - clipMinY));

                    // Bind texture, Draw
                    final var textureId = drawData.getCmdListCmdBufferTextureId(cmdListIdx, cmdBufferIdx);
                    final var elemCount = drawData.getCmdListCmdBufferElemCount(cmdListIdx, cmdBufferIdx);
                    final var idxBufferOffset = drawData.getCmdListCmdBufferIdxOffset(cmdListIdx, cmdBufferIdx);
                    final var vtxBufferOffset = drawData.getCmdListCmdBufferVtxOffset(cmdListIdx, cmdBufferIdx);
                    final var indices = idxBufferOffset * ImDrawData.SIZEOF_IM_DRAW_IDX;

                    glBindTexture(GL_TEXTURE_2D, textureId);

                    if (glVersion >= 320) {
                        glDrawElementsBaseVertex(GL_TRIANGLES, elemCount, GL_UNSIGNED_SHORT, indices, vtxBufferOffset);
                    } else {
                        glDrawElements(GL_TRIANGLES, elemCount, GL_UNSIGNED_SHORT, indices);
                    }
                }
            }
        } finally {
            unbind();
            state.restore();
        }
    }

    // Setup desired GL state
    private boolean bind(final int fbWidth, final int fbHeight) {
        final var left = displayPos.x;
        final var right = displayPos.x + displaySize.x;
        final var top = displayPos.y;
        final var bottom = displayPos.y + displaySize.y;

        // Orthographic matrix projection
        final var matrix = new float[]{
                2.0f / (right - left),              0,                                  0,      0,
                0,                                  2.0f / (top - bottom),              0,      0,
                0,                                  0,                                  -1f,    0,
                (right + left) / (left - right),    (top + bottom) / (bottom - top),    0,      1
        };

        // Bind shader
        shader.bind();
        if (!shader.setUniform("Texture", 0)) return false;
        if (!shader.setUniformMatrix("ProjMtx", false, matrix)) return false;

        // Recreate the VAO every time (this is to easily allow multiple GL contexts to be rendered to. VAO are not shared among GL contexts)
        // The renderer would actually work without any VAO bound, but then our VertexAttrib calls would overwrite the default one currently bound.
        VAO = glGenVertexArrays();

        // Setup render state: alpha-blending enabled, no face culling, no depth testing, scissor enabled, polygon fill
        glEnable(GL_BLEND);
        glBlendEquation(GL_FUNC_ADD);
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_STENCIL_TEST);
        glEnable(GL_SCISSOR_TEST);

        // Setup viewport, orthographic projection matrix
        // Our visible imgui space lies from draw_data->DisplayPos (top left) to draw_data->DisplayPos+data_data->DisplaySize (bottom right).
        // DisplayPos is (0,0) for single viewport apps.
        glViewport(0, 0, fbWidth, fbHeight);
        glBindVertexArray(VAO);

        // Bind vertex/index buffers and setup attributes for ImDrawVert
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glEnableVertexAttribArray(shader.getAttributeLocation("Position"));
        glEnableVertexAttribArray(shader.getAttributeLocation("UV"));
        glEnableVertexAttribArray(shader.getAttributeLocation("Color"));
        glVertexAttribPointer(shader.getAttributeLocation("Position"), 2, GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 0);
        glVertexAttribPointer(shader.getAttributeLocation("UV"), 2, GL_FLOAT, false, ImDrawData.SIZEOF_IM_DRAW_VERT, 8);
        glVertexAttribPointer(shader.getAttributeLocation("Color"), 4, GL_UNSIGNED_BYTE, true, ImDrawData.SIZEOF_IM_DRAW_VERT, 16);

        return true;
    }

    private void unbind() {
        shader.unbind();
        // Destroy the temporary VAO
        glDeleteVertexArrays(VAO);
    }

    public void terminate() {
        glDeleteBuffers(VBO);
        glDeleteBuffers(EBO);
        glDeleteTextures(gFontTexture);

        shader.terminate();
        ImGui.destroyPlatformWindows();
    }
}
