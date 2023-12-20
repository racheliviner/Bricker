package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Vector2;

public class CollisionStrategy {

    private GameObjectCollection object;
    private int numOfBricks;

    public CollisionStrategy(GameObjectCollection object, int numOfBricks) {
        this.object = object;
        this.numOfBricks = numOfBricks;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (otherObj instanceof Ball) {
            if(numOfBricks==1)
                thisObj.setCenter(new Vector2(-50,-50));
            this.object.removeGameObject(thisObj);
            numOfBricks--;
        }
    }

    public int getNumOfBricks() {
        return numOfBricks;
    }
}
