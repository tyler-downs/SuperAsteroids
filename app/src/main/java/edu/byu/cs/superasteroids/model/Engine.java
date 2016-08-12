package edu.byu.cs.superasteroids.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;

import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/12/2016.
 */
public class Engine extends MovingObject{

    //A reference to the mainBody
    private MainBody mainBodyRef;
    //The base speed of the engine
    private int baseSpeed;
    //The base turn rate of the engine
    private int baseTurnRate;
    //The point of the engine image that attaches to the main body image
    private PointF attachPoint;

    /**
     * Constructor for the Engine class
     * @param baseSpeed The base speed of the ship
     * @param baseTurnRate The turn rate
     * @param attachPoint The point it attaches to the main body
     */
    public Engine(int baseSpeed, int baseTurnRate, PointF attachPoint)
    {
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
        this.attachPoint = attachPoint;
    }

    /**
     * Constructor with no parameters
     */
    public Engine()
    {

    }


    //GETTERS AND SETTERS
    public MainBody getMainBodyRef() {
        return mainBodyRef;
    }

    public void setMainBodyRef(MainBody mainBodyRef) {
        this.mainBodyRef = mainBodyRef;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }

    public PointF getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(PointF attachPoint) {
        this.attachPoint = attachPoint;
    }

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        setDirection(mainBodyRef.getDirection());
        PointF engineAttachPoint = GraphicsUtils.scale(attachPoint, getScale());
        float engineCenterXcoord = getImageWidth()/2;
        float engineCenterYcoord = getImageHeight()/2;
        PointF engineCenter = GraphicsUtils.scale(new PointF(engineCenterXcoord, engineCenterYcoord), getScale());
        //Calculate the part offset with these scaled values
        PointF mainBodyEngineAttachScaled = GraphicsUtils.scale(mainBodyRef.getEngineAttach(), getScale());
        PointF partOffset =  GraphicsUtils.add(GraphicsUtils.subtract(mainBodyEngineAttachScaled, mainBodyRef.getMainBodyCenter()),
                GraphicsUtils.subtract(engineCenter, engineAttachPoint));
        PointF rotatedPartOffset = GraphicsUtils.rotate(partOffset, GraphicsUtils.degreesToRadians(getDirection()));
        //Calculate location of engine image
        PointF engineLocation = GraphicsUtils.add(Viewport.worldToViewCoordinates(mainBodyRef.getPosition()), rotatedPartOffset);
        //Draw the image
        DrawingHelper.drawImage(getImageID(), engineLocation.x, engineLocation.y, getDirection(), getScale(), getScale(), 255);
    }


    /**
     * Updates the object, handling movement and collision detection
     */
    @Override
    public void update(double elapsedTime) {
        //All ship part updates handled in MainBody
    }
}
