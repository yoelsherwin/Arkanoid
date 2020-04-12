package collision;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Rectangle.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    /**
     * Instantiates a new Rectangle.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /**
     * generates list of intersection points.
     *
     * @param line the line
     * @return the list
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        double upperLeftX = this.upperLeft.getX();
        double upperLeftY = this.upperLeft.getY();
        double recWidth = this.getWidth();
        double recHeight = this.getHeight();
        List<Point> list = new ArrayList<>();
        Line[] rectLines = new Line[4];
        // calculate 4 lines of the rectangle
        rectLines[0] = new Line(upperLeftX, upperLeftY, upperLeftX + recWidth, upperLeftY);
        rectLines[1] = new Line(upperLeftX + recWidth, upperLeftY, upperLeftX + recWidth, upperLeftY + recHeight);
        rectLines[2] = new Line(upperLeftX + recWidth, upperLeftY + recHeight, upperLeftX, upperLeftY + recHeight);
        rectLines[3] = new Line(upperLeftX, upperLeftY + recHeight, upperLeftX, upperLeftY);
        // check if intersects with line
        for (int i = 0; i < rectLines.length; i++) {
            if (rectLines[i].isIntersecting(line)) {
                list.add(rectLines[i].intersectionWith(line));
            }
        }
        return list;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Gets upper left.
     *
     * @return the upper left
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}