package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.InputStreamReader;

import edu.byu.cs.superasteroids.importer.GameDataImporter;

/**
 * Created by Tyler on 7/14/2016.
 */
public class DbOpenHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public DbOpenHelper(Context context) {
        super(context, "SQLite_Database", null, DB_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREATE THE TABLES IN THE DATABASE
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

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
