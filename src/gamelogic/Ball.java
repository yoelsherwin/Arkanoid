package gamelogic;

import biuoop.DrawSurface;
import collision.CollisionInfo;
import collision.Line;
import collision.Point;
import collision.Velocity;

import java.awt.Color;

/**
 * The type Ball.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private int rightBorder;
    private int leftBorder;
    private int upBorder;
    private int downBorder;
    private GameEnvironment gameEnvironment;

    /**
     * Instantiates a new Ball.
     *
     * @param center the center
     * @param r      the r
     * @param color  the color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    /**
     * Instantiates a new Ball.
     *
     * @param center the center point
     * @param r      the radius
     * @param color  the color of ball
     * @param right  the right border
     * @param left   the left border
     * @param down   the down border
     * @param up     the up border
     */
    public Ball(Point center, int r, java.awt.Color color, int right, int left, int down, int up) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.rightBorder = right;
        this.leftBorder = left;
        this.upBorder = up;
        this.downBorder = down;
    }

    /**
     * Instantiates a new Ball.
     *
     * @param centerX the center x
     * @param centerY the center y
     * @param r       the radius
     * @param color   the color of the ball
     */
    public Ball(double centerX, double centerY, int r, java.awt.Color color) {
        this.center = new Point(centerX, centerY);
        this.radius = r;
        this.color = color;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return (int) (3.14 * this.radius * this.radius);
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getRightBorder() {
        return this.rightBorder;
    }

    /**
     * Gets left border.
     *
     * @return the left border
     */
    public int getLeftBorder() {
        return this.leftBorder;
    }

    /**
     * Gets up border.
     *
     * @return the up border
     */
    public int getUpBorder() {
        return this.upBorder;
    }

    /**
     * Gets down border.
     *
     * @return the down border
     */
    public int getDownBorder() {
        return this.downBorder;
    }


    /**
     * Draws the ball on surface.
     *
     * @param surface the surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        // fill the ball
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.getRadius());
        // draw the frame of the ball
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.getRadius());
    }

    /**
     * Sets velocity of ball.
     *
     * @param v the velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets game environment.
     *
     * @param ge the game envoirnment
     */
    public void setGameEnvironment(GameEnvironment ge) {
        this.gameEnvironment = ge;
    }

    /**
     * Sets velocity of ball.
     *
     * @param dx the dx of velocity
     * @param dy the dy of velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Gets velocity.
     *
     * @return the velocity of ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moves ball one step.
     */
    public void moveOneStep() {
        //if no velocity was initiallized
        if (this.velocity == null) {
            return;
        }
        double centerX = this.center.getX();
        double centerY = this.center.getY();
        int rad = this.getRadius();
        double velX = this.velocity.getDx();
        double velY = this.velocity.getDy();
        // calculate the line of the next step
        Line trajectory = new Line(this.center, new Point(centerX + velX, centerY + velY));
        // check if collides with one of the collidables
        CollisionInfo collision = this.gameEnvironment.getClosestCollision(trajectory);
        Velocity temp;

        // moves the ball one step. if there is no collision continue, else move according to collision
        if (collision == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            temp = collision.collisionObject().hit(this, collision.collisionPoint(), this.velocity);
            this.setVelocity(temp);
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Add ball to game.
     *
     * @param game the game
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * removes object from game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}

