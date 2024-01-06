package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {

    // Sound to be played on collision
    private Sound collisionSound;

    // Constructor
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        // Call the superclass constructor
        super(topLeftCorner, dimensions, renderable);

        // Initialize the collision sound
        this.collisionSound = collisionSound;
    }

    /**
     * Overrides the onCollisionEnter method to handle collision events.
     *
     * @param other     The other game object involved in the collision.
     * @param collision Details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        // Call the superclass method
        super.onCollisionEnter(other, collision);

        // Check if the collision involves a Heart
        if (!(other instanceof Heart)) {
            // Reverse the velocity based on the collision normal
            setVelocity(getVelocity().flipped(collision.getNormal()));

            // Play the collision sound
            collisionSound.play();
        }
    }
}
