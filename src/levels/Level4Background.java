package levels;

import biuoop.DrawSurface;
import gamelogic.Sprite;

import java.awt.Color;

/**
 * The type Level 4 background.
 */
public class Level4Background implements Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(72, 177, 232));
        d.fillRectangle(20, 20, 760, 580);

        d.setColor(new Color(239, 238, 221));
        for (int i = 0; i < 12; i++) {
            d.drawLine(90 + 7 * i, 410, 70 + 7 * i, 600);
        }

        d.setColor(new Color(186, 189, 185));
        d.fillCircle(120, 400, 39);
        d.setColor(new Color(213, 225, 200));
        d.fillCircle(160, 410, 30);
        d.setColor(new Color(196, 201, 191));
        d.fillCircle(120, 430, 30);
        d.setColor(new Color(209, 208, 203));
        d.fillCircle(85, 420, 33);
        d.setColor(new Color(188, 187, 183));
        d.fillCircle(155, 435, 28);

        d.setColor(new Color(239, 238, 221));
        for (int i = 0; i < 12; i++) {
            d.drawLine(590 + 7 * i, 410, 570 + 7 * i, 600);
        }

        d.setColor(new Color(186, 189, 185));
        d.fillCircle(620, 400, 39);
        d.setColor(new Color(213, 225, 200));
        d.fillCircle(660, 410, 30);
        d.setColor(new Color(196, 201, 191));
        d.fillCircle(620, 430, 30);
        d.setColor(new Color(209, 208, 203));
        d.fillCircle(585, 420, 33);
        d.setColor(new Color(188, 187, 183));
        d.fillCircle(655, 435, 28);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }
}
