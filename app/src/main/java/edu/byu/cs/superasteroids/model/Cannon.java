package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.sax.StartElementListener;
import android.view.View;

import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/12/2016.
 */
public class Cannon extends MovingObject{

    //A reference to the mainBody
    private MainBody mainBodyRef;
    //The point of the cannon image that attaches to the main body image
    private PointF attachPoint;
    //The point of the cannon image from which the projectile is emitted
    private PointF emitPoint;
    //The path to the cannon's projectile image
    private String attackImage;
    //The pixel width of the cannon's projectile image
    private int attackImageWidth;
    //The pixel height of the cannon's projectile image
    private int attackImageHeight;
    //The path to the sound file for the cannon when it fires
    private String attackSound;
    //The amount of damage the projectile does
    private int damage;

    /**
     * Empty constructor
     */
    public Cannon()
    {

    }

    /**
     * Constructor that fills in all private data members
     * @param attachPoint
     * @param emitPoint
     * @param attackImage
     * @param attackImageWidth
     * @param attackImageHeight
     * @param attackSound
     * @param damage
     */
    public Cannon(PointF attachPoint, PointF emitPoint, String attackImage, int attackImageWidth,
                  int attackImageHeight, String attackSound, int damage)
    {
        this.attachPoint = attachPoint;
        this.emitPoint = emitPoint;
        this.attackImage = attackImage;
        this.attackImageWidth = attackImageWidth;
        this.attackImageHeight = attackImageHeight;
        this.attackSound = attackSound;
        this.damage = damage;
    }



    //GETTERS AND SETTERS
    public MainBody getMainBodyRef() {
        return mainBodyRef;
    }

    public void setMainBodyRef(MainBody mainBodyRef) {
        this.mainBodyRef = mainBodyRef;
    }

    public PointF getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(PointF attachPoint) {
        this.attachPoint = attachPoint;
    }

    public PointF getEmitPoint() {
        return emitPoint;
    }

    public void setEmitPoint(PointF emitPoint) {
        this.emitPoint = emitPoint;
    }

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        setDirection(mainBodyRef.getDirection());
        PointF cannonAttachPoint = GraphicsUtils.scale(attachPoint, getScale());
        float cannonCenterXcoord = getImageWidth()/2;
        float cannonCenterYcoord = getImageHeight()/2;
        PointF cannonCenter = GraphicsUtils.scale(new PointF(cannonCenterXcoord, cannonCenterYcoord), getScale());
        PointF mainBodyCannonAttachScaled = GraphicsUtils.scale(mainBodyRef.getCannonAttach(), getScale());
        PointF partOffset = GraphicsUtils.add(GraphicsUtils.subtract(mainBodyCannonAttachScaled, mainBodyRef.getMainBodyCenter()),
                            GraphicsUtils.subtract(cannonCenter, cannonAttachPoint));
        PointF rotatedPartOffset = GraphicsUtils.rotate(partOffset, GraphicsUtils.degreesToRadians(getDirection()));
        PointF cannonLocation = GraphicsUtils.add(Viewport.worldToViewCoordinates(mainBodyRef.getPosition()), rotatedPartOffset);
        setPosition(Viewport.viewToWorldCoordinates(cannonLocation));
        DrawingHelper.drawImage(getImageID(), cannonLocation.x, cannonLocation.y, getDirection(), getScale(), getScale(), 255);

    }


    //GETTERS AND SETTERS
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Updates the object, handling movement and collision detection
     */
    @Override
    public void update(double elapsedTime) {
        //All ship part updates handled in MainBody
    }
}
