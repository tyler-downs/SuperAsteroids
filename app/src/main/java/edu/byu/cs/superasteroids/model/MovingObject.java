package edu.byu.cs.superasteroids.model;

import android.graphics.RectF;

/**
 * Created by Tyler on 7/12/2016.
 */
public abstract class MovingObject extends PositionedObject{

    //The direction the object is facing, angle in degrees from 0
    private float direction;
    //The speed at which the object is moving
    private int speed;

    /**
     * Updates the object, handling movement and collision detection
     */
    public abstract void update(double elapsedTime);

    //GETTERS AND SETTERS
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

}
