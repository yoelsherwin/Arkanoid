package gamelogic;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Sprite collection.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Instantiates a new Sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Add sprite.
     *
     * @param s the sptire being added
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Gets sprites.
     *
     * @return the sprites
     */
    public List<Sprite> getSprites() {
        return sprites;
    }

    /**
     * Notify all time passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesList = new ArrayList<>(this.sprites);
        for (Sprite sprite : spritesList) {
            sprite.timePassed();
        }
    }

    /**
     * Draw all on the draw surface.
     *
     * @param d the draw surface
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> spritesList = new ArrayList<>(this.sprites);
        for (Sprite sprite : spritesList) {
            sprite.drawOn(d);
        }
    }
}