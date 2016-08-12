package edu.byu.cs.superasteroids.base;

import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/20/2016.
 */
public class Viewport {

    //The width of the viewport
    private static int width;
    //The height of the viewport
    private static int height;
    //The viewport coordinates in a rectF, just for convenience
    private RectF viewportRect = new RectF(0, 0, width, height);


    //The location in world coordinates of the corner of the viewport, default value 1000, 1000
    private static PointF locationInWorld = new PointF(0, 0);

    /**
     * Function that converts world coordinates to viewport coordinates
     * @param worldCoordinate The coordinate to be converted to view
     * @return the view coordinate
     */
    public static PointF worldToViewCoordinates(PointF worldCoordinate)
    {
        return GraphicsUtils.subtract(worldCoordinate, locationInWorld);
    }

    /**
     * Converts view coordinates to world coordinates
     * @param viewCoordinate
     * @return the world position of that view coordinate
     */
    public static PointF viewToWorldCoordinates(PointF viewCoordinate)
    {
        return GraphicsUtils.add(viewCoordinate, locationInWorld);
    }

    /**
     * Converts a RectF from world to view coordinates
     * @param worldCoordBox
     * @return the box in view coordinates
     */
    public static RectF worldToViewCoordinates(RectF worldCoordBox)
    {
        //convert the topleft and bottomRight points to view coords
        PointF boundingBoxTopLeft = new PointF(worldCoordBox.left, worldCoordBox.top);
        PointF boundingBoxBottomRight = new PointF(worldCoordBox.right, worldCoordBox.bottom);
        PointF boundingBoxTopLeftViewCoords = worldToViewCoordinates(boundingBoxTopLeft);
        PointF boundingBoxBottomRightViewCoords = worldToViewCoordinates(boundingBoxBottomRight);
        return new RectF(boundingBoxTopLeftViewCoords.x, boundingBoxTopLeftViewCoords.y,
                boundingBoxBottomRightViewCoords.x, boundingBoxBottomRightViewCoords.y);
    }


    /**
     * Static function returning the center of the viewport
     * @return the viewport center in viewport coordinates
     */
    public static PointF getCenterOfViewport()
    {
        return new PointF((float)width/2, (float)height/2);
    }

    //GETTERS AND SETTERS
    public static int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static PointF getLocationInWorld() {
        return locationInWorld;
    }

    public static void setLocationInWorld(PointF locationInWorld) {
        //change this to subtract half the view on each side
        Viewport.locationInWorld = locationInWorld;
    }


    public void draw()
    {
        width = DrawingHelper.getGameViewWidth();
        height = DrawingHelper.getGameViewHeight();
    }

    public void update()
    {
        //Set the viewport to surround the ship
        PointF mainBodyPosition = Database.getInstance().getShip().getMainBody().getPosition();
        setLocationInWorld(new PointF(mainBodyPosition.x - Viewport.getWidth()/2, mainBodyPosition.y-Viewport.getHeight()/2));
        //make sure viewport cannot leave the bounds of the world map
        if (getLocationInWorld().x < 0)
        {
            setLocationInWorld(new PointF(0, getLocationInWorld().y));
        }
        if (getLocationInWorld().y < 0)
        {
            setLocationInWorld(new PointF(getLocationInWorld().x, 0));
        }
        if (getLocationInWorld().x > GameLevel.getCurrentLevelWidth() - width)
        {
            setLocationInWorld(new PointF((float)(GameLevel.getCurrentLevelWidth() - width),getLocationInWorld().y));
        }
        if (getLocationInWorld().y > GameLevel.getCurrentLevelHeight() - height)
        {
            setLocationInWorld(new PointF(getLocationInWorld().x,(float)(GameLevel.getCurrentLevelHeight() - height)));
        }

    }
}
