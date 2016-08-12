package edu.byu.cs.superasteroids.model;

/**
 * Created by Tyler on 7/12/2016.
 */
public class StaticBackground {

    //The id for this object
    private int imageID;
    //The path to the image file
    private String image;

    //GETTERS AND SETTERS
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }


    /**
     * Draws the background image
     */
    public void drawImage(){
        //This gets drawn at a higher stage in the draw tree
    }
}
