package collision;

/**
 * The type Velocity.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Instantiates a new Velocity.
     *
     * @param dx the dx of velocity
     * @param dy the dy of  velocity
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Gets dx.
     *
     * @return the dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets dy.
     *
     * @return the dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Gets velocity from speed and angle.
     *
     * @param angle the angle
     * @param speed the speed
     * @return the velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle - 90);
        double dx = Math.cos(radians) * speed;
        double dy = Math.sin(radians) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p the p
     * @return the point
     */
    public Point applyToPoint(Point p) {
        Point newPoint = new Point(p.getX() + this.dx, p.getY() + this.dy);
        return newPoint;
    }
}
