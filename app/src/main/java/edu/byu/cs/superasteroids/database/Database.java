package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.Scanner;

import edu.byu.cs.superasteroids.model.AsteroidsModelContainer;
import edu.byu.cs.superasteroids.model.Ship;

/**
 * Created by Tyler on 7/14/2016.
 */
public class Database {
    //This is a singleton

    //The instance
    private static Database instance;
    //The DAO for the game
    private AsteroidsDAO dao;
    //The Database
    private SQLiteDatabase db;
    //The dbopenhelper
    private DbOpenHelper dbOpenHelper;
    //The context for the app
    private Context appContext;
    //The model container
    private AsteroidsModelContainer modelContainer;
    //The ship
    private Ship ship;

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public AsteroidsModelContainer getModelContainer() {
        return modelContainer;
    }

    public AsteroidsDAO getDAO()
    {
        return dao;
    }


    //Private contructor for singleton
    private Database(Context context){
        appContext = context;
        dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        dao = new AsteroidsDAO(db);
        modelContainer = new AsteroidsModelContainer();
        ship = new Ship();
    }

    //Returns the instance if it is not null, creates and returns it if it is
    public static Database createInstanceifNull(Context context){
        if (instance == null)
        {
            instance = new Database(context);
        }
        return instance;
    }

    public static Database getInstance()
    {
        return instance;
    }

    public void startTransaction()
    {
        db.beginTransaction();
    }

    public void endTransaction (boolean commit){
        if(commit)
            db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     *Completely deletes the database and builds a new one
     */
    public void reset()
    {
        String drop1 = "DROP TABLE IF EXISTS objects";
        db.execSQL(drop1);
        String drop2 = "DROP TABLE IF EXISTS asteroidTypes";
        db.execSQL(drop2);
        String drop3 = "DROP TABLE IF EXISTS levels";
        db.execSQL(drop3);
        String drop4 = "DROP TABLE IF EXISTS levelObjects";
        db.execSQL(drop4);
        String drop5 = "DROP TABLE IF EXISTS levelAsteroids";
        db.execSQL(drop5);
        String drop6 = "DROP TABLE IF EXISTS mainBodies";
        db.execSQL(drop6);
        String drop7 = "DROP TABLE IF EXISTS cannons";
        db.execSQL(drop7);
        String drop8 = "DROP TABLE IF EXISTS extraParts";
        db.execSQL(drop8);
        String drop9 = "DROP TABLE IF EXISTS engines";
        db.execSQL(drop9);
        String drop10 = "DROP TABLE IF EXISTS powerCores";
        db.execSQL(drop10);
        String asteroidTypesSQL = "CREATE TABLE asteroidTypes" +
                "(" +
                "   ASTEROID_ID INTEGER PRIMARY KEY," +
                "   NAME TEXT NOT NULL," +
                "   IMAGE TEXT NOT NULL," +
                "   IMAGE_WIDTH INTEGER NOT NULL," +
                "   IMAGE_HEIGHT INTEGER NOT NULL," +
                "   TYPE TEXT NOT NULL" +
                ")";
        db.execSQL(asteroidTypesSQL);

        String cannonsSQL = "CREATE TABLE cannons" +
                "( " +
                "   ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "   ATTACH_POINT TEXT NOT NULL, " +
                "   EMIT_POINT TEXT NOT NULL, " +
                "   IMAGE TEXT NOT NULL, " +
                "   IMAGE_WIDTH INTEGER NOT NULL, " +
                "   IMAGE_HEIGHT INTEGER NOT NULL, " +
                "   ATTACK_IMAGE TEXT NOT NULL," +
                "   ATTACK_IMAGE_WIDTH INTEGER NOT NULL, " +
                "   ATTACK_IMAGE_HEIGHT INTEGER NOT NULL, " +
                "   ATTACK_SOUND TEXT NOT NULL, " +
                "   DAMAGE INTEGER NOT NULL " +
                ")";
        db.execSQL(cannonsSQL);

        String enginesSQL = "CREATE TABLE engines(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tBASE_SPEED INTEGER NOT NULL,\n" +
                "\tBASE_TURN_RATE INTEGER NOT NULL,\n" +
                "\tATTACH_POINT TEXT NOT NULL,\n" +
                "\tIMAGE TEXT NOT NULL,\n" +
                "\tIMAGE_WIDTH INTEGER NOT NULL,\n" +
                "\tIMAGE_HEIGHT INTEGER NOT NULL\n" +
                ")";
        db.execSQL(enginesSQL);

        String extraPartsSQL = "CREATE TABLE extraParts(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tATTACH_POINT TEXT NOT NULL,\n" +
                "\tIMAGE TEXT NOT NULL,\n" +
                "\tIMAGE_WIDTH INTEGER NOT NULL,\n" +
                "\tIMAGE_HEIGHT INTEGER NOT NULL\n" +
                ")";
        db.execSQL(extraPartsSQL);

        String levelAsteroidsSQL = "CREATE TABLE levelAsteroids(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tNUMBER INTEGER NOT NULL,\n" +
                "\tASTEROID_ID INTEGER NOT NULL,\n" +
                "\tLEVEL INTEGER NOT NULL\n" +
                ")";
        db.execSQL(levelAsteroidsSQL);

        String levelObjectsSQL = "CREATE TABLE levelObjects(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tPOSITION INTEGER NOT NULL,\n" +
                "\tOBJECT_ID INTEGER NOT NULL,\n" +
                "\tSCALE REAL NOT NULL,\n" +
                "\tLEVEL INTEGER NOT NULL\n" +
                ")";
        db.execSQL(levelObjectsSQL);

        String levelsSQL = "CREATE TABLE levels(\n" +
                "\tNUMBER INTEGER PRIMARY KEY NOT NULL,\n" +
                "\tTITLE TEXT NOT NULL,\n" +
                "\tHINT TEXT,\n" +
                "\tWIDTH INTEGER NOT NULL,\n" +
                "\tHEIGHT INTEGER NOT NULL,\n" +
                "\tMUSIC TEXT NOT NULL\n" +
                ")";
        db.execSQL(levelsSQL);

        String mainBodiesSQL = "CREATE TABLE mainBodies(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tCANNON_ATTACH TEXT NOT NULL,\n" +
                "\tENGINE_ATTACH TEXT NOT NULL,\n" +
                "\tEXTRA_ATTACH TEXT NOT NULL,\n" +
                "\tIMAGE TEXT NOT NULL,\n" +
                "\tIMAGE_WIDTH INTEGER NOT NULL,\n" +
                "\tIMAGE_HEIGHT INTEGER NOT NULL\n" +
                ")";
        db.execSQL(mainBodiesSQL);

        String objectsSQL = "CREATE TABLE objects(\n" +
                "\tOBJECT_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tOBJECT TEXT NOT NULL\n" +
                ")";
        db.execSQL(objectsSQL);

        String powerCoresSQL = "CREATE TABLE powerCores(\n" +
                "\tID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "\tCANNON_BOOST INTEGER NOT NULL,\n" +
                "\tENGINE_BOOST INTEGER NOT NULL,\n" +
                "\tIMAGE TEXT NOT NULL\n" +
                ")";
        db.execSQL(powerCoresSQL);
    }

}
