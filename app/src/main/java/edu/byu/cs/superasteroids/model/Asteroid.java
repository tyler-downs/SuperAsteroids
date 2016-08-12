package edu.byu.cs.superasteroids.model;

import android.graphics.RectF;

/**
 * Created by Tyler on 7/12/2016.
 */
public abstract class Asteroid extends MovingObject {

    //The name of the asteroid
    private String name;
    //The type of asteroid
    private String type;
    //The bounding box for the asteroid
    private RectF boundingBox;
    //Hit points
    private int hitPoints;

    //Decrements the hit points by 1
    public void decrementHitPoints()
    {
        hitPoints--;
    }

    //GETTERS AND SETTERS
    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RectF getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(RectF boundingBox) {
        this.boundingBox = boundingBox;
    }

}
