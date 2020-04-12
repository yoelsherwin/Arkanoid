package indicators;

import biuoop.DrawSurface;
import collision.Point;
import gamelogic.Sprite;

import java.awt.Color;


/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private Point point;

    /**
     * Instantiates a new Score indicator.
     *
     * @param score the score
     * @param point the point
     */
    public ScoreIndicator(Counter score, Point point) {
        this.score = score;
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

        // draw the correct score
        d.setColor(Color.BLACK);
        String s = "Score: " + this.score.getValue();
        d.drawText(x, y, s, 15);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }
}