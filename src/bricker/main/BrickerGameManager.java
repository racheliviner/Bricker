package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Heart;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class BrickerGameManager extends GameManager {

    private Vector2 windowDimensions;
    private WindowController windowController;
    private Ball ball;
    private Heart hearts;
    private Paddle paddle;
    private CollisionStrategy collisionStrategy;

    // Constructor for creating BrickerGameManager
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    // Initialization method called once when the game starts
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();

        // Creating the background
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        gameObjects().addGameObject(background);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        // Creating the paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        paddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);

        // Creating the ball
        final float BALL_SPEED = 250;
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage, collisionSound);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 46));
        gameObjects().addGameObject(ball);

        // Creating the walls
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(5, windowDimensions.y()), null);
        gameObjects().addGameObject(leftWall);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - 5, 0), new Vector2(5, windowDimensions.y()), null);
        gameObjects().addGameObject(rightWall);
        GameObject topWall = new GameObject(new Vector2(0, 0), new Vector2(windowDimensions.x(), 5), null);
        gameObjects().addGameObject(topWall);

        // Creating bricks
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        final float BRICK_WIDTH = windowDimensions.x() / 8;
        collisionStrategy = new CollisionStrategy(gameObjects(), 5 * 8);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                GameObject brick = new Brick(Vector2.ZERO, new Vector2(BRICK_WIDTH, 20), brickImage, collisionStrategy);
                brick.setCenter(new Vector2(BRICK_WIDTH * j + BRICK_WIDTH / 2, 21 * i + (float) 10));
                gameObjects().addGameObject(brick);
            }
        }

        // Creating the heart
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        final int NUM_OF_HEART = 3;
        hearts = new Heart(Vector2.ZERO, new Vector2(20, 20), heartImage, gameObjects(), NUM_OF_HEART);
    }

    private boolean winCondition = false;

    // Update method called in each frame
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Display a prompt if the game is won or lost
        String prompt = "";
        if (hearts.GetNumOfHearts() == 0)
            prompt = "You lost!";
        if (winCondition)
            prompt = "You won!";
        if (!prompt.isEmpty()) {
            prompt += " Do you want to play again?";
            if (windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }

        // Reset paddle and ball positions when ball goes beyond the screen
        float ballHeight = ball.getCenter().y() - ball.getDimensions().y();
        if (ballHeight > windowDimensions.y()) {
            hearts.removeHeart();
            paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
            ball.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 46));
        }

        // Check win condition
        if (collisionStrategy.getNumOfBricks() == 0) {
            winCondition = true;
            paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
            ball.setCenter(new Vector2(-50, -50));
        }
    }

    // Main method to run the game
    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}
