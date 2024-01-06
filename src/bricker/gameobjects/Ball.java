package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Brick extends GameObject {

    // Strategy for handling collisions involving the Brick
    private CollisionStrategy collisionStrategy;

    // Constructor
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy) {
        // Call the superclass constructor
        super(topLeftCorner, dimensions, renderable);

        // Set the collision strategy for the Brick
        this.collisionStrategy = collisionStrategy;
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

        // Delegate the collision handling to the associated CollisionStrategy
        collisionStrategy.onCollision(this, other);
    }
}
