package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final int MOVEMENT_SPEED = 400;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = -34;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
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

        float rightBorder = windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x();

        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            transform().setTopLeftCornerX(MIN_DISTANCE_FROM_SCREEN_EDGE);
        }
        if (getTopLeftCorner().x() > rightBorder) {
            transform().setTopLeftCornerX(rightBorder);
        }
    }
}
