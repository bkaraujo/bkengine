package br.bkraujo.engine.graphics.intrinsics;

public final class BufferLayout {

    public final String name;
    public final int stride;
    public final int offset;
    public final boolean normalized;
    public final ShaderDataType type;

    public BufferLayout(ShaderDataType type, String name) {
        this(type, name, false);
    }

    public BufferLayout(ShaderDataType type, String name, boolean normalized) {
        this(type, name, normalized, 0 ,0);
    }

    public BufferLayout(ShaderDataType type, String name, boolean normalized, int stride, int offset) {
        this.name = name;
        this.type = type;
        this.normalized = normalized;
        this.stride = stride;
        this.offset = offset;
    }
}
