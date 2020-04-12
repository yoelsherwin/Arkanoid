package levels;

import biuoop.DrawSurface;
import gamelogic.Sprite;

import java.awt.Color;

/**
 * The type Level 3 background.
 */
public class Level3Background implements Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(0, 153, 0));
        d.fillRectangle(20, 20, 760, 580);
        d.setColor(new Color(31, 33, 37));
        d.fillRectangle(80, 370, 120, 230);
        d.setColor(new Color(49, 48, 52));
        d.fillRectangle(120, 330, 40, 40);
        d.setColor(new Color(64, 68, 67));
        d.fillRectangle(135, 230, 10, 100);
        d.setColor(new Color(224, 147, 50));
        d.fillCircle(140, 225, 10);
        d.setColor(new Color(190, 92, 28));
        d.fillCircle(140, 225, 8);
        d.setColor(Color.WHITE);
        d.fillCircle(140, 225, 5);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                d.fillRectangle(93 + i * 25 , 380 +  j * 40 + 5, 20, 30);
            }
        }

    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }

}
