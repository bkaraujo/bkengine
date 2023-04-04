package br.bkraujo.engine.graphics;

public abstract class GraphicsFactory {
    private GraphicsFactory(){}

    @SuppressWarnings("FieldMayBeFinal")
    private static GraphicsIntrinsicFactory factory = null;

    public static GraphicsIntrinsicFactory intrinsic() {
        return factory;
    }

}
