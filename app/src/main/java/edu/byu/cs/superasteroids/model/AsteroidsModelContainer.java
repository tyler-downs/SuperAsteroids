package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;

/**
 * Created by Tyler on 7/16/2016.
 */
public class AsteroidsModelContainer {

    //Containers for all the types of objects in the game
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<BackgroundObject> backgroundObjects = new ArrayList<>();
    private ArrayList<Cannon> cannons = new ArrayList<>();
    private ArrayList<Engine> engines = new ArrayList<>();
    private ArrayList<ExtraPart> extraParts = new ArrayList<>();
    private ArrayList<Level> levels = new ArrayList<>();
    private ArrayList<MainBody> mainBodies = new ArrayList<>();
    private ArrayList<PowerCore> powerCores = new ArrayList<>();

    //The background image
    private StaticBackground backgroundImage;

    //CLEAR ALL CONTAINERS
    public void clearAll(){
        asteroids.clear();
        backgroundObjects.clear();
        cannons.clear();
        engines.clear();
        extraParts.clear();
        levels.clear();
        mainBodies.clear();
        powerCores.clear();
        backgroundImage = null;

    }

    //GETTERS AND SETTERS
    public StaticBackground getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(StaticBackground backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(ArrayList<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public ArrayList<BackgroundObject> getBackgroundObjects() {
        return backgroundObjects;
    }

    public void setBackgroundObjects(ArrayList<BackgroundObject> backgroundObjects) {
        this.backgroundObjects = backgroundObjects;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(ArrayList<Cannon> cannons) {
        this.cannons = cannons;
    }

    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public void setEngines(ArrayList<Engine> engines) {
        this.engines = engines;
    }

    public ArrayList<ExtraPart> getExtraParts() {
        return extraParts;
    }

    public void setExtraParts(ArrayList<ExtraPart> extraParts) {
        this.extraParts = extraParts;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public ArrayList<MainBody> getMainBodies() {
        return mainBodies;
    }

    public void setMainBodies(ArrayList<MainBody> mainBodies) {
        this.mainBodies = mainBodies;
    }

    public ArrayList<PowerCore> getPowerCores() {
        return powerCores;
    }

    public void setPowerCores(ArrayList<PowerCore> powerCores) {
        this.powerCores = powerCores;
    }
}
