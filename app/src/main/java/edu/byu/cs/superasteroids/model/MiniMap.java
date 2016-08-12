package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;

import edu.byu.cs.superasteroids.base.GameDelegate;
import edu.byu.cs.superasteroids.base.GameLevel;
import edu.byu.cs.superasteroids.base.Viewport;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by Tyler on 7/12/2016.
 */
public class MiniMap extends PositionedObject{

    //The mini-map is 1/SCALE_DIVISOR the size of the world map
    private final int SCALE_DIVISOR = 15;
    //The alpha value for the mini-map
    private final int ALPHA = 200;
    //The color red
    private final int RED = 0xFF0000;
    //The color green
    private final int GREEN = 0x00FF00;
    //The color blue
    private final int BLUE = 0x0000FF;
    //The color black
    private final int BLACK = 0x000000;

    public void drawImage()
    {
        //Draw a box in the corner of the screen
        int miniMapWidth = GameLevel.getCurrentLevelWidth()/SCALE_DIVISOR;
        int miniMapHeight = GameLevel.getCurrentLevelHeight()/SCALE_DIVISOR;
        Rect outerRectangle = new Rect(0, 0, miniMapWidth, miniMapHeight);
        Rect innerRectangle = new Rect(1, 1, miniMapWidth-1, miniMapHeight);
        DrawingHelper.drawRectangle(outerRectangle, RED, ALPHA);
        DrawingHelper.drawFilledRectangle(innerRectangle, BLACK, ALPHA);
        //1. loop through the asteroids list
        //2. scale down the position of the asteroid
        //3. draw a point at the scaled-down position of the asteroid
        for (Asteroid a : GameDelegate.getAsteroids())
        {
            PointF scaledDownPosition = GraphicsUtils.scale(a.getPosition(), (float)1/SCALE_DIVISOR);
            DrawingHelper.drawPoint(scaledDownPosition, 2, GREEN, ALPHA);
        }
        //Draw the ship on the mini-map
        PointF scaledDownPositionOfShip = GraphicsUtils.scale(
                Database.getInstance().getShip().getMainBody().getPosition(), (float)1/SCALE_DIVISOR);
        DrawingHelper.drawPoint(scaledDownPositionOfShip, 4, BLUE, ALPHA);
    }

}
