package collision;

/**
 * The type Collision info.
 */
public class CollisionInfo {
    private Collidable cObject;
    private Point cPoint;

    /**
     * Instantiates a new Collision info.
     *
     * @param collisionObject the collision object
     * @param collisionPoint  the collision point
     */
    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        this.cObject = collisionObject;
        this.cPoint = collisionPoint;
    }

    /**
     * Collision point point.
     *
     * @return the point
     */
    public Point collisionPoint() {
        return this.cPoint;
    }

    /**
     * Collision object collidable.
     *
     * @return the collidable
     */
    public Collidable collisionObject() {
        return this.cObject;
    }
}