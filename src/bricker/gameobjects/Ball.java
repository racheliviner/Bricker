package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    Sound collisionSound;
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
    }
}
