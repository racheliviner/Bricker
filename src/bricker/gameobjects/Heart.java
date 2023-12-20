package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
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
            this.hearts[i] = new Heart(topLeftCorner,dimensions,renderable);
            this.hearts[i].setTopLeftCorner(new Vector2(i*30+10, 470));
            objects.addGameObject(hearts[i]);
        }
    }

    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    public void removeHeart(){
        if (numOfHearts==1)
            this.hearts[0].setCenter(new Vector2(-50,-50));
        this.objects.removeGameObject(hearts[numOfHearts-1]);
        numOfHearts--;
    }

    public int GetNumOfHearts() {
        return this.numOfHearts;
    }
}
