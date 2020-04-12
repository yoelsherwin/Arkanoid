package gamelogic;

import animation.Animation;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import animation.CountdownAnimation;
import animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.Point;
import collision.Rectangle;
import indicators.Counter;
import indicators.LevelNameIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import levels.LevelInformation;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import java.awt.Color;

/**
 * The type GameLevel.
 */
public class GameLevel implements Animation {
    private LevelInformation levelInformation;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter remainingLives;
    private Paddle paddle;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;


    /**
     * Instantiates a new GameLevel.
     *
     * @param levelInfo the level info
     * @param ks        the ks
     * @param ar        the ar
     * @param gui       the gui
     * @param lc        the lc
     * @param sc        the sc
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor ks, AnimationRunner ar, GUI gui, Counter lc,
                     Counter sc) {
        this.levelInformation = levelInfo;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = gui;
        this.remainingBlocks = new Counter(this.levelInformation.numberOfBlocksToRemove());
        this.remainingBalls = new Counter(0);
        this.score = sc;
        this.remainingLives = lc;
        this.runner = ar;
        this.running = true;
        this.keyboard = ks;
    }

    /**
     * Sets paddle.
     *
     * @param padd the padd
     */
    public void setPaddle(Paddle padd) {
        this.paddle = padd;
    }

    /**
     * Gets remaining blocks.
     *
     * @return the remaining blocks
     */
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }

    /**
     * Gets remaining lives.
     *
     * @return the remaining lives
     */
    public Counter getRemainingLives() {
        return remainingLives;
    }

    /**
     * Gets remaining balls.
     *
     * @return the remaining balls
     */
    public Counter getRemainingBalls() {
        return remainingBalls;
    }

    /**
     * Gets level information.
     *
     * @return the level information
     */
    public LevelInformation getLevelInformation() {
        return levelInformation;
    }

    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        this.addSprite(this.levelInformation.getBackground());

        // indicators background
        Point p = new Point(0, 0);
        Rectangle r = new Rectangle(p, 800, 20);
        Block indicatorsBackground = new Block(r, Color.GRAY, 0);
        this.sprites.addSprite(indicatorsBackground);


        //creating lives indicator
        p = new Point(100, 15);
        LivesIndicator livesIndicator = new LivesIndicator(this.remainingLives, p);
        this.sprites.addSprite(livesIndicator);

        //creating score indicator
        p = new Point(300, 15);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score, p);
        this.sprites.addSprite(scoreIndicator);

        //creating level name indicator
        p = new Point(500, 15);
        LevelNameIndicator levelNameIndicator = new LevelNameIndicator(this.levelInformation.levelName(), p);
        this.sprites.addSprite(levelNameIndicator);

        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);

        // generating blocks
        for (Block b : this.levelInformation.blocks()) {
            b.addToGame(this);
            b.addHitListener(scoreTrackingListener);
            b.addHitListener(blockRemover);
        }

        // generating up border
        p = new Point(0, 20);
        r = new Rectangle(p, 800, 20);
        Block upBorder = new Block(r, Color.GRAY, 0);
        upBorder.addToGame(this);
        // generating right border
        p = new Point(780, 40);
        r = new Rectangle(p, 20, 560);
        Block rightBorder = new Block(r, Color.GRAY, 0);
        rightBorder.addToGame(this);
        // generating left border
        p = new Point(0, 40);
        r = new Rectangle(p, 20, 560);
        Block leftBorder = new Block(r, Color.GRAY, 0);
        leftBorder.addToGame(this);

        // creating the death block listener and adding it
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        // generating death block
        p = new Point(0, 610);
        r = new Rectangle(p, 800, 20);
        Block deathBlock = new Block(r, Color.GRAY, 0);
        deathBlock.addToGame(this);
        deathBlock.addHitListener(ballRemover);
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() { return !this.running; }

    /**
     * Do one frame.
     *
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        // if all balls are dead
        if (this.remainingBalls.getValue() == 0) {
            this.running = false;
        }

        // if all block are dead
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        }

        // if game is paused
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen(this.keyboard)));
        }
    }

    /**
     * Play one turn.
     */
    public void playOneTurn() {
        this.generateBallsAndPaddle();
        Animation count = new CountdownAnimation(2, 3, this.sprites);
        this.runner.run(count);
        this.running = true;
        this.runner.run(this);
    }

    /**
     * Generate balls and paddle.
     */
    public void generateBallsAndPaddle() {
        int paddleVel = this.levelInformation.paddleSpeed();
        int paddleWidth = this.levelInformation.paddleWidth();
        int paddleX = 400 - this.levelInformation.paddleWidth() / 2;

        // creating paddle
        Rectangle r = new Rectangle(new Point(paddleX, 560), paddleWidth, 20);
        Paddle padd = new Paddle(this.keyboard, r, Color.YELLOW, paddleVel, 780, 20);
        padd.addToGame(this);
        setPaddle(padd);

        // creating balls and setting their velocities
        Ball[] balls = new Ball[this.levelInformation.numberOfBalls()];
        for (int i = 0; i < this.levelInformation.numberOfBalls(); i++) {
            balls[i] = new Ball(new Point(400, 550), 5, Color.WHITE);
            balls[i].setVelocity(this.levelInformation.initialBallVelocities().get(i));
            balls[i].setGameEnvironment(environment);
            balls[i].addToGame(this);
        }
    }

    /**
     * Remove paddle.
     *
     * @param game the game
     */
    public void removePaddle(GameLevel game) {
        this.paddle.removePaddleFromGame(game);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.environment.getCollidables().remove(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSprites().remove(s);
    }
}