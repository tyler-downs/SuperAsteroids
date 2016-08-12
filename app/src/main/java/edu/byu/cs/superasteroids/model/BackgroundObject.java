package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/12/2016.
 */
public class BackgroundObject extends PositionedObject {

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        PointF viewCoords = Viewport.worldToViewCoordinates(getPosition());
        DrawingHelper.drawImage(getImageID(), viewCoords.x, viewCoords.y, 0, getScale(), getScale(), 255);
    }
}
