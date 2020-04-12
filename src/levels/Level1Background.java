package levels;

import biuoop.DrawSurface;
import gamelogic.Sprite;

import java.awt.Color;

/**
 * The type Level 1 background.
 */
public class Level1Background implements Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(20, 20, 760, 580);
        d.setColor(Color.BLUE);
        d.drawCircle(400, 150, 100);
        d.drawCircle(400, 150, 70);
        d.drawCircle(400, 150, 40);
        d.drawLine(400, 170, 400, 270);
        d.drawLine(400, 130, 400, 30);
        d.drawLine(380, 150, 280, 150);
        d.drawLine(420, 150, 520, 150);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }

}
