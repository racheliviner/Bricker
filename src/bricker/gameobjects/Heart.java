package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {

    // Collection of game objects
    private GameObjectCollection objects;

    // Number of hearts
    private int numOfHearts;

    // Array to store individual heart game objects
    private GameObject[] hearts;

    // Constructor for creating a Heart with a collection of hearts
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection objects, int numOfHearts) {
        // Call the superclass constructor
        super(topLeftCorner, dimensions, renderable);

        // Initialize the collection and number of hearts
        this.objects = objects;
        this.numOfHearts = numOfHearts;

        // Create an array to store individual heart game objects
        this.hearts = new GameObject[numOfHearts];

        // Initialize and add each heart to the collection
        for (int i = 0; i < numOfHearts; i++) {
            this.hearts[i] = new Heart(topLeftCorner, dimensions, renderable);
            this.hearts[i].setTopLeftCorner(new Vector2(i * 30 + 10, 470));
            objects.addGameObject(hearts[i]);
        }
    }

    // Constructor for creating a standalone Heart
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        // Call the superclass constructor
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * Removes a heart from the collection and updates the number of hearts.
     */
    public void removeHeart() {
        // Check if there is only one heart left
        if (numOfHearts == 1)
            this.hearts[0].setCenter(new Vector2(-50, -50));

        // Remove the last heart from the collection
        this.objects.removeGameObject(hearts[numOfHearts - 1]);

        // Decrease the number of hearts
        numOfHearts--;
    }

    /**
     * Returns the current number of hearts.
     *
     * @return The number of hearts.
     */
    public int getNumOfHearts() {
        return this.numOfHearts;
    }
}
