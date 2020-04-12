package collision;

import biuoop.DrawSurface;
import gamelogic.Ball;

/**
 * The interface Collidable.
 */
public interface Collidable {
    /**
     * Gets collision rectangle.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * returns the velocity after the hit.
     *
     * @param hitter          the hitter
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Draw the collidable on the surface.
     *
     * @param surface the surface
     */
    void drawOn(DrawSurface surface);
}