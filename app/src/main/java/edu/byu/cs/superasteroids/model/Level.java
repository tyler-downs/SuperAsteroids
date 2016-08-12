package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;

/**
 * Created by Tyler on 7/12/2016.
 */
public class Level {

    //The level number
    private int number;
    //The title of the level
    private String title;
    //The hint for the level
    private String hint;
    //The width of the level
    private int levelWidth;
    //The height of the level
    private int levelHeight;
    //The path to the music file
    private String music;
    //A list of the asteroids in the level
    private ArrayList<Asteroid> levelAsteroids;
    //A list of the background objects in the level
    private ArrayList<BackgroundObject> levelObjects;

    //GETTERS AND SETTERS
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public ArrayList<Asteroid> getLevelAsteroids() {
        return levelAsteroids;
    }

    public void setLevelAsteroids(ArrayList<Asteroid> levelAsteroids) {
        this.levelAsteroids = levelAsteroids;
    }

    public ArrayList<BackgroundObject> getLevelObjects() {
        return levelObjects;
    }

    public void setLevelObjects(ArrayList<BackgroundObject> levelObjects) {
        this.levelObjects = levelObjects;
    }
}
