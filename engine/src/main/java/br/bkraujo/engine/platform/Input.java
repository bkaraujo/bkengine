package br.bkraujo.engine.platform;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.utils.Vectors;
import org.joml.Vector2fc;

public abstract class Input {
    private Input(){}

    public static boolean isKeyActive(int key) { return Platform.Keyboard.isActive(key); }
    public static boolean isKeyPressed(int key) { return Platform.Keyboard.isPressed(key); }
    public static boolean isKeyReleased(int key) { return Platform.Keyboard.isReleased(key); }

    public static boolean isMousePressed(int key) { return Platform.Mouse.isPressed(key); }
    public static boolean isMouseReleased(int key) { return Platform.Mouse.isReleased(key); }
    public static Vector2fc getMouse() { return Vectors.of(Platform.Mouse.position.x, Platform.Mouse.position.y); }

}
