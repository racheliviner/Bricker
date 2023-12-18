package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class UserPeddle extends GameObject {
    private static final int MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;

    public UserPeddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            movementDir = movementDir.add(Vector2.LEFT);
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            movementDir = movementDir.add(Vector2.RIGHT);
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
