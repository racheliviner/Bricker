package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Heart extends GameObject {
    private GameObjectCollection objects;
    private int numOfHearts;
    private GameObject[] hearts;

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection objects, int numOfHearts) {
        super(topLeftCorner, dimensions, renderable);
        this.objects = objects;
        this.numOfHearts = numOfHearts;
        this.hearts = new GameObject[numOfHearts];
        for (int i = 0; i < numOfHearts; i++) {
            this.hearts[i] = new GameObject(topLeftCorner,dimensions,renderable);
            this.hearts[i].setCenter(new Vector2(50+i*40, 40));
            objects.addGameObject(hearts[i]);
        }
    }

    public void removeHeart(){
        this.objects.removeGameObject(hearts[numOfHearts-1]);
        numOfHearts--;
    }

    public int GetNumOFhearts() {
        return this.numOfHearts;
    }
}
