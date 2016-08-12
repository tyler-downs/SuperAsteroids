package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

/**
 * Created by Tyler on 7/12/2016.
 */
public abstract class PositionedObject {

    //The ID for this object
    private int imageID;
    //The position of the object, PointF has x and y coordinate
    private PointF position;
    //The path to the image file
    private String image;
    //The width of the image in pixels
    private int imageWidth;
    //The height of the image in pixels
    private int imageHeight;
    //The scale of the image, can contain decimals
    private float scale = 1;

    /**
     * Draws the image associated with this PositionedObject
     */
    public abstract void drawImage();

    //GETTERS AND SETTERS
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
