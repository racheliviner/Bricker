package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Random;

public class BrickerGameManager extends GameManager{

    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController= windowController;
        this.windowDimensions = windowController.getWindowDimensions();

        //creating the background
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        gameObjects().addGameObject(background);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //creating the ball
        final float BALL_SPEED = 250;
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound= soundReader.readSound("assets/blop.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(20,20), ballImage, collisionSound);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean())
            ballVelY*=-1;
        ball.setVelocity(new Vector2(ballVelX,ballVelY));
        ball.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()-45));
        gameObjects().addGameObject(ball);


        //creating the paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(100,15), paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);


        //creating the walls
//        RectangleRenderer wallRect = new RectangleRenderer(new Color(211,211,211));
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(5, windowDimensions.y()),null);
        gameObjects().addGameObject(leftWall);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - 5, 0), new Vector2(5, windowDimensions.y()), null);
        gameObjects().addGameObject(rightWall);
        GameObject topWall = new GameObject(new Vector2(0, 0), new Vector2(windowDimensions.x(), 5), null);
        gameObjects().addGameObject(topWall);

        // creating a bricks
        final float BRICK_WIDTH = (float) windowDimensions.x()/8;

        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        GameObject brick;
        CollisionStrategy[] collisionStrategy = {new CollisionStrategy( gameObjects())};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                brick= new Brick(Vector2.ZERO, new Vector2(BRICK_WIDTH,20), brickImage, collisionStrategy[0]);
                gameObjects().addGameObject(brick);
                brick.setCenter(new Vector2(BRICK_WIDTH*j+BRICK_WIDTH/2,21*i+(float) 10));
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = ball.getCenter().y()-ball.getDimensions().y();
        String prompt = "";
        if (ballHeight>windowDimensions.y()){
            prompt = "You lose!";
        }
        if (!prompt.isEmpty()){
            prompt+=" Do you want to play again?";
            if(windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }

    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker",new Vector2(700,500)).run();
    }
}