package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class CollisionStrategy {

    private GameObjectCollection object;

    public CollisionStrategy(GameObjectCollection object) {

        this.object = object;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (otherObj instanceof Ball)
            this.object.removeGameObject(thisObj);
    }
}
