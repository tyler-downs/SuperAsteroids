package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.PointF;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.AsteroidsModelContainer;
import edu.byu.cs.superasteroids.model.BackgroundObject;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.GrowingAsteroid;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.Octaroid;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.RegularAsteroid;

/**
 * Created by Tyler on 7/12/2016.
 */
public class AsteroidsDAO {

    //The database we will be working with
    private SQLiteDatabase db;

    //asteroid ID count, to be used later in selecting asteroid types
    private int asteroidIDCount = 1;

    //Asteroid IDs
    private final int REG_ASTEROID_ID = 1;
    private final int GRO_ASTEROID_ID = 2;
    private final int OCT_ASTEROID_ID = 3;


    /**
     * Private constructor that loads the current working database into db
     * @param db the database
     */
    public AsteroidsDAO(SQLiteDatabase db)
    {
        this.db = db;
    }


    /**
     * Adds a background object's data to the database
     * @param backgroundObject the background object to be added
     */
    public boolean addBackgroundObject(BackgroundObject backgroundObject)
    {
        ContentValues values = new ContentValues();
        values.put("OBJECT", backgroundObject.getImage());

        //return value of insert function is the index of the row that item was stored at
        //-1 if failed to insert
        long rowNumber = db.insert("objects", null, values);

        if (rowNumber >= 0)
        {
            return true;
        }
        else
            return false;
    }

    /**
     * Reads all background object data from the database
     * @return An array list of all the background objects found in the database
     */
    public ArrayList<BackgroundObject> getBackgroundObjects()
    {
        //create a list
        ArrayList<BackgroundObject> backgroundObjects = new ArrayList<>();

        Cursor cursor = db.query("objects", new String[]{"OBJECT_ID", "OBJECT"}, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            BackgroundObject b = new BackgroundObject();
            b.setImage(cursor.getString(1));
            backgroundObjects.add(b);
            cursor.moveToNext();
        }
        cursor.close();
        return backgroundObjects;
    }

    /**
     * When deconstructing a level object to add it to the database, takes the list of background objects
     * and fills the levelobjects table accordingly
     * @param objects the array list of level objects to be added to the levelObjects table
     * @param levelNumber the number of the current level
     * @return true if successful, false if not
     */
    public boolean addLevelObjects(ArrayList<BackgroundObject> objects, int levelNumber)
    {
        //iterate through all the background objects in the list
        for (BackgroundObject temp : objects)
        {
            ContentValues values = new ContentValues();
            //level objects needs position, objectID, scale, and level
            //take the position pointF and convert it to a string
            values.put("POSITION", PointFStringConverter.pointfToString(temp.getPosition()));
            //now we need the object_id, i.e. the index of this object in the table of objects
            //we need to query that table to find the objectid that corresponds to this object
            Cursor cursor = db.query("objects", new String[]{"OBJECT_ID", "OBJECT"}, null, null, null, null, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                if (temp.getImage().equals(cursor.getString(1)))
                {
                    //take the object id at this point and add it to values
                    values.put("OBJECT_ID", cursor.getInt(0));
                    break;//break from the while loop
                }
                cursor.moveToNext();
            }
            cursor.close();
            //now just fill scale and level
            values.put("SCALE", temp.getScale());
            values.put("LEVEL", levelNumber);
            //now insert
            long rowNumber = db.insert("levelObjects", null, values);
        }
        return true;
    }

    public boolean addLevelAsteroids(ArrayList<Asteroid> asteroids, int levelNumber)
    {
        //include three counts of regular, growing, and octaroid
        //increment them whenever the asteroid in the list is that one
        int regCount = 0;
        int groCount = 0;
        int octCount = 0;

        //iterate through all the asteroids in the list
        for (Asteroid temp : asteroids)
        {
            //levelasteroids needs number (of asteroids), asteroid id, and level
            if (temp.getClass() == RegularAsteroid.class)
            {
                regCount++;
            }
            else if (temp.getClass() == GrowingAsteroid.class)
            {
                groCount++;
            }
            else //if it is an octaroid
            {
                octCount++;
            }
        }
        //now fill in the table for the asteroids of that level
        if (regCount > 0)
        {
            //add the number of regCounts to the database
            ContentValues values = new ContentValues();
            values.put("NUMBER", regCount);
            values.put("ASTEROID_ID", REG_ASTEROID_ID);
            values.put("LEVEL", levelNumber);
            db.insert("levelAsteroids", null, values);
        }
        if (groCount > 0)
        {
            ContentValues values = new ContentValues();
            values.put("NUMBER", groCount);
            values.put("ASTEROID_ID", GRO_ASTEROID_ID);
            values.put("LEVEL", levelNumber);
            db.insert("levelAsteroids", null, values);
        }
        if(octCount > 0)
        {
            ContentValues values = new ContentValues();
            values.put("NUMBER", octCount);
            values.put("ASTEROID_ID", OCT_ASTEROID_ID);
            values.put("LEVEL", levelNumber);
            db.insert("levelAsteroids", null, values);
        }
        return true;
    }

    /**
     * Adds a level object's data to the database
     * A level knows
     * @param level the level object to be added
     */
    public boolean addLevel(Level level)
    {
        ContentValues values = new ContentValues();
        values.put("NUMBER", level.getNumber());
        values.put("TITLE", level.getTitle());
        values.put("HINT", level.getHint());
        values.put("WIDTH", level.getLevelWidth());
        values.put("HEIGHT", level.getLevelHeight());
        values.put("MUSIC", level.getMusic());


        long rowNumber = db.insert("levels", null, values);

        //insert the level asteroids and level objects in their tables
        addLevelObjects(level.getLevelObjects(), level.getNumber());
        addLevelAsteroids(level.getLevelAsteroids(), level.getNumber());

        if (rowNumber >= 0)
        {
            return true;
        }
        else return false;
    }



    public ArrayList<BackgroundObject> getLevelObjects(int levelNumber)
    {
        ArrayList<BackgroundObject> objects = new ArrayList<>();
        Cursor cursor = db.query("levelObjects", new String[]{"POSITION", "OBJECT_ID", "SCALE", "LEVEL"},
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            //assign lvl to the value at "LEVEL" column of the database
            int lvl = cursor.getInt(3);
            //if lvl equals the level number that was passed in
            if (lvl == levelNumber)
            {
                //make the background object and save it into objects
                BackgroundObject b = new BackgroundObject();
                //get the image path from the other table of just IDs and images
                //use the objectID in cursor, if it matches the objectid in objectsCursor then return the object string
                Cursor objectsCursor = db.query("objects", new String[]{"OBJECT_ID", "OBJECT"}, null,
                                        null, null, null, null);
                objectsCursor.moveToFirst();
                while(!objectsCursor.isAfterLast())
                {
                    //this goes down the rows and checks if objectID in this is equal to objectID in the other
                    if (objectsCursor.getInt(0) == cursor.getInt(1))
                    {
                        //add the string at objectscursor(1) to b as the image
                        b.setImage(objectsCursor.getString(1));
                        break;
                    }
                    objectsCursor.moveToNext();
                }
                objectsCursor.close();
                //now add the position and everything from the level objects table into the object b
                //convert the position string to a PointF and put it in b
                PointF position = PointFStringConverter.stringToPointF(cursor.getString(0));
                b.setPosition(position);
                //set the scale
                b.setScale(cursor.getFloat(2));
                //put b in the list
                objects.add(b);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return objects;
    }

    public ArrayList<Asteroid> getLevelAsteroids(int levelNumber)
    {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Cursor cursor = db.query("levelAsteroids", new String[]{"NUMBER", "ASTEROID_ID", "LEVEL"},
                        null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            //assign lvl to the value in the "LEVEL" column of this table at this point
            int lvl = cursor.getInt(2);
            if (lvl == levelNumber)
            {
                //the level passed in equals the level number at this row of the table
                //we will now see what the asteroid type we are dealing with is, how many there are,
                //and create that many of that type of asteroid
                int asteroidId = cursor.getInt(1);
                int numberOfAsteroids = cursor.getInt(0);
                for (int i = 0; i < numberOfAsteroids; i++)
                {
                    if (asteroidId == REG_ASTEROID_ID)
                    {
                        RegularAsteroid r = new RegularAsteroid();
                        //access the asteroid types table to assign values to this asteroid
                        Cursor astTypesCursor = db.query("asteroidTypes", new String[]{"ASTEROID_ID", "NAME",
                                        "IMAGE", "IMAGE_WIDTH", "IMAGE_HEIGHT", "TYPE"}, null,
                                null, null, null, null);
                        astTypesCursor.moveToFirst();
                        while(!astTypesCursor.isAfterLast())
                        {
                            //go through the rows of the table, and if asteroid id is reg asteroid id, get
                            //all the informations from the columns
                            if(astTypesCursor.getInt(0) == REG_ASTEROID_ID)
                            {
                                r.setName(astTypesCursor.getString(1));
                                r.setImage(astTypesCursor.getString(2));
                                r.setImageWidth(astTypesCursor.getInt(3));
                                r.setImageHeight(astTypesCursor.getInt(4));
                                r.setType(astTypesCursor.getString(5));
                                asteroids.add(r);
                                break; //breaks out of while loop
                            }
                            astTypesCursor.moveToNext();
                        }
                        astTypesCursor.close();
                    }
                    else if (asteroidId == GRO_ASTEROID_ID)
                    {
                        GrowingAsteroid r = new GrowingAsteroid();
                        //access the asteroid types table to assign values to this asteroid
                        Cursor astTypesCursor = db.query("asteroidTypes", new String[]{"ASTEROID_ID", "NAME",
                                        "IMAGE", "IMAGE_WIDTH", "IMAGE_HEIGHT", "TYPE"}, null,
                                null, null, null, null);
                        astTypesCursor.moveToFirst();
                        while(!astTypesCursor.isAfterLast())
                        {
                            //go through the rows of the table, and if asteroid id is reg asteroid id, get
                            //all the informations from the columns
                            if(astTypesCursor.getInt(0) == GRO_ASTEROID_ID)
                            {
                                r.setName(astTypesCursor.getString(1));
                                r.setImage(astTypesCursor.getString(2));
                                r.setImageWidth(astTypesCursor.getInt(3));
                                r.setImageHeight(astTypesCursor.getInt(4));
                                r.setType(astTypesCursor.getString(5));
                                asteroids.add(r);
                                break; //breaks out of while loop
                            }
                            astTypesCursor.moveToNext();

                        }
                        astTypesCursor.close();
                    }
                    else if (asteroidId == OCT_ASTEROID_ID)
                    {
                        Octaroid r = new Octaroid();
                        //access the asteroid types table to assign values to this asteroid
                        Cursor astTypesCursor = db.query("asteroidTypes", new String[]{"ASTEROID_ID", "NAME",
                                        "IMAGE", "IMAGE_WIDTH", "IMAGE_HEIGHT", "TYPE"}, null,
                                null, null, null, null);
                        astTypesCursor.moveToFirst();
                        while(!astTypesCursor.isAfterLast())
                        {
                            //go through the rows of the table, and if asteroid id is reg asteroid id, get
                            //all the informations from the columns
                            if(astTypesCursor.getInt(0) == OCT_ASTEROID_ID)
                            {
                                r.setName(astTypesCursor.getString(1));
                                r.setImage(astTypesCursor.getString(2));
                                r.setImageWidth(astTypesCursor.getInt(3));
                                r.setImageHeight(astTypesCursor.getInt(4));
                                r.setType(astTypesCursor.getString(5));
                                asteroids.add(r);
                                break; //breaks out of while loop
                            }
                            astTypesCursor.moveToNext();
                        }
                        astTypesCursor.close();
                    }
                    else
                    {
                        //catch-all, if asteroid type is somehow read in as none of these three types
                        return null;
                    }
                }

            }
            cursor.moveToNext();
        }
        cursor.close();
        return asteroids;
    }


    /**
     * Reads all Level data from the database and returns a list of Level objects
     * @return An array list of level objects
     */
    public ArrayList<Level> getLevels()
    {
        ArrayList<Level> levels = new ArrayList<>();

        Cursor cursor = db.query("levels", new String[]{"NUMBER", "TITLE", "HINT", "WIDTH", "HEIGHT",
                "MUSIC"}, null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            Level level = new Level();
            level.setNumber(cursor.getInt(0));
            level.setTitle(cursor.getString(1));
            level.setHint(cursor.getString(2));
            level.setLevelWidth(cursor.getInt(3));
            level.setLevelHeight(cursor.getInt(4));
            level.setMusic(cursor.getString(5));
            level.setLevelObjects(getLevelObjects(cursor.getInt(0)));
            level.setLevelAsteroids(getLevelAsteroids(cursor.getInt(0)));
            levels.add(level);
            cursor.moveToNext();
        }
        cursor.close();
        return levels;
    }

    /**
     * Adds an Asteroid object's data to the database
     * btw this loads in just the asteroid types
     * @param asteroid the asteroid object to be added
     */
    public boolean addAsteroid(Asteroid asteroid)
    {
        ContentValues values = new ContentValues();
        values.put("ASTEROID_ID", asteroidIDCount);
        values.put("NAME", asteroid.getName());
        values.put("IMAGE", asteroid.getImage());
        values.put("IMAGE_WIDTH", asteroid.getImageWidth());
        values.put("IMAGE_HEIGHT", asteroid.getImageHeight());
        values.put("TYPE", asteroid.getType());

        long rowNumber = db.insert("asteroidTypes", null, values);
        asteroidIDCount++; //increment the global variable
        if(rowNumber >= 0)
        {
            return true;
        }
        else return false;
    }

    /**
     * Reads all asteroid data from the database and returns a list of Asteroid objects
     * btw this just gets the asteroid type data
     * @return An array list of Asteroid objects
     */
    public ArrayList<Asteroid> getAsteroids()
    {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Cursor cursor = db.query("asteroidTypes", new String[]{"ASTEROID_ID", "NAME", "IMAGE",
                        "IMAGE_WIDTH", "IMAGE_HEIGHT", "TYPE"}, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            int asteroidID = cursor.getInt(0);
            if (asteroidID == REG_ASTEROID_ID)
            {
                RegularAsteroid r = new RegularAsteroid();
                r.setName(cursor.getString(1));
                r.setImage(cursor.getString(2));
                r.setImageWidth(cursor.getInt(3));
                r.setImageHeight(cursor.getInt(4));
                r.setType(cursor.getString(5));
                asteroids.add(r);
            }
            else if (asteroidID == GRO_ASTEROID_ID)
            {
                GrowingAsteroid r = new GrowingAsteroid();
                r.setName(cursor.getString(1));
                r.setImage(cursor.getString(2));
                r.setImageWidth(cursor.getInt(3));
                r.setImageHeight(cursor.getInt(4));
                r.setType(cursor.getString(5));
                asteroids.add(r);
            }
            else if (asteroidID == OCT_ASTEROID_ID)
            {
                Octaroid r = new Octaroid();
                r.setName(cursor.getString(1));
                r.setImage(cursor.getString(2));
                r.setImageWidth(cursor.getInt(3));
                r.setImageHeight(cursor.getInt(4));
                r.setType(cursor.getString(5));
                asteroids.add(r);
            }
            else
            {
                return null; //catch-all if asteroid id wasnt valid
            }
            cursor.moveToNext();
        }
        cursor.close();
        return asteroids;
    }

    /**
     * Adds a mainbody object's data to the database
     * @param mainBody the main body object to be added
     */
    public boolean addMainBody(MainBody mainBody)
    {
        ContentValues values = new ContentValues();
        //cannon, engine, and extra attach are pointF and need to be converted to strings
        String cannonAttach = String.valueOf(mainBody.getCannonAttach().x) + "," + String.valueOf(mainBody.getCannonAttach().y);
        String engineAttach = String.valueOf(mainBody.getEngineAttach().x) + "," + String.valueOf(mainBody.getEngineAttach().y);
        String extraAttach =  String.valueOf(mainBody.getExtraAttach().x) + "," + String.valueOf(mainBody.getExtraAttach().y);
        //now put the values in the contentvalues
        values.put("CANNON_ATTACH", cannonAttach);
        values.put("ENGINE_ATTACH", engineAttach);
        values.put("EXTRA_ATTACH", extraAttach);
        values.put("IMAGE", mainBody.getImage());
        values.put("IMAGE_WIDTH", mainBody.getImageWidth());
        values.put("IMAGE_HEIGHT", mainBody.getImageHeight());

        long rowNumber = db.insert("mainBodies", null, values);
        if (rowNumber >= 0)
            return true;
        else return false;
    }


    /**
     * Reads all main body data from the database and returns a list of mainbody objects
     * @return An arraylist of main body objects
     */
    public ArrayList<MainBody> getMainBodies()
    {
        ArrayList<MainBody> mainBodies = new ArrayList<>();

        Cursor cursor = db.query("mainBodies", new String[]{"CANNON_ATTACH", "ENGINE_ATTACH",
                "EXTRA_ATTACH", "IMAGE", "IMAGE_WIDTH", "IMAGE_HEIGHT"}, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            MainBody m = new MainBody();
            //convert string to pointf
            PointF cannonAttach = PointFStringConverter.stringToPointF(cursor.getString(0));
            m.setCannonAttach(cannonAttach);
            PointF engineAttach = PointFStringConverter.stringToPointF(cursor.getString(1));
            m.setEngineAttach(engineAttach);
            PointF extraAttach = PointFStringConverter.stringToPointF(cursor.getString(2));
            m.setExtraAttach(extraAttach);
            m.setImage(cursor.getString(3));
            m.setImageWidth(cursor.getInt(4));
            m.setImageHeight(cursor.getInt(5));
            mainBodies.add(m);
            cursor.moveToNext();
        }

        cursor.close();
        return mainBodies;
    }

    /**
     * Adds a cannon object's data to the database
     * @param cannon the cannon object to be added
     */
    public boolean addCannon(Cannon cannon)
    {
        ContentValues values = new ContentValues();
        //attach and emit points need conversion to string
        String attachPoint = String.valueOf(cannon.getAttachPoint().x) + "," + String.valueOf(cannon.getAttachPoint().y);
        String emitPoint = String.valueOf(cannon.getEmitPoint().x) + "," + String.valueOf(cannon.getEmitPoint().y);
        //Now put the values in values
        values.put("ATTACH_POINT", attachPoint);
        values.put("EMIT_POINT", emitPoint);
        values.put("IMAGE", cannon.getImage());
        values.put("IMAGE_WIDTH", cannon.getImageWidth());
        values.put("IMAGE_HEIGHT", cannon.getImageHeight());
        values.put("ATTACK_IMAGE", cannon.getAttackImage());
        values.put("ATTACK_IMAGE_WIDTH", cannon.getAttackImageWidth());
        values.put("ATTACK_IMAGE_HEIGHT", cannon.getAttackImageHeight());
        values.put("ATTACK_SOUND", cannon.getAttackSound());
        values.put("DAMAGE", cannon.getDamage());

        long rowNumber = db.insert("cannons", null, values);
        if (rowNumber >= 0)
            return true;
        else return false;
    }

    /**
     * Reads all the data for cannons in db and returns a list of cannon objects
     * @return An array list of Cannon objects
     */
    public ArrayList<Cannon> getCannons()
    {
        ArrayList<Cannon> cannons = new ArrayList<>();

        Cursor cursor = db.query("cannons", new String[]{"ATTACH_POINT", "EMIT_POINT", "IMAGE",
                "IMAGE_WIDTH", "IMAGE_HEIGHT", "ATTACK_IMAGE", "ATTACK_IMAGE_WIDTH", "ATTACK_IMAGE_HEIGHT",
                "ATTACK_SOUND", "DAMAGE"}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Cannon c = new Cannon();
            //convert attachpoint and emitpoint strings to pointf
            PointF attachPoint = PointFStringConverter.stringToPointF(cursor.getString(0));
            c.setAttachPoint(attachPoint);
            PointF emitPoint = PointFStringConverter.stringToPointF(cursor.getString(1));
            c.setEmitPoint(emitPoint);
            c.setImage(cursor.getString(2));
            c.setImageWidth(cursor.getInt(3));
            c.setImageHeight(cursor.getInt(4));
            c.setAttackImage(cursor.getString(5));
            c.setAttackImageWidth(cursor.getInt(6));
            c.setAttackImageHeight(cursor.getInt(7));
            c.setAttackSound(cursor.getString(8));
            c.setDamage(cursor.getInt(9));

            cannons.add(c);
            cursor.moveToNext();
        }
        cursor.close();
        return cannons;
    }

    /**
     * Adds an extrapart object's data to the db
     * @param extraPart The object to be added
     */
    public boolean addExtraPart(ExtraPart extraPart)
    {
        ContentValues values = new ContentValues();
        //attach point must be converted to string
        String attachPoint = extraPart.getAttachPoint().x + "," + extraPart.getAttachPoint().y;
        //now fill the values
        values.put("ATTACH_POINT", attachPoint);
        values.put("IMAGE", extraPart.getImage());
        values.put("IMAGE_WIDTH", extraPart.getImageWidth());
        values.put("IMAGE_HEIGHT", extraPart.getImageHeight());

        long rowNumber = db.insert("extraParts", null, values);
        if (rowNumber >= 0)
            return true;
        else return false;
    }

    /**
     * Reads all the ExtraPart data in the db and returns a list of all extra parts
     * @return An array list of all extraPart objects
     */
    public ArrayList<ExtraPart> getExtraParts()
    {
        ArrayList<ExtraPart> extraParts = new ArrayList<>();
        Cursor cursor = db.query("extraParts", new String[]{"ATTACH_POINT", "IMAGE", "IMAGE_WIDTH",
                        "IMAGE_HEIGHT"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ExtraPart e = new ExtraPart();
            //convert string attachpoint to pointf
            PointF attachPoint = PointFStringConverter.stringToPointF(cursor.getString(0));
            e.setAttachPoint(attachPoint);
            e.setImage(cursor.getString(1));
            e.setImageWidth(cursor.getInt(2));
            e.setImageHeight(cursor.getInt(3));

            extraParts.add(e);
            cursor.moveToNext();
        }
        cursor.close();
        return extraParts;
    }

    /**
     * Adds an Engine object's data to db
     * @param engine The object to be added
     */
    public boolean addEngine(Engine engine)
    {
        ContentValues values = new ContentValues();
        //convert attach point to string
        String attachPoint = String.valueOf(engine.getAttachPoint().x) + "," + String.valueOf(engine.getAttachPoint().y);
        //now fill values
        values.put("BASE_SPEED", engine.getBaseSpeed());
        values.put("BASE_TURN_RATE", engine.getBaseTurnRate());
        values.put("ATTACH_POINT", attachPoint);
        values.put("IMAGE", engine.getImage());
        values.put("IMAGE_WIDTH", engine.getImageWidth());
        values.put("IMAGE_HEIGHT", engine.getImageHeight());

        long rowNumber = db.insert("engines", null, values);
        if (rowNumber >= 0)
            return true;
        else return false;
    }

    /**
     * Reads the engine data and returns a list of all engine objects
     * @return An array list of all engines in the db
     */
    public ArrayList<Engine> getEngines()
    {
        ArrayList<Engine> engines = new ArrayList<>();
        Cursor cursor = db.query("engines", new String[]{"BASE_SPEED", "BASE_TURN_RATE", "ATTACH_POINT",
                        "IMAGE", "IMAGE_WIDTH", "IMAGE_HEIGHT"}, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            Engine e = new Engine();
            e.setBaseSpeed(cursor.getInt(0));
            e.setBaseTurnRate(cursor.getInt(1));
            PointF attachPoint = PointFStringConverter.stringToPointF(cursor.getString(2));
            e.setAttachPoint(attachPoint);
            e.setImage(cursor.getString(3));
            e.setImageWidth(cursor.getInt(4));
            e.setImageHeight(cursor.getInt(5));

            engines.add(e);
            cursor.moveToNext();
        }
        cursor.close();
        return engines;
    }

    /**
     * Adds a powercore's data to the database
     * @param powerCore The object to be added
     */
    public boolean addPowerCore(PowerCore powerCore)
    {
        ContentValues values = new ContentValues();
        values.put("CANNON_BOOST", powerCore.getCannonBoost());
        values.put("ENGINE_BOOST", powerCore.getEngineBoost());
        values.put("IMAGE", powerCore.getImage());
        long rowNumber = db.insert("powerCores", null, values);
        if (rowNumber >= 0)
            return true;
        else return false;
    }

    /**
     * Reads all the power core data in db and returns a list of all power cores
     * @return An array list of power core objects
     */
    public ArrayList<PowerCore> getPowerCores()
    {
        ArrayList<PowerCore> powerCores = new ArrayList<>();
        Cursor cursor = db.query("powerCores", new String[]{"CANNON_BOOST", "ENGINE_BOOST", "IMAGE"},
                        null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            PowerCore p = new PowerCore();
            p.setCannonBoost(cursor.getInt(0));
            p.setEngineBoost(cursor.getInt(1));
            p.setImage(cursor.getString(2));
            powerCores.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        return powerCores;
    }

}
