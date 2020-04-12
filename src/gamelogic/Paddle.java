package gamelogic;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.Line;
import collision.Point;
import collision.Rectangle;
import collision.Velocity;


import java.awt.Color;

/**
 * The type Paddle.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private Color color;
    private int dx;
    private int rightBorder;
    private int leftBorder;

    /**
     * Instantiates a new Paddle.
     *
     * @param sensor      the sensor
     * @param rect        the rect
     * @param color       the color
     * @param dx          the dx
     * @param rightBorder the right border
     * @param leftBorder  the leftt border
     */
    public Paddle(biuoop.KeyboardSensor sensor, Rectangle rect, Color color, int dx, int rightBorder, int leftBorder) {
        this.keyboard = sensor;
        this.rect = rect;
        this.color = color;
        this.dx = dx;
        this.rightBorder = rightBorder;
        this.leftBorder = leftBorder;
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        // move only if the paddle is not in the borders
        if (this.rect.getUpperLeft().getX() > this.leftBorder) {
            Point nextPoint = new Point(this.rect.getUpperLeft().getX() - this.dx, this.rect.getUpperLeft().getY());
            this.rect = new Rectangle(nextPoint, this.rect.getWidth(), this.rect.getHeight());
        }
    }

    /**
     * Move right.
     */
    public void moveRight() {
        // move only if the paddle is not in the borders
        if (this.rect.getUpperLeft().getX() + this.rect.getWidth() < this.rightBorder) {
            Point nextPoint = new Point(this.rect.getUpperLeft().getX() + this.dx, this.rect.getUpperLeft().getY());
            this.rect = new Rectangle(nextPoint, this.rect.getWidth(), this.rect.getHeight());
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * Draw the collidable on the surface.
     *
     * @param d the surface
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        // fill the paddle
        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);
        // draw the frame of the paddle
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * Gets collision rectangle.
     *
     * @return the collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Gets dx.
     *
     * @return the dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Remove paddle from game.
     *
     * @param game the game
     */
    public void removePaddleFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * returns the velocity after the hit.
     *
     * @param hitter the hitter
     * @param collisionPoint the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double upperLeftX = this.rect.getUpperLeft().getX();
        double upperLeftY = this.rect.getUpperLeft().getY();
        double recWidth = this.rect.getWidth();
        double recHeight = this.rect.getHeight();
        // calculate the lines of the paddle
        Line l1 = new Line(upperLeftX, upperLeftY, upperLeftX + recWidth, upperLeftY);
        Line l2 = new Line(upperLeftX + recWidth, upperLeftY, upperLeftX + recWidth, upperLeftY + recHeight);
        Line l3 = new Line(upperLeftX, upperLeftY + recHeight, upperLeftX + recWidth, upperLeftY + recHeight);
        Line l4 = new Line(upperLeftX, upperLeftY, upperLeftX, upperLeftY + recHeight);
        // if paddle is hit from above - check where and assign the correct velocity
        if ((l1.pointInLine(collisionPoint)) && (currentVelocity.getDy() > 0)) {
            double speed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
            Line reg1 = new Line(upperLeftX, upperLeftY,
                    upperLeftX + (recWidth / 5), upperLeftY);
            Line reg2 = new Line(upperLeftX + (recWidth / 5), upperLeftY,
                    upperLeftX + 2 * (recWidth / 5), upperLeftY);
            Line reg3 = new Line(upperLeftX + 2 * (recWidth / 5), upperLeftY,
                    upperLeftX + 3 * (recWidth / 5), upperLeftY);
            Line reg4 = new Line(upperLeftX + 3 * (recWidth / 5), upperLeftY,
                    upperLeftX + 4 * (recWidth / 5), upperLeftY);
            Line reg5 = new Line(upperLeftX + 4 * (recWidth / 5), upperLeftY,
                    upperLeftX + recWidth, upperLeftY);
            if (reg1.pointInLine(collisionPoint)) {
                currentVelocity = Velocity.fromAngleAndSpeed(300, speed);
            } else if (reg2.pointInLine(collisionPoint)) {
                currentVelocity = Velocity.fromAngleAndSpeed(330, speed);
            } else if (reg3.pointInLine(collisionPoint)) {
                currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (reg4.pointInLine(collisionPoint)) {
                currentVelocity = Velocity.fromAngleAndSpeed(30, speed);
            } else if (reg5.pointInLine(collisionPoint)) {
                currentVelocity = Velocity.fromAngleAndSpeed(60, speed);
            }
        }
        // if paddle is hit from below
        if ((l3.pointInLine(collisionPoint) && (currentVelocity.getDy() < 0))) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        // if paddle is hit from one of the sides
        if (((l4.pointInLine(collisionPoint)) && (currentVelocity.getDx() > 0))
                || (l2.pointInLine(collisionPoint) && (currentVelocity.getDx() < 0))) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        return currentVelocity;
    }

    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}