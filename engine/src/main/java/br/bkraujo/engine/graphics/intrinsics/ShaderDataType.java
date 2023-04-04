package br.bkraujo.engine.graphics.intrinsics;

public enum ShaderDataType {

    FLOAT(1, Float.BYTES),
    FLOAT2(2, 2 * Float.BYTES),
    FLOAT3(3, 3 * Float.BYTES),
    FLOAT4(4, 4 * Float.BYTES),

    INT(1, Integer.BYTES),
    INT2(2, 2 * Integer.BYTES),
    INT3(3, 3 * Integer.BYTES),
    INT4(4, 4 * Integer.BYTES),

    MAT2(2*2, Float.BYTES * 2 * 2),
    MAT3(3*3, Float.BYTES * 3 * 3),
    MAT4(4*4, Float.BYTES * 4 * 4),

    BOOL(1, 1);

    public final int count;
    public final int size;

    ShaderDataType(int count, int size) {
        this.count = count;
        this.size = size;
    }
}
