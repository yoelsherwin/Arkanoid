package indicators;

import biuoop.DrawSurface;
import collision.Point;
import gamelogic.Sprite;

import java.awt.Color;


/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    private Point point;

    /**
     * Instantiates a new Live indicator.
     *
     * @param lives the lives
     * @param point the point
     */
    public LivesIndicator(Counter lives, Point point) {
        this.lives = lives;
        this.point = point;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.point.getX();
        int y = (int) this.point.getY();

        // draw the correct lives
        d.setColor(Color.BLACK);
        String s = "Lives: " + this.lives.getValue();
        d.drawText(x, y, s, 15);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }
}
