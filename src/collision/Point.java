package collision;

/**
 * The type Point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Instantiates a new Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * returns this distance from another point.
     *
     * @param other the other
     * @return the double
     */
    public double distance(Point other) {
        double sqrXDistance = (this.x - other.getX()) * (this.x - other.getX());
        double sqrYDistance = (this.y - other.getY()) * (this.y - other.getY());
        double tempDistance = sqrXDistance + sqrYDistance;

        return Math.sqrt(tempDistance);
    }

    /**
     * checks if this point equals to another point.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean equals(Point other) {
        return ((this.x == other.getX()) && (this.y == other.getY()));
    }

    /**
     * Gets x of point.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets y of point.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }
}