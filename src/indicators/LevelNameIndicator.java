package indicators;

import biuoop.DrawSurface;
import collision.Point;
import gamelogic.Sprite;

import java.awt.Color;

/**
 * The type Level name indicator.
 */
public class LevelNameIndicator implements Sprite {
    private String name;
    private Point point;

    /**
     * Instantiates a new Live indicator.
     *
     * @param name  the name
     * @param point the point
     */
    public LevelNameIndicator(String name, Point point) {
        this.name = name;
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
        String s = "Level Name: " + this.name;
        d.drawText(x, y, s, 15);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }
}
