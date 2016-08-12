package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.Random;

import edu.byu.cs.superasteroids.base.GameLevel;
import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/12/2016.
 */
public class RegularAsteroid extends Asteroid {

    /**
     * Updates the object, handling movement and collision detection
     */
    @Override
    public void update(double elapsedTime) {
        //Set it moving in the random direction
        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(getPosition(),
                getBoundingBox(), getSpeed(), GraphicsUtils.degreesToRadians(getDirection()), elapsedTime);
        setPosition(result.getNewObjPosition());
        setBoundingBox(result.getNewObjBounds());

        //Set up ricochet off wall
        GraphicsUtils.RicochetObjectResult ricochetObjectResult = GraphicsUtils.ricochetObject(
                getPosition(),
                getBoundingBox(),
                GraphicsUtils.degreesToRadians(getDirection()),
                GameLevel.getCurrentLevelWidth(),
                GameLevel.getCurrentLevelHeight());
        setPosition(ricochetObjectResult.getNewObjPosition());
        setBoundingBox(ricochetObjectResult.getNewObjBounds());
        setDirection((float)GraphicsUtils.radiansToDegrees(ricochetObjectResult.getNewAngleRadians()));

    }

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        //CONVERT WORLD COORDS TO VIEW COORDS
        PointF viewCoords = Viewport.worldToViewCoordinates(getPosition());
        DrawingHelper.drawImage(getImageID(), viewCoords.x, viewCoords.y, 0, getScale(), getScale(), 255);
    }

}
