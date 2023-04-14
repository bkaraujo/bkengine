package br.pong.opengl;

import br.bkraujo.engine.debug.DebugUI;
import br.bkraujo.engine.debug.RendererUI;
import br.bkraujo.engine.graphics.GeometryComponent;
import br.bkraujo.engine.graphics.GraphicsFactory;
import br.bkraujo.engine.graphics.intrinsics.BufferLayout;
import br.bkraujo.engine.graphics.intrinsics.Shader;
import br.bkraujo.engine.graphics.intrinsics.ShaderDataType;
import br.bkraujo.engine.physics.VelocityComponent;
import br.bkraujo.engine.scene.Scene;
import br.bkraujo.engine.scene.actor.Actor;
import br.bkraujo.engine.scene.camera.Camera;
import br.bkraujo.engine.scene.camera.OrthographicCamera;
import br.bkraujo.engine.scene.layer.Layer;
import br.bkraujo.engine.scene.layer.LayerType;
import br.bkraujo.utils.MathUtils;
import br.bkraujo.utils.Resources;
import org.joml.Math;
import org.joml.Vector4fc;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    private final Camera camera = new OrthographicCamera(-6f, 6f, -6f, 6f);
    public Camera getCamera() {
        return camera;
    }

    @Override
    protected boolean doInitialize() {
        getLayer(LayerType.UI).newActor().addBehaviour(RendererUI.class);
        getLayer(LayerType.UI).newActor().addBehaviour(DebugUI.class);
        final var world = getLayer(LayerType.WORLD);

        final var shader = GraphicsFactory.intrinsic().shader();
        shader.addSource(Shader.Type.FRAGMENT, Resources.asString("pong/white.frag"));

        final var viewport = getCamera().getViewport();

        if (!initializePlayers(world, viewport, shader)) return false;
        return initializeBall(world, viewport, shader);
    }

    private boolean initializePlayers(Layer world, Vector4fc viewport, Shader shader) {
        if (!initializePlayerLeft(world, viewport, shader)) return false;
        if (!initializePlayerRight(world, viewport, shader)) return false;

        return shader.initialize();
    }

    private boolean initializePlayerLeft(Layer world, Vector4fc viewport, Shader shader) {
        final var left = world.newActor("LEFT");
        if (!initializePlayer(left, shader, GLFW_KEY_W, GLFW_KEY_S)) return false;
        final var geometry = left.getComponent(GeometryComponent.class);
        geometry.vertex.addVertex(
                new float[]{
                        viewport.x() + Geometry.PADDLE_WIDTH,  Geometry.PADDLE_HEIGHT, 0.0f,  // top right
                        viewport.x() + Geometry.PADDLE_WIDTH, -Geometry.PADDLE_HEIGHT, 0.0f,  // bottom right
                        viewport.z()                        , -Geometry.PADDLE_HEIGHT, 0.0f,  // bottom left
                        viewport.z()                        ,  Geometry.PADDLE_HEIGHT, 0.0f   // top left
                },
                new BufferLayout(ShaderDataType.FLOAT3, "iPosition"));
        return true;
    }

    private boolean initializePlayerRight(Layer world, Vector4fc viewport, Shader shader) {
        final var right = world.newActor("RIGHT");
        if (!initializePlayer(right, shader, GLFW_KEY_UP, GLFW_KEY_DOWN)) return false;
        final var geometry = right.getComponent(GeometryComponent.class);
        geometry.vertex.addVertex(
                new float[]{
                        viewport.y() - Geometry.PADDLE_WIDTH,  Geometry.PADDLE_HEIGHT, 0.0f,  // top right
                        viewport.y() - Geometry.PADDLE_WIDTH, -Geometry.PADDLE_HEIGHT, 0.0f,  // bottom right
                        viewport.w()                        , -Geometry.PADDLE_HEIGHT, 0.0f,  // bottom left
                        viewport.w()                        ,  Geometry.PADDLE_HEIGHT, 0.0f   // top left
                },
                new BufferLayout(ShaderDataType.FLOAT3, "iPosition"));
        return true;
    }

    private boolean initializePlayer(Actor player, Shader shader, int up, int down) {
        final var geometry = player.addComponent(GeometryComponent.class);
        geometry.material.setShader(shader);
        geometry.vertex.setIndex(
                0, 1, 3,   // first triangle
                1, 2, 3    // second triangle
        );
        player.addComponent(VelocityComponent.class).velocity = 10f;

        final var behaviour = player.addBehaviour(PaddleController.class);
        behaviour.up = up;
        behaviour.down = down;
        behaviour.viewport = camera.getViewport();

        return true;
    }

    private boolean initializeBall(Layer world, Vector4fc viewport, Shader shader) {
        final var ball = world.newActor("BALL");
        final var velocity = ball.addComponent(VelocityComponent.class);
        velocity.velocity = 1.0f;

        final var geometry = ball.addComponent(GeometryComponent.class);
        geometry.material.setShader(shader);
        geometry.vertex.addVertex(
                circle( 16),
                new BufferLayout(ShaderDataType.FLOAT3, "iPosition")
        );

        return true;
    }

    private float[] circle(int numberOfSides) {
        final var numberOfVertices = numberOfSides + 2;
        final var circle = new float[numberOfVertices * 3];
        circle[0] = circle[1] = circle[2] = 0f; // center

        for (int i = 1 ; i < numberOfVertices; ++i) {
            final var theta = i * MathUtils.PI2f / numberOfSides;

            circle[ i * 3     ] = Math.cos(theta); // x
            circle[(i * 3) + 1] = Math.sin(theta); // y
            circle[(i * 3) + 2] = 0;               // z
        }

        return circle;
    }
}
