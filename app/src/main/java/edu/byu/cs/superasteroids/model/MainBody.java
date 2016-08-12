package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import edu.byu.cs.superasteroids.base.GameDelegate;
import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by Tyler on 7/12/2016.
 */

public class MainBody extends MovingObject{

    //The attach point for the cannon
    private PointF cannonAttach;
    //The attach point for the engine
    private PointF engineAttach;
    //The attach point for the extraPart
    private PointF extraAttach;
    //The bounding box for the ship
    private RectF boundingBox;
    //A boolean to indicate whether the ship is in safe mode
    public boolean inSafeMode = false;
    //A counter for the safe mode
    private double safeModeTimer = 5;
    //The number of lives the ship has left
    private static int lives = 5;

    public MainBody()
    {

    }

    //Returns the center of the main body image
    public PointF getMainBodyCenter() {
        float mainBodyCenterXcoord = getImageWidth()/2;
        float mainBodyCenterYcoord = getImageHeight()/2;
        return GraphicsUtils.scale(new PointF(mainBodyCenterXcoord, mainBodyCenterYcoord), getScale());
    }

    //GETTERS AND SETTERS
    public RectF getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(RectF boundingBox) {
        this.boundingBox = boundingBox;
    }

    public PointF getCannonAttach() {
        return cannonAttach;
    }

    public void setCannonAttach(PointF cannonAttach) {
        this.cannonAttach = cannonAttach;
    }

    public PointF getEngineAttach() {
        return engineAttach;
    }

    public void setEngineAttach(PointF engineAttach) {
        this.engineAttach = engineAttach;
    }

    public PointF getExtraAttach() {
        return extraAttach;
    }

    public void setExtraAttach(PointF extraAttach) {
        this.extraAttach = extraAttach;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int lives) {
        MainBody.lives = lives;
    }

    public boolean isInSafeMode() {
        return inSafeMode;
    }

    public void setInSafeMode(boolean inSafeMode) {
        this.inSafeMode = inSafeMode;
    }

    /**
     * Updates the object, handling movement and collision detection
     */
    @Override
    public void update(double elapsedTime) {
        //THESE THINGS MUST BE IN WORLD COORDINATES
        PointF movePointIn = InputManager.movePoint;
        if (movePointIn != null)
        {
            //Change the move point to world coordinates
            movePointIn = Viewport.viewToWorldCoordinates(movePointIn);
            //Set the direction of the body with this equation
            setDirection((float)GraphicsUtils.radiansToDegrees(Math.atan2(movePointIn.y-getPosition().y,
                                                                    movePointIn.x-getPosition().x)) + 90);
            //Move the body
            GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(getPosition(), boundingBox, 500,
                    GraphicsUtils.degreesToRadians(getDirection()-90), elapsedTime);
            setPosition(result.getNewObjPosition());
            setBoundingBox(result.getNewObjBounds());
        }

        //Collision with asteroids
        for (Asteroid a : GameDelegate.getAsteroids())
        {
            if (RectF.intersects(a.getBoundingBox(), boundingBox) && !inSafeMode)
            {
                //turn on safe mode
                inSafeMode = true;
                //decrement lives
                //lives--; //This can be uncommented if we want losing to be a possibility
            }
        }
        //Safe mode timer is ticking
        if (inSafeMode && safeModeTimer > 0)
        {
            safeModeTimer -= elapsedTime;
            if (safeModeTimer <= 0)
            {
                safeModeTimer = 5;
                inSafeMode = false;
            }
        }
    }

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        //CONVERT WORLD COORDS TO VIEW COORDS
        PointF viewCoords = Viewport.worldToViewCoordinates(getPosition());
        //Draw the image
        DrawingHelper.drawImage(getImageID(), viewCoords.x, viewCoords.y, getDirection(), getScale(), getScale(), 255);
        if (inSafeMode)
        {
            //Draw the red circle around the ship
            DrawingHelper.drawFilledCircle(Viewport.worldToViewCoordinates(getPosition()),
                                            getImageHeight()*getScale(), 0xFF0000, 50);
        }

    }
}
