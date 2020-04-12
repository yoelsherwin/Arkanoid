package gamelogic;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.HitNotifier;
import collision.Rectangle;
import collision.Velocity;
import collision.Point;
import collision.Line;
import listeners.HitListener;


import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Block.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private java.awt.Color defFillColor;
    private Image defFillImage;
    private Map<Integer, Color> colorMap;
    private Map<Integer, Image> imageMap;
    private int hitPoints;
    private List<HitListener> hitListeners;
    private Color stroke;


    /**
     * Instantiates a new Block.
     *
     * @param rect         the rect
     * @param defFillColor the defFillColor
     * @param hitPoints    the hitPoints
     */
    public Block(Rectangle rect, java.awt.Color defFillColor, int hitPoints) {
        this.rect = rect;
        this.defFillColor = defFillColor;
        this.defFillImage = null;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        this.stroke = null;
    }

    /**
     * Instantiates a new Block.
     *
     * @param rect         the rect
     * @param defFillImage the def fill image
     * @param hitPoints    the hit points
     */
    public Block(Rectangle rect, Image defFillImage, int hitPoints) {
        this.rect = rect;
        this.defFillImage = defFillImage;
        this.defFillColor = null;
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Sets image map.
     *
     * @param newImageMap the new image map
     */
    public void setImageMap(Map<Integer, Image> newImageMap) {
        this.imageMap = newImageMap;
    }

    /**
     * Sets color map.
     *
     * @param newColorMap the color map
     */
    public void setColorMap(Map<Integer, Color> newColorMap) {
        this.colorMap = newColorMap;
    }

    /**
     * Sets stroke.
     *
     * @param newStroke the new stroke
     */
    public void setStroke(Color newStroke) {
        this.stroke = newStroke;
    }

    /**
     * Gets collision rectangle.
     *
     * @return the collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Gets defFillColor.
     *
     * @return the defFillColor
     */
    public Color getDefFillColor() {
        return defFillColor;
    }

    /**
     * Gets hitPoints.
     *
     * @return the hitPoints
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * returns the velocity after the hit.
     *
     * @param hitter the hitter
     * @param collisionPoint the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double upperLeftX = this.rect.getUpperLeft().getX();
        double upperLeftY = this.rect.getUpperLeft().getY();
        double recWidth = this.rect.getWidth();
        double recHeight = this.rect.getHeight();
        // first, unless block is dead - reduce hitPoints
        if (this.hitPoints != 0) {
            this.reduceCounter();
        }
        // calculate 4 lines of the block
        Line l1 = new Line(upperLeftX, upperLeftY, upperLeftX + recWidth, upperLeftY);
        Line l2 = new Line(upperLeftX + recWidth, upperLeftY, upperLeftX + recWidth, upperLeftY + recHeight);
        Line l3 = new Line(upperLeftX, upperLeftY + recHeight, upperLeftX + recWidth, upperLeftY + recHeight);
        Line l4 = new Line(upperLeftX, upperLeftY, upperLeftX, upperLeftY + recHeight);
        // check if dy is needed to be changed (hit upper line or bottom line)
        if (((l1.pointInLine(collisionPoint)) && (currentVelocity.getDy() > 0))
                || (l3.pointInLine(collisionPoint) && (currentVelocity.getDy() < 0))) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        // check if dx is needed to be changed (hit right line or left line)
        if (((l4.pointInLine(collisionPoint)) && (currentVelocity.getDx() > 0))
                || (l2.pointInLine(collisionPoint) && (currentVelocity.getDx() < 0))) {
            currentVelocity = new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        this.notifyHit(hitter);
        return currentVelocity;
    }

    /**
     * Reduce hitPoints.
     */
    public void reduceCounter() {
        this.hitPoints -= 1;
    }

    /**
     * Draw on.
     *
     * @param surface the surface
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();

        // if block is border (0 hit points)
        if (this.hitPoints == 0) {
            if (this.defFillColor == null) {
                surface.drawImage(x, y, this.defFillImage);
            } else {
                surface.setColor(this.defFillColor);
                surface.fillRectangle(x, y, width, height);
            }
            if (this.stroke != null) {
                surface.setColor(this.stroke);
                surface.drawRectangle(x, y, width, height);
            }
        } else {                                                                // not a border block
            // fill the block
            if (this.imageMap.containsKey(this.hitPoints)) {                    // fill-k with image
                surface.drawImage(x, y, this.imageMap.get(this.hitPoints));
            } else if (this.colorMap.containsKey(this.hitPoints)) {             // fill-k with color
                surface.setColor(this.colorMap.get(this.hitPoints));
                surface.fillRectangle(x, y, width, height);
            } else {
                if (this.defFillImage == null) {                                // fill with def color
                    surface.setColor(this.defFillColor);
                    surface.fillRectangle(x, y, width, height);
                } else {                                                        // fill with def image
                    surface.drawImage(x, y, this.defFillImage);
                }
            }
            // draw the frame of the block
            if (this.stroke != null) {
                surface.setColor(this.stroke);
                surface.drawRectangle(x, y, width, height);
            }
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() { }

    /**
     * Add block to game.
     *
     * @param game the game
     */
    public void addToGame(GameLevel game) {
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl the hl
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hl
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * removes object from game.
     *
     * @param game the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * notify block that was hit.
     *
     * @param hitter the ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}