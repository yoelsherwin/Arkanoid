package menu;

import biuoop.DrawSurface;
import gamelogic.Sprite;

import java.awt.Color;

/**
 * The type Menu background.
 */
public class MenuBackground implements Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
    }
}
