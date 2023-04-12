package br.bkraujo.core.renderer.imgui;

class Shaders {
    public static final String[] V120 = new String[] {
            "#version 120\n" +
                    "\n" +
                    "#ifdef GL_ES\n" +
                    "    precision mediump float;\n" +
                    "#endif\n" +
                    "uniform sampler2D Texture;\n" +
                    "varying vec2 Frag_UV;\n" +
                    "varying vec4 Frag_Color;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    gl_FragColor = Frag_Color * texture2D(Texture, Frag_UV.st);\n" +
                    "}",
            "#version 120\n" +
                    "\n" +
                    "uniform mat4 ProjMtx;\n" +
                    "attribute vec2 Position;\n" +
                    "attribute vec2 UV;\n" +
                    "attribute vec4 Color;\n" +
                    "varying vec2 Frag_UV;\n" +
                    "varying vec4 Frag_Color;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    Frag_UV = UV;\n" +
                    "    Frag_Color = Color;\n" +
                    "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n" +
                    "}"
    };
    public static final String[] V130 = new String[] {
            "#version 130\n" +
                    "\n" +
                    "uniform sampler2D Texture;\n" +
                    "in vec2 Frag_UV;\n" +
                    "in vec4 Frag_Color;\n" +
                    "out vec4 Out_Color;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n" +
                    "}",
            "#version 130\n" +
                    "\n" +
                    "uniform mat4 ProjMtx;\n" +
                    "in vec2 Position;\n" +
                    "in vec2 UV;\n" +
                    "in vec4 Color;\n" +
                    "out vec2 Frag_UV;\n" +
                    "out vec4 Frag_Color;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    Frag_UV = UV;\n" +
                    "    Frag_Color = Color;\n" +
                    "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n" +
                    "}"
    };
    public static final String[] V300 = new String[] {
            "#version 300 es\n" +
                    "\n" +
                    "uniform sampler2D Texture;\n" +
                    "in vec2 Frag_UV;\n" +
                    "in vec4 Frag_Color;\n" +
                    "out vec4 Out_Color;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n" +
                    "}",
            "#version 300 es\n" +
                    "\n" +
                    "precision highp float;\n" +
                    "layout (location = 0) in vec2 Position;\n" +
                    "layout (location = 1) in vec2 UV;\n" +
                    "layout (location = 2) in vec4 Color;\n" +
                    "uniform mat4 ProjMtx;\n" +
                    "out vec2 Frag_UV;\n" +
                    "out vec4 Frag_Color;\n" +
                    "\n" +
                    "void main()  {\n" +
                    "    Frag_UV = UV;\n" +
                    "    Frag_Color = Color;\n" +
                    "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n" +
                    "}"
    };
    public static final String[] V410 = new String[] {
            "#version 410 core\n" +
                    "\n" +
                    "in vec2 Frag_UV;\n" +
                    "in vec4 Frag_Color;\n" +
                    "uniform sampler2D Texture;\n" +
                    "layout (location = 0) out vec4 Out_Color;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n" +
                    "}",
            "#version 410 core\n" +
                    "\n" +
                    "layout (location = 0) in vec2 Position;\n" +
                    "layout (location = 1) in vec2 UV;\n" +
                    "layout (location = 2) in vec4 Color;\n" +
                    "uniform mat4 ProjMtx;\n" +
                    "out vec2 Frag_UV;\n" +
                    "out vec4 Frag_Color;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    Frag_UV = UV;\n" +
                    "    Frag_Color = Color;\n" +
                    "    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n" +
                    "}"
    };
}
