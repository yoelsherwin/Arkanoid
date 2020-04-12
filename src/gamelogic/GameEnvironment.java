package gamelogic;

import collision.Collidable;
import collision.CollisionInfo;
import collision.Line;
import collision.Point;
import collision.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * The type GameLevel environment.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Instantiates a new GameLevel environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Gets collidables.
     *
     * @return the collidables
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }

    /**
     * Get closest collision collision info.
     *
     * @param trajectory the trajectory
     * @return the collision info
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Rectangle r;
        Point temp;
        Point minDistancePoint = null;
        Collidable minDistanceObject = null;
        // checking collision with every object in list.
        List<Collidable> collidablesList = new ArrayList<>(this.collidables);
        for (Collidable c : collidablesList) {
            r = c.getCollisionRectangle();
            // getting closest intersection eith the rectangle
            temp = trajectory.closestIntersectionToStartOfLine(r);
            // if there is no intersection with this object move to next one
            if (temp == null) {
                continue;
            }
            //
            // if no min distance point was set or if this point is closer than the current min distance point then set
            // new min distance point and min distance object
            //
            if ((minDistancePoint == null)
                    || (temp.distance(trajectory.start()) < minDistancePoint.distance(trajectory.start()))) {
                minDistancePoint = temp;
                minDistanceObject = c;
            }
        }
        // if no min distance point and min distance object were set, return null. else, return them in collision info
        if (minDistancePoint == null) {
            return null;
        } else {
            return new CollisionInfo(minDistanceObject, minDistancePoint);
        }
    }
}