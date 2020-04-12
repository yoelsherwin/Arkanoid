package levels;

import biuoop.DrawSurface;
import gamelogic.Sprite;

import java.awt.Color;

/**
 * The type Level 2 background.
 */
public class Level2Background implements Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(20, 20, 760, 580);
        d.setColor(new Color(217, 184, 136));
        d.fillCircle(110, 110, 70);
        for (int i = 0; i < 96; i++) {
            d.drawLine(110, 110, 20 + 8 * i, 210);
        }
        d.setColor(new Color(224, 177, 39));
        d.fillCircle(110, 110, 65);
        d.setColor(new Color(224, 216, 74));
        d.fillCircle(110, 110, 60);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }
}
