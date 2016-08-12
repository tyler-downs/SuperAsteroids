package edu.byu.cs.superasteroids.model;

/**
 * Created by Tyler on 7/12/2016.
 */
public class PowerCore {

    //The path to the image
    private String image;
    //The cannon boost amount
    private int cannonBoost;
    //The engine boost amount
    private int engineBoost;

    /**
     * Empty constructor
     */
    public PowerCore()
    {

    }

    /**
     * Constructor that fills in all data members
     * @param image
     * @param cannonBoost
     * @param engineBoost
     */
    public PowerCore(String image, int cannonBoost, int engineBoost)
    {
        this.image = image;
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
    }

    /**
     * Draws the image associated with this Object
     */
    public void drawImage() {
        //Note: This image is never actually drawn in the game
    }

    //GETTERS AND SETTERS
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCannonBoost() {
        return cannonBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }
}
