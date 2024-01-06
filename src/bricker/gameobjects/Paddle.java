package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    // Movement speed of the paddle
    private static final int MOVEMENT_SPEED = 400;

    // Minimum distance from the screen edge
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = -34;

    // User input listener for handling keyboard input
    private UserInputListener inputListener;

    // Dimensions of the game window
    private Vector2 windowDimensions;

    // Constructor for creating a Paddle
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        // Call the superclass constructor
        super(topLeftCorner, dimensions, renderable);

        // Initialize input listener and window dimensions
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        // Call the superclass update method
        super.update(deltaTime);

        // Calculate movement direction based on keyboard input
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            movementDir = movementDir.add(Vector2.LEFT);
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            movementDir = movementDir.add(Vector2.RIGHT);

        // Set the velocity of the paddle based on the movement direction and speed
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        // Calculate the right border of the screen
        float rightBorder = windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x();

        // Check if the paddle is beyond the left screen edge and adjust its position
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            transform().setTopLeftCornerX(MIN_DISTANCE_FROM_SCREEN_EDGE);
        }

        // Check if the paddle is beyond the right screen edge and adjust its position
        if (getTopLeftCorner().x() > rightBorder) {
            transform().setTopLeftCornerX(rightBorder);
        }
    }
}
