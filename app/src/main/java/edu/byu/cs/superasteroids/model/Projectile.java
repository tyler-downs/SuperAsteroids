package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.base.GameLevel;
import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/12/2016.
 */
public class Projectile extends MovingObject {

    //A reference to the cannon of the ship
    private Cannon cannonRef;
    //the attackImage of the projectile
    private String attackImage;
    //the attackImage width of the projectile
    private int attackImageWidth;
    //the attack image height of the projectile
    private int attackImageHeight;
    //the sound of the projectile
    private String attackSound;
    //The bounding box for the projectile image
    private RectF boundingBox;
    //SoundID
    private int soundID;



    //CONSTRUCTOR
    public Projectile(Cannon cannon)
    {
        cannonRef = cannon;
        attackImage = cannonRef.getAttackImage();
        attackImageWidth = cannonRef.getAttackImageWidth();
        attackImageHeight = cannonRef.getAttackImageHeight();
        attackSound = cannonRef.getAttackSound();
        setPosition(cannonRef.getPosition());
        setDirection(cannonRef.getMainBodyRef().getDirection());
        //This bounding box is just a small box in the center of the projectile of side length attackimageWidth
        boundingBox = new RectF(getPosition().x - getAttackImageWidth()/2,
                                getPosition().y - getAttackImageWidth()/2,
                                getPosition().x + getAttackImageWidth()/2,
                                getPosition().y + getAttackImageWidth()/2);
    }

    /**
     * Updates the object, handling movement and collision detection
     */
    @Override
    public void update(double elapsedTime) {
        //Move the projectile
        GraphicsUtils.MoveObjectResult moveResult = GraphicsUtils.moveObject(getPosition(), getBoundingBox(),
                                                    1000, GraphicsUtils.degreesToRadians(getDirection()-90), elapsedTime);
        setPosition(moveResult.getNewObjPosition());
        setBoundingBox(moveResult.getNewObjBounds());
    }

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        //CONVERT WORLD COORDS TO VIEW COORDS
        PointF viewCoords = Viewport.worldToViewCoordinates(getPosition());
        DrawingHelper.drawImage(getImageID(), viewCoords.x, viewCoords.y, getDirection(), (float).25, (float).25, 255);
    }

    //GETTERS AND SETTERS
    public int getSoundID() {
        return soundID;
    }

    public void setSoundID(int soundID) {
        this.soundID = soundID;
    }

    public RectF getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(RectF boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Cannon getCannonRef() {
        return cannonRef;
    }

    public void setCannonRef(Cannon cannonRef) {
        this.cannonRef = cannonRef;
    }


    public String getAttackImage() {
        return attackImage;
    }

    public void setAttackImage(String attackImage) {
        this.attackImage = attackImage;
    }

    public int getAttackImageWidth() {
        return attackImageWidth;
    }

    public void setAttackImageWidth(int attackImageWidth) {
        this.attackImageWidth = attackImageWidth;
    }

    public int getAttackImageHeight() {
        return attackImageHeight;
    }

    public void setAttackImageHeight(int attackImageHeight) {
        this.attackImageHeight = attackImageHeight;
    }

    public String getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }
}
