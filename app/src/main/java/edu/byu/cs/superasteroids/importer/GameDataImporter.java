package edu.byu.cs.superasteroids.importer;

import android.database.Cursor;
import android.graphics.PointF;
import android.provider.ContactsContract;
import android.util.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.AsteroidsDAO;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.database.PointFStringConverter;
import edu.byu.cs.superasteroids.main_menu.MainActivity;
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
 * Created by Tyler on 7/14/2016.
 */
public class GameDataImporter implements IGameDataImporter {

    //An instance of the import activity for context
    ImportActivity importActivity;

    //Constructor passes in instance of import activity
    public GameDataImporter(ImportActivity i)
    {
        importActivity = i;
    }


    /**
     * Parses the JSON stream style to get all the information out, puts them in temporary model objects,
     * and then loads those into the database
     * @param jsonReader The json reader created in the wrapping function
     * @throws IOException
     */
    private void readJson (JsonReader jsonReader) throws IOException
    {
        jsonReader.beginObject();
        jsonReader.nextName(); //consume the name asteroidsGame
        jsonReader.beginObject();//begins the object asteroidsGame
        jsonReader.nextName(); //consume the name Objects
        jsonReader.beginArray(); //enters the array of image file paths
        while(jsonReader.hasNext())//while there are still strings in the array
        {
            //get the strings and create backgroundObjects for them and save them into the database
            String imagePath = jsonReader.nextString();
            BackgroundObject b = new BackgroundObject();
            b.setImage(imagePath);
            Database.getInstance().getDAO().addBackgroundObject(b);
        }
        jsonReader.endArray(); //close the objects array

        //ASTEROIDS
        jsonReader.nextName(); //consumes the name "asteroids"
        jsonReader.beginArray(); //begins the asteroids array
        int asteroidIDCount = 1;
        while(jsonReader.hasNext()) //while the asteroid types array still has elements
        {
            //this is an array of objects
            jsonReader.beginObject();
            Asteroid asteroid;
            //initialize the members to stub values
            String asteroidName = "";
            String image = "";
            int imageWidth = 0;
            int imageHeight = 0;
            String type = "";
            while (jsonReader.hasNext()) //going through this object
            {
                String propName = jsonReader.nextName();
                switch (propName){
                    case "name":
                        asteroidName = jsonReader.nextString();
                        break;
                    case "image":
                        image = jsonReader.nextString();
                        break;
                    case "imageWidth":
                        imageWidth = jsonReader.nextInt();
                        break;
                    case "imageHeight":
                        imageHeight = jsonReader.nextInt();
                        break;
                    case "type":
                        type = jsonReader.nextString();
                        break;
                }
            }
            jsonReader.endObject(); //end of the asteroid object in this array
            //now create an object that has these values and put it in the database with addAsteroids
            if (asteroidIDCount == 1)
            {
                asteroid = new RegularAsteroid();
            }
            else if (asteroidIDCount == 2)
            {
                asteroid = new GrowingAsteroid();
            }
            else
            {
                asteroid = new Octaroid();
            }
            //now build the asteroid and put it in the db
            asteroid.setName(asteroidName);
            asteroid.setImage(image);
            asteroid.setImageWidth(imageWidth);
            asteroid.setImageHeight(imageHeight);
            asteroid.setType(type);
            Database.getInstance().getDAO().addAsteroid(asteroid);
        }
        jsonReader.endArray(); //end of the asteroid types array

        //LEVELS
        jsonReader.nextName(); //consumes the name "levels
        jsonReader.beginArray(); //beginning the levels array
        while(jsonReader.hasNext()) //going through all the level objects in the array
        {
            jsonReader.beginObject(); //opening a level object within the levels array
            Level level = new Level(); //create a new level model object
            while(jsonReader.hasNext()) //going through the current level object
            {
                String propName = jsonReader.nextName();
                switch (propName){ //switch through the different property names
                    case "number":
                        level.setNumber(jsonReader.nextInt());
                        break;
                    case "title":
                        level.setTitle(jsonReader.nextString());
                        break;
                    case "hint":
                        level.setHint(jsonReader.nextString());
                        break;
                    case "width":
                        level.setLevelWidth(jsonReader.nextInt());
                        break;
                    case "height":
                        level.setLevelHeight(jsonReader.nextInt());
                        break;
                    case "music":
                        level.setMusic(jsonReader.nextString());
                        break;
                    case "levelObjects":
                        //open this inner array
                        jsonReader.beginArray(); //beginning the inner level objects array
                        ArrayList<BackgroundObject> bObjectsList = new ArrayList<>();
                        while(jsonReader.hasNext()) //going through the objects in this array
                        {
                            jsonReader.beginObject();//open the object
                            BackgroundObject backgroundObject = new BackgroundObject(); //create a new backgroundObject
                            while (jsonReader.hasNext()) //go through the items in the object
                            {
                                String levObjPropName = jsonReader.nextName();
                                switch (levObjPropName){
                                    case "position":
                                        PointF point = PointFStringConverter.stringToPointF(jsonReader.nextString());
                                        backgroundObject.setPosition(point);
                                        break;
                                    case "objectId":
                                        //i need to use this id to get the image file path
                                        //since i have already populated the table of objects, i could
                                        //just get the image path from there
                                        ArrayList<BackgroundObject> b =  Database.getInstance().getDAO().getBackgroundObjects();
                                        int objectID = jsonReader.nextInt();
                                        int forCount = 1; //this will increment every time through the for loop
                                        for (BackgroundObject temp : b)//iterate through the list of backgroundobjects
                                        {
                                            //background objects were added to the list in order and maintain that order
                                            //i can just get the object_idth one
                                            if (objectID == forCount)
                                            {
                                                //get the image and save it into the current background object
                                                backgroundObject.setImage(temp.getImage());
                                                break; //break out of the for loop
                                            }
                                            forCount++;
                                        }
                                        break;
                                    case "scale":
                                        float scale =(float) jsonReader.nextDouble();
                                        backgroundObject.setScale(scale);
                                        break;
                                    default:
                                        //catch-all
                                        break;
                                }
                            }
                            jsonReader.endObject(); //closing the levelobjects object
                            bObjectsList.add(backgroundObject);
                        }
                        jsonReader.endArray(); //close the level objects array
                        level.setLevelObjects(bObjectsList); //set the level's objects list to the one just made
                        break;
                    case "levelAsteroids":
                        //open this inner array
                        jsonReader.beginArray(); //begin the level asteroids array
                        ArrayList<Asteroid> asteroidsList = new ArrayList<>(); //create a list to store all the level asteroids in
                        while(jsonReader.hasNext()) //go through the level asteroids array
                        {
                            jsonReader.beginObject(); //open the levelAsteroids json object
                            int numberOfAsteroids = 0;
                            int typeOfAsteroid = 0;
                            while(jsonReader.hasNext())
                            {
                                //in each of these objects we will be adding a certain number of asteroids to a
                                //list of asteroids
                                String lvlAsteroidsPropName = jsonReader.nextName();
                                switch (lvlAsteroidsPropName){
                                    case "number":
                                        numberOfAsteroids = jsonReader.nextInt();
                                        break;
                                    case "asteroidId":
                                        typeOfAsteroid = jsonReader.nextInt();
                                        break;
                                    default:
                                        break;
                                }
                            }
                            jsonReader.endObject(); //close the current object
                            //now number of asteroid and type of asteroid are filled. we must create asteroids
                            //of these types
                            //Get the list of types from the database
                            ArrayList<Asteroid> asteroidTypesList = Database.getInstance().getDAO().getAsteroids();
                            if (typeOfAsteroid == 1)
                            {
                                //make numberofAsteroids regular asteroids
                                for (int i = 0; i < numberOfAsteroids; i++)
                                {
                                    RegularAsteroid r = new RegularAsteroid();
                                    //fill this r with the stuff from the asteroid
                                    for (Asteroid a : asteroidTypesList)
                                    {
                                        if (a.getName().equals("regular"))
                                        {
                                            r.setName(a.getName());
                                            r.setImage(a.getImage());
                                            r.setImageWidth(a.getImageWidth());
                                            r.setImageHeight(a.getImageHeight());
                                            r.setType(a.getType());
                                            asteroidsList.add(r);
                                            break; //break out of for loop, we're done
                                        }
                                    }
                                }
                            }
                            else if (typeOfAsteroid == 2)
                            {
                                //make numberofAsteroids growing asteroids
                                for (int i = 0; i < numberOfAsteroids; i++)
                                {
                                    GrowingAsteroid r = new GrowingAsteroid();
                                    //fill this r with the stuff from the asteroid
                                    for (Asteroid a : asteroidTypesList)
                                    {
                                        if (a.getName().equals("growing"))
                                        {
                                            r.setName(a.getName());
                                            r.setImage(a.getImage());
                                            r.setImageWidth(a.getImageWidth());
                                            r.setImageHeight(a.getImageHeight());
                                            r.setType(a.getType());
                                            asteroidsList.add(r);
                                            break;
                                        }
                                    }
                                }
                            }
                            else if (typeOfAsteroid == 3)
                            {
                                //make numberofAsteroids octaroids asteroids
                                for (int i = 0; i < numberOfAsteroids; i++)
                                {
                                    Octaroid r = new Octaroid();
                                    //fill this r with the stuff from the asteroid
                                    for (Asteroid a : asteroidTypesList)
                                    {
                                        if (a.getName().equals("octeroid"))
                                        {
                                            r.setName(a.getName());
                                            r.setImage(a.getImage());
                                            r.setImageWidth(a.getImageWidth());
                                            r.setImageHeight(a.getImageHeight());
                                            r.setType(a.getType());
                                            asteroidsList.add(r);
                                            break;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                //catch-All
                            }
                        }
                        //put the asteroidsList into the level object
                        level.setLevelAsteroids(asteroidsList);
                        jsonReader.endArray(); //end of the levelAsteroids array
                        break;
                    default:
                        //catch all, if the property name wasnt any of these
                        break;

                }
            }
            jsonReader.endObject(); //ends the current level object
            Database.getInstance().getDAO().addLevel(level);
        }
        jsonReader.endArray(); //ends the levels array

        //MAIN BODIES
        jsonReader.nextName(); //consumes the name "mainBodies"
        jsonReader.beginArray(); //opens the array of main body objects
        while(jsonReader.hasNext()) //go through the array of main body objects
        {
            MainBody mainBody = new MainBody(); //create a new main body function
            jsonReader.beginObject(); //opens the main body object
            while(jsonReader.hasNext())//go through all the elements in the main body object
            {
                String propName = jsonReader.nextName();
                switch (propName){
                    case "cannonAttach":
                        PointF cannonPoint = PointFStringConverter.stringToPointF(jsonReader.nextString());
                        mainBody.setCannonAttach(cannonPoint);
                        break;
                    case "engineAttach":
                        PointF enginePoint = PointFStringConverter.stringToPointF(jsonReader.nextString());
                        mainBody.setEngineAttach(enginePoint);
                        break;
                    case "extraAttach":
                        PointF extraPoint = PointFStringConverter.stringToPointF(jsonReader.nextString());
                        mainBody.setExtraAttach(extraPoint);
                        break;
                    case "image":
                        mainBody.setImage(jsonReader.nextString());
                        break;
                    case "imageWidth":
                        mainBody.setImageWidth(jsonReader.nextInt());
                        break;
                    case "imageHeight":
                        mainBody.setImageHeight(jsonReader.nextInt());
                        break;
                }
            }
            jsonReader.endObject();
            Database.getInstance().getDAO().addMainBody(mainBody);//add the mainbody to the database
        }
        jsonReader.endArray();

        //CANNONS
        jsonReader.nextName(); //consumes the name "cannons"
        jsonReader.beginArray(); //opens the array of cannon objects
        while (jsonReader.hasNext())//go through the array of cannon objects
        {
            Cannon cannon = new Cannon();//create a new cannon object
            jsonReader.beginObject(); //opens the cannon json object
            while(jsonReader.hasNext()) //iterate through the json cannon object
            {
                String propName = jsonReader.nextName();
                switch (propName){
                    case "attachPoint":
                        PointF attachPoint = PointFStringConverter.stringToPointF(jsonReader.nextString());
                        cannon.setAttachPoint(attachPoint);
                        break;
                    case "emitPoint":
                        PointF emitPoint = PointFStringConverter.stringToPointF(jsonReader.nextString());
                        cannon.setEmitPoint(emitPoint);
                        break;
                    case "image":
                        cannon.setImage(jsonReader.nextString());
                        break;
                    case "imageWidth":
                        cannon.setImageWidth(jsonReader.nextInt());
                        break;
                    case "imageHeight":
                        cannon.setImageHeight(jsonReader.nextInt());
                        break;
                    case "attackImage":
                        cannon.setAttackImage(jsonReader.nextString());
                        break;
                    case "attackImageWidth":
                        cannon.setAttackImageWidth(jsonReader.nextInt());
                        break;
                    case "attackImageHeight":
                        cannon.setAttackImageHeight(jsonReader.nextInt());
                        break;
                    case "attackSound":
                        cannon.setAttackSound(jsonReader.nextString());
                        break;
                    case "damage":
                        cannon.setDamage(jsonReader.nextInt());
                        break;
                }
            }
            jsonReader.endObject(); //close the cannon object
            Database.getInstance().getDAO().addCannon(cannon);
        }
        jsonReader.endArray();

        //EXTRA PARTS
        jsonReader.nextName(); //consumes "engines" name
        jsonReader.beginArray(); //begins the engines array
        while(jsonReader.hasNext()) //iterate through the array of extra parts
        {
            ExtraPart extraPart = new ExtraPart(); //create a new extra part to fill
            jsonReader.beginObject(); //opens an extra part json object
            while(jsonReader.hasNext()) //iterate through the elements of the extrapart object
            {
                String propName = jsonReader.nextName();
                switch(propName){
                    case "attachPoint":
                        PointF attachPoint = PointFStringConverter.stringToPointF(jsonReader.nextString());
                        extraPart.setAttachPoint(attachPoint);
                        break;
                    case "image":
                        extraPart.setImage(jsonReader.nextString());
                        break;
                    case "imageWidth":
                        extraPart.setImageWidth(jsonReader.nextInt());
                        break;
                    case "imageHeight":
                        extraPart.setImageHeight(jsonReader.nextInt());
                        break;
                }
            }
            jsonReader.endObject(); //closes that extrapart object
            Database.getInstance().getDAO().addExtraPart(extraPart);//add that extra part to the db
        }
        jsonReader.endArray();

        //ENGINES
        jsonReader.nextName(); //consumes the name "engines"
        jsonReader.beginArray(); //opens the engines array
        while (jsonReader.hasNext()) //iterates through the engines array
        {
            Engine engine = new Engine(); //create a new engine model object
            jsonReader.beginObject(); //opens the engine json object
            while(jsonReader.hasNext()) //iterates through the json object of engine
            {
                String propName = jsonReader.nextName();
                switch(propName){
                    case "baseSpeed":
                        engine.setBaseSpeed(jsonReader.nextInt());
                        break;
                    case "baseTurnRate":
                        engine.setBaseTurnRate(jsonReader.nextInt());
                        break;
                    case "attachPoint":
                        PointF attachPoint = PointFStringConverter.stringToPointF(jsonReader.nextString());
                        engine.setAttachPoint(attachPoint);
                        break;
                    case "image":
                        engine.setImage(jsonReader.nextString());
                        break;
                    case "imageWidth":
                        engine.setImageWidth(jsonReader.nextInt());
                        break;
                    case "imageHeight":
                        engine.setImageHeight(jsonReader.nextInt());
                        break;
                }
            }
            jsonReader.endObject(); //closes the engine json object
            Database.getInstance().getDAO().addEngine(engine); //adds the engine to the db
        }
        jsonReader.endArray();//ends the engines array

        //POWER CORES
        jsonReader.nextName(); //consumes the name "powerCores
        jsonReader.beginArray(); //opens the array of power core objects
        while (jsonReader.hasNext()) //iterates through the array of power core objects
        {
            PowerCore powerCore = new PowerCore(); //create a new power core model object
            jsonReader.beginObject(); //open the power core json object
            while(jsonReader.hasNext())//iterate through the elements of the power core object json
            {
                String propName = jsonReader.nextName();
                switch(propName){
                    case "cannonBoost":
                        powerCore.setCannonBoost(jsonReader.nextInt());
                        break;
                    case "engineBoost":
                        powerCore.setEngineBoost(jsonReader.nextInt());
                        break;
                    case "image":
                        powerCore.setImage(jsonReader.nextString());
                        break;
                    default:
                        break;
                }
            }
            jsonReader.endObject(); //closes the power core object
            Database.getInstance().getDAO().addPowerCore(powerCore);
        }
        jsonReader.endArray(); //close the power core array

        //get rid of two final braces
        jsonReader.endObject();
        jsonReader.endObject();

    }

    /**
     * Imports the data from the .json file the given InputStreamReader is connected to. Imported data
     * should be stored in a SQLite database for use in the ship builder and the game.
     *
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return TRUE if the data was imported successfully, FALSE if the data was not imported due
     * to any error.
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        //Reset the database and clear the model container
        Database.createInstanceifNull(importActivity.getApplicationContext()).reset();
        Database.getInstance().getModelContainer().clearAll();

        //We will read the JSON with the stream reader method
        JsonReader jsonReader = new JsonReader(dataInputReader);
        try {
            readJson(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}