package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/12/2016.
 */
public class ExtraPart extends MovingObject{

    //A reference to the mainBody
    private MainBody mainBodyRef;
    //The point of the extraPart image that attaches to the main body image
    private PointF attachPoint;

    /**
     * Empty constructor with no parameters
     */
    public ExtraPart()
    {
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

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        setDirection(mainBodyRef.getDirection());
        PointF extraAttachPoint = GraphicsUtils.scale(attachPoint, getScale());
        float engineCenterXcoord = getImageWidth()/2;
        float engineCenterYcoord = getImageHeight()/2;
        PointF extraPartCenter = GraphicsUtils.scale(new PointF(engineCenterXcoord, engineCenterYcoord), getScale());
        PointF mainBodyExtraAttachScaled = GraphicsUtils.scale(mainBodyRef.getExtraAttach(), getScale());
        PointF partOffset =  GraphicsUtils.add(GraphicsUtils.subtract(mainBodyExtraAttachScaled, mainBodyRef.getMainBodyCenter()),
                GraphicsUtils.subtract(extraPartCenter, extraAttachPoint));
        PointF rotatedPartOffset = GraphicsUtils.rotate(partOffset, GraphicsUtils.degreesToRadians(getDirection()));
        PointF extraPartLocation = GraphicsUtils.add(Viewport.worldToViewCoordinates(mainBodyRef.getPosition()), rotatedPartOffset);
        DrawingHelper.drawImage(getImageID(), extraPartLocation.x, extraPartLocation.y, getDirection(), getScale(), getScale(), 255);
    }


    /**
     * Updates the object, handling movement and collision detection
     */
    @Override
    public void update(double elapsedTime) {
        //All ship part updates handled in MainBody
    }
}
