package edu.byu.cs.superasteroids.base;

import android.graphics.RectF;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.Level;

/**
 * Created by Tyler on 7/21/2016.
 */
public class GameLevel {
    //The current level of the game
    private Level currentLevel;
    //The bounding box of the game level
    private static RectF worldMap;
    //The width of the current level
    private static int currentLevelWidth;
    //The height of the current level
    private static int currentLevelHeight;
    //An array list of all the asteroids
    private ArrayList<Asteroid> levelAsteroids;
    /*
    Note: When an asteroid is destroyed, take it out of the levelAsteroids list
    When the levelAsteroids list is empty, go to the next level
     */

    //Constructor
    public GameLevel(Level level)
    {
        currentLevel = level;
        worldMap = new RectF(0, 0, currentLevel.getLevelWidth(), currentLevel.getLevelHeight());
        currentLevelWidth = currentLevel.getLevelWidth();
        currentLevelHeight = currentLevel.getLevelHeight();
        levelAsteroids = level.getLevelAsteroids();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public static RectF getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(RectF worldMap) {
        this.worldMap = worldMap;
    }

    public static int getCurrentLevelWidth() {
        return currentLevelWidth;
    }

    public void setCurrentLevelWidth(int currentLevelWidth) {
        this.currentLevelWidth = currentLevelWidth;
    }

    public static int getCurrentLevelHeight() {
        return currentLevelHeight;
    }

    public void setCurrentLevelHeight(int currentLevelHeight) {
        this.currentLevelHeight = currentLevelHeight;
    }

    public ArrayList<Asteroid> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelAsteroids(ArrayList<Asteroid> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }
}
