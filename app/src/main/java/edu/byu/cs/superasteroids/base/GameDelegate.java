package edu.byu.cs.superasteroids.base;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.ContactsContract;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.importer.IGameDataImporter;
import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.BackgroundObject;
import edu.byu.cs.superasteroids.model.GrowingAsteroid;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.MiniMap;
import edu.byu.cs.superasteroids.model.Octaroid;
import edu.byu.cs.superasteroids.model.PositionedObject;
import edu.byu.cs.superasteroids.model.Projectile;
import edu.byu.cs.superasteroids.model.RegularAsteroid;
import edu.byu.cs.superasteroids.model.StaticBackground;

/**
 * Created by Tyler on 7/19/2016.
 */
public class GameDelegate implements IGameDelegate {

    //This list will be filled with objects for loading and drawing
    private ArrayList<BackgroundObject> backgroundObjects = new ArrayList<>();
    //This list will be filled with asteroids
    private static ArrayList<Asteroid> asteroids = new ArrayList<>();
    //This is an array list of projectiles
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    //The number of the current level of gameplay
    private int levelCount = 1;

    //The gameLevel object
    GameLevel gameLevel;

    //The viewport
    Viewport viewport = new Viewport();

    //The miniMap
    MiniMap miniMap = new MiniMap();


    private void breakApartAsteroid(Asteroid asteroidIn)
    {
        if ((asteroidIn.getClass() == RegularAsteroid.class) && (asteroidIn.getScale() >= 1))
        {
            //This is just after an asteroid reaches low hit points, only if it is regular
            //with a scale of 1 (original size asteroid. Break it into three more small asteroids and
            //add them to the list
            for (int i = 0; i < 2; i++)
            {
                RegularAsteroid r = new RegularAsteroid();
                r.setImage(asteroidIn.getImage());
                r.setImageID(ContentManager.getInstance().loadImage(r.getImage()));
                //set a position for r
                r.setPosition(asteroidIn.getPosition());
                //Set a random movement direction for the asteroid
                Random rand = new Random();
                r.setDirection(Math.abs(rand.nextInt() % 360));
                //Set the bounding box for this asteroid
                r.setBoundingBox(new RectF(r.getPosition().x-r.getImageWidth()/2,
                        r.getPosition().y - r.getImageHeight()/2,
                        r.getPosition().x + r.getImageWidth()/2,
                        r.getPosition().y + r.getImageHeight()/2));
                r.setHitPoints(1);
                r.setScale((float) .5);
                r.setSpeed(rand.nextInt() % 300);
                asteroids.add(r);
            }
        }
        if (asteroidIn.getClass() == GrowingAsteroid.class && asteroidIn.getScale() >= 1)
        {
            for (int i = 0; i < 2; i++)
            {
                GrowingAsteroid r = new GrowingAsteroid();
                r.setImage(asteroidIn.getImage());
                r.setImageID(ContentManager.getInstance().loadImage(r.getImage()));
                //set a position for r
                r.setPosition(asteroidIn.getPosition());
                //Set a random movement direction for the asteroid
                Random rand = new Random();
                r.setDirection(Math.abs(rand.nextInt() % 360));
                //Set the bounding box for this asteroid
                r.setBoundingBox(new RectF(r.getPosition().x-r.getImageWidth()/2,
                        r.getPosition().y - r.getImageHeight()/2,
                        r.getPosition().x + r.getImageWidth()/2,
                        r.getPosition().y + r.getImageHeight()/2));
                r.setHitPoints(1);
                r.setScale(asteroidIn.getScale()/2);
                r.setSpeed(rand.nextInt() % 300);
                asteroids.add(r);
            }
        }
        if (asteroidIn.getClass() == Octaroid.class && asteroidIn.getScale() >= 1)
        {
            for (int i = 0; i < 8; i++)
            {
                Octaroid r = new Octaroid();
                r.setImage(asteroidIn.getImage());
                r.setImageID(ContentManager.getInstance().loadImage(r.getImage()));
                //set a position for r
                r.setPosition(asteroidIn.getPosition());
                //Set a random movement direction for the asteroid
                Random rand = new Random();
                r.setDirection(Math.abs(rand.nextInt() % 360));
                //Set the bounding box for this asteroid
                r.setBoundingBox(new RectF(r.getPosition().x-r.getImageWidth()/2,
                        r.getPosition().y - r.getImageHeight()/2,
                        r.getPosition().x + r.getImageWidth()/2,
                        r.getPosition().y + r.getImageHeight()/2));
                r.setHitPoints(1);
                r.setScale((float) .5);
                r.setSpeed(rand.nextInt() % 300);
                asteroids.add(r);
            }
        }

    }




    /**
     * Updates the game delegate. The game engine will call this function 60 times a second
     * once it enters the game loop.
     *
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
    @Override
    public void update(double elapsedTime) {
        //Update the ship
        Database.getInstance().getShip().update(elapsedTime);
        //update the viewport
        viewport.update();

        //Update the asteroids
        for (Asteroid a : asteroids)
        {
            a.update(elapsedTime);
        }
        //if the fire is pressed, create a new projectile and add it to the list
        if (InputManager.firePressed)
        {
            Projectile p = new Projectile(Database.getInstance().getShip().getCannon());
            p.setImageID(ContentManager.getInstance().loadImage(p.getAttackImage()));
            p.setImage(p.getAttackImage());
            try {
                p.setSoundID(ContentManager.getInstance().loadSound(p.getAttackSound()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ContentManager.getInstance().playSound(p.getSoundID(), 1, 1);
            projectiles.add(p);
        }
        for (Projectile p : projectiles) //update all the projectiles
        {
            p.update(elapsedTime);
        }
        //Check for collision
        for (int aIndex = asteroids.size()-1; aIndex >= 0; aIndex--)
        { //iterate backwards
            for (int pIndex = projectiles.size()-1; pIndex >= 0; pIndex--)
            {
                //if any asteroid hits any projectile at any given moment in time
                if (RectF.intersects(asteroids.get(aIndex).getBoundingBox(), projectiles.get(pIndex).getBoundingBox()))
                {
                    //remove the projectile from the draw list
                    projectiles.remove(pIndex);
                    //decrement the hit points on the current asteroid
                    asteroids.get(aIndex).decrementHitPoints();
                    if (asteroids.get(aIndex).getHitPoints() <= 0)
                    {
                        breakApartAsteroid(asteroids.get(aIndex));
                        asteroids.remove(aIndex);
                        break;
                    }


                }
            }
        }
        //Check for incrementing the level
        if (asteroids.size() <= 0)
        {
            levelCount++;
            unloadContent(ContentManager.getInstance());
        }
        //Check for if the lives of the ship are depleted
        if (Database.getInstance().getShip().getMainBody().getLives() <= 0)
        {
            //Reset to level one and unload content cause you lost, set lives back to 5
            levelCount = 1;
            unloadContent(ContentManager.getInstance());
            Database.getInstance().getShip().getMainBody().setLives(5);
        }
    }

    /**
     * Loads content such as image and sounds files and other data into the game. The GameActivty will
     * call this once right before entering the game engine enters the game loop. The ShipBuilding
     * activity calls this function in its onCreate() function.
     *
     * @param content An instance of the content manager. This should be used to load images and sound
     *                files.
     */
    @Override
    public void loadContent(ContentManager content) {
        //Each model object has variable image id, set that here
        gameLevel = new GameLevel(Database.getInstance().getModelContainer().getLevels().get(levelCount-1));
        Level currentLevel = gameLevel.getCurrentLevel();
        //Load all the objects and asteroids from the current level into the content
        ArrayList<BackgroundObject> currentLevelObjects = currentLevel.getLevelObjects();
        for (BackgroundObject b : currentLevelObjects)
        {
            b.setImageID(content.loadImage(b.getImage()));
            backgroundObjects.add(b);
        }

        //put the background image into the content
        StaticBackground s = new StaticBackground();
        s.setImage("images/space.bmp");
        Database.getInstance().getModelContainer().setBackgroundImage(s);
        StaticBackground b = Database.getInstance().getModelContainer().getBackgroundImage();
        b.setImageID(content.loadImage(b.getImage()));

        //Load the images for the ship
        Database.getInstance().getShip().getMainBody().setImageID(content.loadImage(Database.getInstance()
                                                                    .getShip().getMainBody().getImage()));
        Database.getInstance().getShip().getCannon().setImageID(content.loadImage(Database.getInstance()
                .getShip().getCannon().getImage()));
        Database.getInstance().getShip().getEngine().setImageID(content.loadImage(Database.getInstance()
                .getShip().getEngine().getImage()));
        Database.getInstance().getShip().getExtraPart().setImageID(content.loadImage(Database.getInstance()
                .getShip().getExtraPart().getImage()));

        //Set initial position for mainBody
        Database.getInstance().getShip().getMainBody().setPosition(
                new PointF((float)gameLevel.getCurrentLevelWidth()/2, (float)gameLevel.getCurrentLevelHeight()/2));
        //Set the bounding box of the main body
        RectF mainBodyBoundingBox = new RectF(Database.getInstance().getShip().getMainBody().getPosition().x-80,
                Database.getInstance().getShip().getMainBody().getPosition().y-80,
                Database.getInstance().getShip().getMainBody().getPosition().x+80,
                Database.getInstance().getShip().getMainBody().getPosition().y+80); //world coordinates
        Database.getInstance().getShip().getMainBody().setBoundingBox(mainBodyBoundingBox);

        //Scale all the ship parts down
        float scale = (float).25;
        Database.getInstance().getShip().getMainBody().setScale(scale);
        Database.getInstance().getShip().getCannon().setScale(scale);
        Database.getInstance().getShip().getEngine().setScale(scale);
        Database.getInstance().getShip().getExtraPart().setScale(scale);

        //Load the level asteroids in
        ArrayList<Asteroid> currentLevelAsteroids = currentLevel.getLevelAsteroids();
        for (Asteroid a : currentLevelAsteroids)
        {
            a.setImageID(content.loadImage(a.getImage()));
            //set a random position for a
            Random random = new Random();
            float randomAsteroidXcoord = Math.abs(random.nextInt() % gameLevel.getCurrentLevelWidth());
            float randomAsteroidYcoord = Math.abs(random.nextInt() % gameLevel.getCurrentLevelHeight());
            PointF randomPosition = new PointF(randomAsteroidXcoord, randomAsteroidYcoord);
            //Creating a large safety box around the ship
            //This is so the asteroid wont spawn on or right next to the ship
            RectF safetyBox = new RectF(Database.getInstance().getShip().getMainBody().getPosition().x-1000,
                    Database.getInstance().getShip().getMainBody().getPosition().y-1000,
                    Database.getInstance().getShip().getMainBody().getPosition().x+1000,
                    Database.getInstance().getShip().getMainBody().getPosition().y+1000); //world coordinates
            //If the asteroid's position is within this safety box, create a new position
            while(safetyBox.contains(randomPosition.x, randomPosition.y))
            {
                float randomAsteroidXcoordTry = Math.abs(random.nextInt() % gameLevel.getCurrentLevelWidth());
                float randomAsteroidYcoordTry = Math.abs(random.nextInt() % gameLevel.getCurrentLevelHeight());
                randomPosition = new PointF(randomAsteroidXcoordTry, randomAsteroidYcoordTry);
            }
            //Set a random movement direction for the asteroid
            Random r = new Random();
            a.setDirection(Math.abs(r.nextInt() % 360));
            a.setPosition(randomPosition);
            //Set the bounding box for this asteroid
            a.setBoundingBox(new RectF(a.getPosition().x-a.getImageWidth()/2,
                                        a.getPosition().y - a.getImageHeight()/2,
                                        a.getPosition().x + a.getImageWidth()/2,
                                        a.getPosition().y + a.getImageHeight()/2));
            a.setHitPoints(2);
            a.setScale(1);
            a.setSpeed(100);
            asteroids.add(a);
        }
    }

    /**
     * Unloads content from the game. The GameActivity will call this function after the game engine
     * exits the game loop. The ShipBuildingActivity will call this function after the "Start Game"
     * button has been pressed.
     *
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    @Override
    public void unloadContent(ContentManager content) {
        //Unload the asteroids
        for (Asteroid a : gameLevel.getLevelAsteroids())
        {
            content.unloadImage(a.getImageID());
        }
        //Unload the background objects
        for (BackgroundObject b : backgroundObjects)
        {
            content.unloadImage(b.getImageID());
        }
        //Unload the ship parts
        content.unloadImage(Database.getInstance().getShip().getMainBody().getImageID());
        content.unloadImage(Database.getInstance().getShip().getEngine().getImageID());
        content.unloadImage(Database.getInstance().getShip().getCannon().getImageID());
        content.unloadImage(Database.getInstance().getShip().getExtraPart().getImageID());
        //Unload the remaining projectiles
        for (Projectile p : projectiles)
        {
            content.unloadImage(p.getImageID());
        }
        //Clear these sets
        projectiles.clear();
        backgroundObjects.clear();
        asteroids.clear();

        //call load content
        loadContent(content);
    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
    @Override
    public void draw() {
        //Call the draw function of the viewport
        viewport.draw();

        //Draw the background
        float backgroundImageScale = (gameLevel.getCurrentLevelWidth()*gameLevel.getCurrentLevelHeight())/(2048*2048);
        float x = (float) gameLevel.getCurrentLevelWidth()/2;
        float y = (float) gameLevel.getCurrentLevelHeight()/2;
        PointF worldToView = Viewport.worldToViewCoordinates(new PointF(x, y));
        DrawingHelper.drawImage(Database.getInstance().getModelContainer().getBackgroundImage().getImageID(),
                worldToView.x,
                worldToView.y,
                0, backgroundImageScale, backgroundImageScale, 255);

        //draw all visible objects in the level
        for (BackgroundObject b : backgroundObjects)
        {
           b.drawImage();
        }
        for (Asteroid a : asteroids)
        {
            a.drawImage();
        }

        //Draw the current projectiles
        for (Projectile p : projectiles)
        {
            p.drawImage();
        }
        //draw the ship parts
        Database.getInstance().getShip().drawImage();

        //draw mini-map
        miniMap.drawImage();
    }

    //Getter and setter for asteroids list
    public static ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public static void setAsteroids(ArrayList<Asteroid> asteroids) {
        GameDelegate.asteroids = asteroids;
    }
}
