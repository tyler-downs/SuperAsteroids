package edu.byu.cs.superasteroids.database;

import android.graphics.PointF;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model.Asteroid;
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
 * Created by Tyler on 7/15/2016.
 */
public class AsteroidsDAOTest extends AndroidTestCase {

    /*
    I am using the debugger to break through and see whether what I get out of the Database
    is the same as what I put in
     */

    public void testAddBackgroundObject() {
        Database.createInstanceifNull(getContext()).reset();
        BackgroundObject backgroundObject = new BackgroundObject();
        backgroundObject.setImage("Aw Yes" );
        Database.getInstance().getDAO().addBackgroundObject(backgroundObject);
        ArrayList<BackgroundObject> objects = Database.getInstance().getDAO().getBackgroundObjects();
        assertEquals(objects.size(), 1);
    }

    public void testMultBackgroundObjects() {
        Database.createInstanceifNull(getContext()).reset();
        BackgroundObject b1 = new BackgroundObject();
        BackgroundObject b2 = new BackgroundObject();
        BackgroundObject b3 = new BackgroundObject();
        BackgroundObject b4 = new BackgroundObject();
        BackgroundObject b5 = new BackgroundObject();
        b1.setImage("There" );
        b2.setImage("will" );
        b3.setImage("be" );
        b4.setImage("better" );
        b5.setImage("days" );
        Database.getInstance().getDAO().addBackgroundObject(b1);
        Database.getInstance().getDAO().addBackgroundObject(b2);
        Database.getInstance().getDAO().addBackgroundObject(b3);
        Database.getInstance().getDAO().addBackgroundObject(b4);
        Database.getInstance().getDAO().addBackgroundObject(b5);
        ArrayList<BackgroundObject> objects = Database.getInstance().getDAO().getBackgroundObjects();
        assertEquals(objects.size(), 5);
    }

    public void testAddLevel() {
        Database.createInstanceifNull(getContext()).reset();
        Level level = new Level();
        ArrayList<Asteroid> levelAsteroids = new ArrayList<>();
        Octaroid o = new Octaroid();
        levelAsteroids.add(o);
        level.setLevelAsteroids(levelAsteroids);
        ArrayList<BackgroundObject> bo = new ArrayList<>();
        BackgroundObject b = new BackgroundObject();
        b.setImage("Hello people of earth" );
        b.setImageHeight(0);
        b.setImageWidth(0);
        b.setPosition(new PointF(2, 4));

        bo.add(b);
        level.setLevelObjects(bo);
        level.setNumber(1);
        level.setLevelWidth(0);
        level.setLevelHeight(0);
        level.setMusic("music" );
        level.setHint("just do it" );
        level.setTitle("Big mama's house 3" );
        //now add the level to the database
        Database.getInstance().getDAO().addLevel(level);
        ArrayList<Level> levelsList = Database.getInstance().getDAO().getLevels();
        assertEquals(levelsList.size(), 1);
    }

    public void testAddMultLevels() {
        Database.createInstanceifNull(getContext()).reset();
        Level level1 = new Level();
        Level level2 = new Level();
        Level level3 = new Level();
        //LEVEL 1
        ArrayList<Asteroid> levelAsteroids1 = new ArrayList<>();
        Octaroid o1 = new Octaroid();
        levelAsteroids1.add(o1);
        level1.setLevelAsteroids(levelAsteroids1);
        ArrayList<BackgroundObject> bo1 = new ArrayList<>();
        BackgroundObject b1 = new BackgroundObject();
        b1.setImage("Hello people of earth" );
        b1.setImageHeight(0);
        b1.setImageWidth(0);
        b1.setPosition(new PointF(2, 4));
        bo1.add(b1);
        level1.setLevelObjects(bo1);
        level1.setNumber(1);
        level1.setLevelWidth(0);
        level1.setLevelHeight(0);
        level1.setMusic("music" );
        level1.setHint("just do it" );
        level1.setTitle("Level 1" );
        //now add the level to the database
        Database.getInstance().getDAO().addLevel(level1);
        //LEVEL 2
        ArrayList<Asteroid> levelAsteroids2 = new ArrayList<>();
        Octaroid o2 = new Octaroid();
        levelAsteroids2.add(o2);
        level2.setLevelAsteroids(levelAsteroids2);
        ArrayList<BackgroundObject> bo2 = new ArrayList<>();
        BackgroundObject b2 = new BackgroundObject();
        b2.setImage("Hello people of earth" );
        b2.setImageHeight(0);
        b2.setImageWidth(0);
        b2.setPosition(new PointF(2, 4));
        bo2.add(b2);
        level2.setLevelObjects(bo2);
        level2.setNumber(2);
        level2.setLevelWidth(0);
        level2.setLevelHeight(0);
        level2.setMusic("music" );
        level2.setHint("just do it" );
        level2.setTitle("Level 2" );
        //now add the level to the database
        boolean addLevel2WasSuccessful = Database.getInstance().getDAO().addLevel(level2);
        //LEVEL 3
        ArrayList<Asteroid> levelAsteroids3 = new ArrayList<>();
        Octaroid o3 = new Octaroid();
        levelAsteroids3.add(o3);
        level3.setLevelAsteroids(levelAsteroids3);
        ArrayList<BackgroundObject> bo3 = new ArrayList<>();
        BackgroundObject b3 = new BackgroundObject();
        b3.setImage("Hello people of earth" );
        b3.setImageHeight(0);
        b3.setImageWidth(0);
        b3.setPosition(new PointF(2, 4));
        bo3.add(b3);
        level3.setLevelObjects(bo3);
        level3.setNumber(3);
        level3.setLevelWidth(0);
        level3.setLevelHeight(0);
        level3.setMusic("music" );
        level3.setHint("just do it" );
        level3.setTitle("Level 3" );
        //now add the level to the database
        boolean addLevel3WasSuccessful = Database.getInstance().getDAO().addLevel(level3);
        ArrayList<Level> levelsList = Database.getInstance().getDAO().getLevels();
        assertEquals(3, levelsList.size());
    }


    public void testAddAsteroids()
    {
        Database.createInstanceifNull(getContext()).reset();
        RegularAsteroid r = new RegularAsteroid();
        GrowingAsteroid g = new GrowingAsteroid();
        Octaroid o = new Octaroid();
        r.setPosition(new PointF(2, 4));
        r.setType("");
        r.setImage("");
        r.setName("r");
        g.setPosition(new PointF(2, 4));
        g.setType("");
        g.setImage("");
        g.setName("r");
        o.setPosition(new PointF(2, 4));
        o.setType("");
        o.setImage("");
        o.setName("r");
        Database.getInstance().getDAO().addAsteroid(r);
        Database.getInstance().getDAO().addAsteroid(g);
        Database.getInstance().getDAO().addAsteroid(o);

        ArrayList<Asteroid> asteroids = Database.getInstance().getDAO().getAsteroids();
        assertEquals(3, asteroids.size());
    }

    public void testAddLevelAsteroids() {
        //Database.createInstanceifNull(getContext()).reset();
        Database.createInstanceifNull(getContext());
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        RegularAsteroid r = new RegularAsteroid();
        GrowingAsteroid g = new GrowingAsteroid();
        Octaroid o = new Octaroid();
        r.setPosition(new PointF(2, 4));
        r.setType("");
        r.setImage("");
        r.setName("r");
        g.setPosition(new PointF(2, 4));
        g.setType("");
        g.setImage("");
        g.setName("r");
        o.setPosition(new PointF(2, 4));
        o.setType("");
        o.setImage("");
        o.setName("r");

        asteroids.add(r);
        asteroids.add(g);
        asteroids.add(o);

        boolean addWasSuccessful = Database.getInstance().getDAO().addLevelAsteroids(asteroids, 1);
        ArrayList<Asteroid> outputAsteroids = Database.getInstance().getDAO().getLevelAsteroids(1);
        assertTrue(addWasSuccessful);
        assertEquals(3, outputAsteroids.size());
    }

    public void testAddLevelObjects()
    {
        Database.createInstanceifNull(getContext());
        BackgroundObject b1 = new BackgroundObject();
        BackgroundObject b2 = new BackgroundObject();
        BackgroundObject b3 = new BackgroundObject();
        b1.setPosition(new PointF(2, 4));
        b2.setPosition(new PointF(2, 5));
        b3.setPosition(new PointF(2, 6));
        b1.setImage("There");
        b2.setImage("will");
        b3.setImage("be");
        ArrayList<BackgroundObject> objects = new ArrayList<>();
        objects.add(b1);
        objects.add(b2);
        objects.add(b3);
        boolean addWasSuccessful = Database.getInstance().getDAO().addLevelObjects(objects, 1);
        ArrayList<BackgroundObject> goodObjects = Database.getInstance().getDAO().getLevelObjects(1);
        assertEquals(3, goodObjects.size());
        assertTrue(addWasSuccessful);
    }

    public void testAddMainBodies()
    {
        Database.createInstanceifNull(getContext()).reset();
        MainBody m = new MainBody();
        MainBody n = new MainBody();
        m.setCannonAttach(new PointF(2, 3));
        m.setEngineAttach(new PointF(2, 4));
        m.setExtraAttach(new PointF(2, 5));
        n.setCannonAttach(new PointF(2, 6));
        n.setEngineAttach(new PointF(2, 7));
        n.setExtraAttach(new PointF(2, 8));
        m.setImage("m");
        m.setPosition(new PointF(0, 0));
        n.setImage("n");
        n.setPosition(new PointF(0, 0));
        Database.getInstance().getDAO().addMainBody(m);
        Database.getInstance().getDAO().addMainBody(n);
        ArrayList<MainBody> mainBodies = Database.getInstance().getDAO().getMainBodies();
        assertEquals(2, mainBodies.size());
    }

    public void testAddCannons()
    {
        Database.createInstanceifNull(getContext()).reset();
        Cannon c = new Cannon();
        Cannon d = new Cannon();
        c.setAttackImage("attackImage");
        c.setAttackImageHeight(10);
        c.setAttackImageWidth(20);
        c.setAttachPoint(new PointF(2, 2));
        c.setImage("image");
        c.setPosition(new PointF(0, 0));
        c.setDamage(1);
        c.setAttackSound("attackSound");
        c.setEmitPoint(new PointF(0, 0));
        d.setAttackImage("attackImage");
        d.setAttackImageHeight(10);
        d.setAttackImageWidth(20);
        d.setAttachPoint(new PointF(2, 2));
        d.setImage("image");
        d.setPosition(new PointF(0, 0));
        d.setDamage(1);
        d.setAttackSound("attackSound");
        d.setEmitPoint(new PointF(0, 0));
        Database.getInstance().getDAO().addCannon(c);
        Database.getInstance().getDAO().addCannon(d);
        ArrayList<Cannon> cannons = Database.getInstance().getDAO().getCannons();
        assertEquals(2, cannons.size());
    }

    public void testAddEngines()
    {
        Database.getInstance().reset();
        Engine e = new Engine();
        Engine f = new Engine();
        e.setAttachPoint(new PointF(2, 2));
        e.setBaseTurnRate(10);
        e.setBaseSpeed(10);
        e.setImage("image");
        f.setAttachPoint(new PointF(3, 3));
        f.setBaseTurnRate(30);
        f.setBaseSpeed(30);
        f.setImage("image");
        Database.getInstance().getDAO().addEngine(e);
        Database.getInstance().getDAO().addEngine(f);
        ArrayList<Engine> engines = Database.getInstance().getDAO().getEngines();;
        assertEquals(2, engines.size());
    }

    public void testExtraParts()
    {
        Database.getInstance().reset();
        ExtraPart e = new ExtraPart();
        ExtraPart f = new ExtraPart();
        e.setAttachPoint(new PointF(2, 2));
        e.setImage("image");
        e.setImageHeight(10);
        e.setImageWidth(20);
        f.setAttachPoint(new PointF(2, 2));
        f.setImage("image");
        f.setImageHeight(10);
        f.setImageWidth(20);
        Database.getInstance().getDAO().addExtraPart(e);
        Database.getInstance().getDAO().addExtraPart(f);
        ArrayList<ExtraPart> extraParts = Database.getInstance().getDAO().getExtraParts();;
        assertEquals(2, extraParts.size());
    }

    public void testAddPowerCores()
    {
        Database.getInstance().reset();
        PowerCore p = new PowerCore();
        PowerCore q = new PowerCore();
        p.setImage("image");
        p.setEngineBoost(10);
        p.setCannonBoost(20);
        q.setImage("image2");
        q.setEngineBoost(30);
        q.setCannonBoost(40);
        Database.getInstance().getDAO().addPowerCore(p);
        Database.getInstance().getDAO().addPowerCore(q);
        ArrayList<PowerCore> powerCores = Database.getInstance().getDAO().getPowerCores();
        assertEquals(2, powerCores.size());
    }

}

