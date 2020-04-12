package collision;

import java.util.List;

/**
 * The type Line.
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * Instantiates a new Line.
     *
     * @param start the start
     * @param end   the end
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Instantiates a new Line.
     *
     * @param x1 the x of start point.
     * @param y1 the y of start point.
     * @param x2 the x of end point.
     * @param y2 the y of end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * returns length of line.
     *
     * @return distance double
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the point
     */
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        Point middle = new Point(middleX, middleY);
        return middle;
    }

    /**
     * returns the starting point of the line.
     *
     * @return the point
     */
    public Point start() {
        return this.start;
    }

    /**
     * returns the ending point of the line.
     *
     * @return the point
     */
    public Point end() {
        return this.end;
    }

    /**
     * checks if this line is intersecting with another line.
     *
     * @param other the other line
     * @return true if intersects, false if not.
     */
    public boolean isIntersecting(Line other) {
        return (this.intersectionWith(other) != null);
    }

    /**
     * Intersection with point.
     *
     * @param other the other line
     * @return the point
     */
    public Point intersectionWith(Line other) {
        double xInter;
        double yInter;
        double x1 = this.start.getX();
        double x2 = this.end.getX();
        double y1 = this.start.getY();
        double y2 = this.end.getY();
        double m1;
        double b1;
        double x3 = other.start.getX();
        double x4 = other.end.getX();
        double y3 = other.start.getY();
        double y4 = other.end.getY();
        double m2;
        double b2;
        // trying to find slope of first line
        try {
            m1 = this.getSlope();
            // finding y intercept of first line
            b1 = y1 - m1 * x1;
            // trying to find slope of second line
            try {
                m2 = other.getSlope();
                // finding y intercept of second line
                b2 = y3 - m2 * x3;
                // if lines are parallel - there is no intersection point between them.
                if (m1 == m2) {
                    return null;
                }
                // finding x and y of intersection point
                xInter = (b2 - b1) / (m1 - m2);
                yInter = m1 * xInter + b1;
                // checking if the point is on both segments and then returns point/null.
                if (((yInter >= Math.min(y1, y2)) && (yInter <= Math.max(y1, y2)))
                        && ((yInter >= Math.min(y3, y4)) && (yInter <= Math.max(y3, y4))
                        && ((xInter >= Math.min(x1, x2)) && (xInter <= Math.max(x1, x2)))
                        && ((xInter >= Math.min(x3, x4)) && (xInter <= Math.max(x3, x4))))) {
                    return new Point(xInter, yInter);
                } else {
                    return null;
                }
                // if second line has no slope
            } catch (Exception e3) {
                // the intersection (if exists) must be with th x of the second line.
                xInter = x3;
                // finding y intercept with equation
                yInter = m1 * x3 + b1;
                // checking if the point is on both segments and then returns point/null.
                if (((yInter >= Math.min(y1, y2)) && (yInter <= Math.max(y1, y2)))
                        && ((yInter >= Math.min(y3, y4)) && (yInter <= Math.max(y3, y4))
                        && ((xInter >= Math.min(x1, x2)) && (xInter <= Math.max(x1, x2)))
                        && ((xInter >= Math.min(x3, x4)) && (xInter <= Math.max(x3, x4))))) {
                    return new Point(xInter, yInter);
                } else {
                    return null;
                }
            }
            // if the first line has no slope
        } catch (Exception e1) {
            // checking if second line has slope
            try {
                m2 = other.getSlope();
                // finding y intercept of second line
                b2 = y3 - m2 * x3;
                // the intersection (if exists) must be with the x of the first line.
                xInter = x1;
                // finding y intercept with equation
                yInter = m2 * x1 + b2;
                // checking if the point is on both segments and then returns point/null.
                if (((yInter >= Math.min(y1, y2)) && (yInter <= Math.max(y1, y2)))
                        && ((yInter >= Math.min(y3, y4)) && (yInter <= Math.max(y3, y4))
                        && ((xInter >= Math.min(x1, x2)) && (xInter <= Math.max(x1, x2)))
                        && ((xInter >= Math.min(x3, x4)) && (xInter <= Math.max(x3, x4))))) {
                    return new Point(xInter, yInter);
                } else {
                    return null;
                }
            /*
            if both lines have no slope than they are both parallel to the y axis and parallel to each other. and
            therefore there isno intersection point between them.
            */
            } catch (Exception e2) {
                return null;
            }
        }
    }

    /**
     * Gets slope of line.
     *
     * @return the slope
     * @throws Exception the exception
     */
    public double getSlope() throws Exception {
        // if line is parallel to y y axis there is no slope, throw exception
        if (this.end.getX() == this.start.getX()) {
            throw new Exception("Can't find slope, division by 0");
        }
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     * Checks if line equals to another line.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean equals(Line other) {
        return (((this.start.equals(other.start)) && (this.end.equals(other.end)))
                || ((this.start.equals(other.end)) && (this.end.equals(other.start))));
    }

    /**
     * Checks if point is in line.
     *
     * @param p the point
     * @return the boolean
     */
    public boolean pointInLine(Point p) {
        return ((p.distance(this.start()) + this.end().distance(p)) == this.start().distance(this.end()));
    }

    /**
     * Finds the closest intersection point of the rectangle and the line to the start of line.
     *
     * @param rect the rectangle
     * @return the intersection point of line and rect with minimum distance from start point of the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // creating list of intersection points
        List<Point> list = rect.intersectionPoints(this);
        // if the list is empty then there are no intersection point, thus return null
        if (list.size() == 0) {
            return null;
        }
        // set min point and min distance to the first intersection point
        double minDistance = this.start.distance(list.get(0));
        Point min = list.get(0);
        // compare to other intersection points and find the min point
        for (int i = 1; i < list.size(); i++) {
            if (this.start.distance(list.get(i)) < minDistance && this.start.distance(list.get(i)) > 0.0001) {
                minDistance = this.start.distance(list.get(i));
                min = list.get(i);
            }
        }
        return min;
    }
}