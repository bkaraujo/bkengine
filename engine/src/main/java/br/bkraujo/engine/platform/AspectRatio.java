package br.bkraujo.engine.platform;

public enum AspectRatio {
    R4x3(4, 3),
    R16x9(16, 9),
    R21x9(21, 9);

    public final int vertical;
    public final int horizontal;

    AspectRatio(int vertical, int  horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }
}
