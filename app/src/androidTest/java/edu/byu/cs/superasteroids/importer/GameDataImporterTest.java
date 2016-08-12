package edu.byu.cs.superasteroids.importer;

import android.content.res.AssetManager;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.database.PointFStringConverter;
import edu.byu.cs.superasteroids.model.Asteroid;
import edu.byu.cs.superasteroids.model.BackgroundObject;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;

/**
 * Created by Tyler on 7/16/2016.
 */
public class GameDataImporterTest extends AndroidTestCase {

    private ImportActivity i = new ImportActivity();

    public void testDataImporter()
    {
        Database.createInstanceifNull(getContext()).reset();
        GameDataImporter importer = new GameDataImporter(i);
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("gamedata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        importer.importData(reader);
        ArrayList<Asteroid> asteroidTypes = Database.getInstance().getDAO().getAsteroids();
        assertEquals(3, asteroidTypes.size());

        ArrayList<MainBody> mainBodies = Database.getInstance().getDAO().getMainBodies();
        assertEquals(2, mainBodies.size());
    }

    public void testModelLoader()
    {
        Database.createInstanceifNull(getContext()).reset();
        GameDataImporter importer = new GameDataImporter(i);
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("gamedata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        importer.importData(reader);
        //Now the stuff is in the model objects
        ArrayList<Level> levels = Database.getInstance().getModelContainer().getLevels();
        ArrayList<MainBody> mainBodies = Database.getInstance().getDAO().getMainBodies();
        ArrayList<ExtraPart> extraParts = Database.getInstance().getDAO().getExtraParts();
        assertEquals(2, extraParts.size());
        assertEquals(5, levels.size());
        assertEquals(2, mainBodies.size());
    }

    public void testCannonLoading()
    {
        Database.createInstanceifNull(getContext()).reset();
        GameDataImporter importer = new GameDataImporter(i);
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("gamedata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        importer.importData(reader);

        ArrayList<Cannon> cannons = Database.getInstance().getDAO().getCannons();
        assertEquals(2, cannons.size());

    }

    public void testPowerCoreLoading()
    {
        Database.createInstanceifNull(getContext()).reset();
        GameDataImporter importer = new GameDataImporter(i);
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("gamedata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        importer.importData(reader);

        ArrayList<PowerCore> powerCores = Database.getInstance().getDAO().getPowerCores();
        assertEquals(2, powerCores.size());
    }

    public void testlevelObjectsLoader()
    {
        Database.createInstanceifNull(getContext()).reset();
        GameDataImporter importer = new GameDataImporter(i);
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("gamedata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        importer.importData(reader);
        ArrayList<BackgroundObject> backgroundObjects = Database.getInstance().getDAO().getLevelObjects(1);
        assertEquals(1, backgroundObjects.size());
    }

    public void testLevelAsteroidsLoader()
    {
        Database.createInstanceifNull(getContext()).reset();
        GameDataImporter importer = new GameDataImporter(i);
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("gamedata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        importer.importData(reader);
        ArrayList<Asteroid> asteroids = Database.getInstance().getDAO().getLevelAsteroids(1);
        assertEquals(8, asteroids.size());
    }

}
