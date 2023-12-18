package bricker.main;

import bricker.gameobjects.Ball;
import bricker.gameobjects.UserPeddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class BrickerGameManager extends GameManager{

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Vector2 windowDimensions = windowController.getWindowDimensions();


        //creating the backround
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        gameObjects().addGameObject(background);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //creating the ball
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound= soundReader.readSound("assets/blop.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(20,20), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);

        //creating the peddles
        //user peddle
        Renderable peddleImage = imageReader.readImage("assets/paddle.png", true);

        GameObject userPeddle = new UserPeddle(Vector2.ZERO, new Vector2(100,15), peddleImage, inputListener);
        userPeddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(userPeddle);
        //AI peddle
        GameObject aiPeddle = new GameObject(Vector2.ZERO, new Vector2(100,15), peddleImage);
        aiPeddle.setCenter(new Vector2(windowDimensions.x()/2, 30));
        gameObjects().addGameObject(aiPeddle);

        //creating the walls
        RectangleRenderable wallRect = new RectangleRenderable(new Color(211,211,211));
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(10, windowDimensions.y()),wallRect);
        gameObjects().addGameObject(leftWall);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - 10, 0), new Vector2(10, windowDimensions.y()), wallRect);
        gameObjects().addGameObject(rightWall);
        GameObject topWall = new GameObject(new Vector2(0, 0), new Vector2(windowDimensions.x(), 10), wallRect);
        gameObjects().addGameObject(topWall);
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker",new Vector2(700,500)).run();
    }
}