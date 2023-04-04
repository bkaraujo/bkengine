package br.bkraujo.engine.renderer;

import br.bkraujo.engine.core.renderer.RenderCommand;
import br.bkraujo.engine.core.renderer.Renderable;
import br.bkraujo.engine.graphics.intrinsics.Shader;
import br.bkraujo.engine.graphics.intrinsics.VertexArray;
import br.bkraujo.engine.scene.camera.Camera;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Renderer {

    private static final Matrix4f viewProjection = new Matrix4f();
    private static final List<RenderCommand> commands = new ArrayList<>();
    private static final Map<Shader, List<Renderable>> submissions = new HashMap<>();

    private Renderer(){}

    public static long drawCalls;

    public static void beginScene(Camera camera){
        drawCalls = 0;
        commands.clear();
        submissions.clear();
        Renderer.viewProjection.set(camera.getViewProjection());
    }

    public static void submit(Shader shader, VertexArray geometry, Matrix4fc transform){
        if (!submissions.containsKey(shader)) submissions.put(shader, new ArrayList<>());
        submissions.get(shader).add(new Renderable(geometry, transform));
    }

    public static void endScene(){
        for (var entry : submissions.entrySet())
            commands.add(new RenderCommand(Renderer.viewProjection, entry.getKey(), entry.getValue()));
    }

    public static void flush(){ for (var command : commands) drawCalls += command.run(); }
}
