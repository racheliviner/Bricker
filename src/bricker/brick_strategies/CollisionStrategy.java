package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;

public class CollisionStrategy {

    // Collection of game objects
    private GameObjectCollection object;

    // Number of remaining bricks
    private int numOfBricks;

    // Constructor
    public CollisionStrategy(GameObjectCollection object, int numOfBricks) {
        this.object = object;
        this.numOfBricks = numOfBricks;
    }

    /**
     * Handles collision between game objects.
     *
     * @param thisObj   The current game object.
     * @param otherObj  The other game object involved in the collision.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        // Check if the collision involves a Ball object
        if (otherObj instanceof Ball) {
            // Special condition: Set the position of the object to (-50, -50)
            if (numOfBricks == 1) {
                thisObj.setCenter(new Vector2(-50, -50));
            }

            // Remove the collided brick from the object collection
            this.object.removeGameObject(thisObj);

            // Decrement the number of remaining bricks
            numOfBricks--;
        }
    }

    /**
     * Gets the number of remaining bricks.
     *
     * @return The number of remaining bricks.
     */
    public int getNumOfBricks() {
        return numOfBricks;
    }
}
