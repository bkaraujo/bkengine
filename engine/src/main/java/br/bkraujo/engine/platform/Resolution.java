package br.bkraujo.engine.platform;

public enum Resolution {

    HD(720),
    FHD(1080),
    QHD(1440),
    UHD(2160);

    public final int pixels;

    Resolution(int pixels) {
        this.pixels = pixels;
    }

    public int compute(AspectRatio ratio) {
        return (pixels * ratio.vertical) / ratio.horizontal;
    }
}
