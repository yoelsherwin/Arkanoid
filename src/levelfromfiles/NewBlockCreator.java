package levelfromfiles;

import gamelogic.Block;

import java.awt.Color;
import java.awt.Image;
import java.util.Map;

/**
 * The type New block creator.
 */
public class NewBlockCreator implements BlockCreator {
    private Map<Integer, Color> colorFillMap;
    private Map<Integer, Image> imageFillMap;
    private Image fillImage;
    private Color fillColor;
    private Color stroke;
    private int height;
    private int width;
    private int hitPoints;

    /**
     * Instantiates a new New block creator.
     *
     * @param colorFillMap the color fill map
     * @param imageFillMap the image fill map
     * @param fillImage    the fill image
     * @param fillColor    the fill color
     * @param stroke       the stroke
     * @param hitPoints    the hit points
     */
    public NewBlockCreator(Map<Integer, Color> colorFillMap, Map<Integer, Image> imageFillMap, Image fillImage,
                           Color fillColor, Color stroke, int hitPoints) {
        this.colorFillMap = colorFillMap;
        this.imageFillMap = imageFillMap;
        this.fillImage = fillImage;
        this.fillColor = fillColor;
        this.stroke = stroke;
        this.hitPoints = hitPoints;
    }

    /**
     * Sets height.
     *
     * @param tempHeight the temp height
     */
    public void setHeight(int tempHeight) {
        this.height = tempHeight;
    }

    /**
     * Sets width.
     *
     * @param tempWidth the temp width
     */
    public void setWidth(int tempWidth) {
        this.width = tempWidth;
    }

    /**
     * Create a block at the specified location.
     *
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
    public Block create(int xpos, int ypos) {
        Block block;
        collision.Point upperLeft = new collision.Point(xpos, ypos);
        collision.Rectangle rect = new collision.Rectangle(upperLeft, width, height);
        if (this.fillImage == null) {
            block = new Block(rect, this.fillColor, this.hitPoints);
        } else {
            block = new Block(rect, this.fillImage, this.hitPoints);
        }
        block.setColorMap(this.colorFillMap);
        block.setImageMap(this.imageFillMap);
        block.setStroke(this.stroke);
        return block;
    }
}
