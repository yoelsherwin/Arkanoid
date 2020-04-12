package levelfromfiles;

import biuoop.DrawSurface;
import gamelogic.Sprite;

import java.awt.Image;
import java.awt.Color;


/**
 * The type Background.
 */
public class Background implements Sprite {
    private Image image;
    private Color color;

    /**
     * Instantiates a new Background.
     *
     * @param image the image
     */
    public Background(Image image) {
        this.image = image;
        this.color = null;
    }

    /**
     * Instantiates a new Background.
     *
     * @param color the color
     */
    public Background(Color color) {
        this.color = color;
        this.image = null;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        if (this.color == null) {
            d.drawImage(0, 0, this.image);
        } else {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }
}
