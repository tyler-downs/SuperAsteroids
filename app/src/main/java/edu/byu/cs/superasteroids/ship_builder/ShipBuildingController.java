package edu.byu.cs.superasteroids.ship_builder;

import android.content.ContentValues;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;
import edu.byu.cs.superasteroids.model.Ship;

/**
 * Created by Tyler on 7/16/2016.
 */
public class ShipBuildingController implements IShipBuildingController {

    //An instance of ship building activity so I can use the functions
    private ShipBuildingActivity sba;


    //Bools for the parts being selected
    private boolean mainBodyIsSelected = false;
    private boolean cannonIsSelected = false;
    private boolean engineIsSelected = false;
    private boolean extraPartIsSelected = false;
    private boolean powerCoreIsSelected = false;

    //the IDs of the parts that were selected
    private int mainBodySelectedID;
    private int cannonSelectedID;
    private int engineSelectedID;
    private int extraPartSelectedID;
    private int powerCoreSelectedID;

    //The current ship building part selection view, set initially to mainbody
    private IShipBuildingView.PartSelectionView currentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;

    //The five array lists of image path strings
    private ArrayList<String> mainBodyImages = new ArrayList<>();
    private ArrayList<String> cannonImages = new ArrayList<>();
    private ArrayList<String> engineImages = new ArrayList<>();
    private ArrayList<String> extraPartImages = new ArrayList<>();
    private ArrayList<String> powerCoreImages = new ArrayList<>();

    //The five array lists of image ids for the parts
    private ArrayList<Integer> mainBodyIds = new ArrayList<>();
    private ArrayList<Integer> cannonIds = new ArrayList<>();
    private ArrayList<Integer> engineIds = new ArrayList<>();
    private ArrayList<Integer> extraPartIds = new ArrayList<>();
    private ArrayList<Integer> powerCoreIds = new ArrayList<>();



    /**
     * Constructor
     */
    public ShipBuildingController(ShipBuildingActivity sba)
    {
        this.sba = sba;
        //Populate the model container
        Database.getInstance().getModelContainer().setAsteroids(Database.getInstance().getDAO().getAsteroids());
        Database.getInstance().getModelContainer().setBackgroundObjects(Database.getInstance().getDAO().getBackgroundObjects());
        Database.getInstance().getModelContainer().setCannons(Database.getInstance().getDAO().getCannons());
        Database.getInstance().getModelContainer().setEngines(Database.getInstance().getDAO().getEngines());
        Database.getInstance().getModelContainer().setExtraParts(Database.getInstance().getDAO().getExtraParts());
        Database.getInstance().getModelContainer().setLevels(Database.getInstance().getDAO().getLevels());
        Database.getInstance().getModelContainer().setMainBodies(Database.getInstance().getDAO().getMainBodies());
        Database.getInstance().getModelContainer().setPowerCores(Database.getInstance().getDAO().getPowerCores());
    }

    /**
     * The ship building view calls this function when a part selection view is loaded. This function
     * should be used to configure the part selection view. Example: Set the arrows for the view in
     * this function.
     *
     * @param partView
     */
    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        switch(partView){
            case MAIN_BODY:
                sba.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.UP, true, "Power Core");
                sba.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.DOWN, true, "Engine");
                sba.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.LEFT, true, "Extra Part");
                sba.setArrow(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.RIGHT, true, "Cannon");
                sba.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, mainBodyIds);
                break;
            case ENGINE:
                sba.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.UP, true, "Main Body");
                sba.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.DOWN, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.LEFT, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.RIGHT, false, "");
                sba.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, engineIds);
                break;
            case CANNON:
                sba.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.UP, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.DOWN, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.LEFT, true, "Main Body");
                sba.setArrow(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.RIGHT, false, "");
                sba.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, cannonIds);
                break;
            case EXTRA_PART:
                sba.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.UP, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.DOWN, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.LEFT, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.RIGHT, true, "Main Body");
                sba.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, extraPartIds);
                break;
            case POWER_CORE:
                sba.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.UP, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.DOWN, true, "Main Body");
                sba.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.LEFT, false, "");
                sba.setArrow(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.RIGHT, false, "");
                sba.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, powerCoreIds);
                break;

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
        //leave this function empty
    }

    //Adds the images of the ship parts from the database to the lists for use in this class
    private void getImagesFromDatabase()
    {
        ArrayList<MainBody> databaseMainBodies = Database.getInstance().getDAO().getMainBodies();
        for (MainBody m : databaseMainBodies)
        {
            String image = m.getImage();
            mainBodyImages.add(image);
        }
        ArrayList<Cannon> databaseCannons = Database.getInstance().getDAO().getCannons();
        for (Cannon c : databaseCannons)
        {
            String image = c.getImage();
            cannonImages.add(image);
        }
        ArrayList<Engine> databaseEngines = Database.getInstance().getDAO().getEngines();
        for (Engine e : databaseEngines)
        {
            String image = e.getImage();
            engineImages.add(image);
        }
        ArrayList<ExtraPart> databaseExtraParts = Database.getInstance().getDAO().getExtraParts();
        for (ExtraPart e : databaseExtraParts)
        {
            String image = e.getImage();
            extraPartImages.add(image);
        }
        ArrayList<PowerCore> databasePowerCores = Database.getInstance().getDAO().getPowerCores();
        for (PowerCore p : databasePowerCores)
        {
            String image = p.getImage();
            powerCoreImages.add(image);
        }
    }

    //Bool to indicate if all ship parts have been chosen
    private boolean allPartsPicked()
    {
        if (mainBodyIsSelected && cannonIsSelected && engineIsSelected && extraPartIsSelected && powerCoreIsSelected)
        {
            return true;
        }
        else return false;
    }

    /**
     * The ShipBuildingView calls this function as it is created. Load ship building content in this function.
     *
     * @param content An instance of the content manager. This should be used to load images and sound.
     */
    @Override
    public void loadContent(ContentManager content) {

        //Clear old images
        mainBodyImages.clear();
        cannonImages.clear();
        engineImages.clear();
        extraPartImages.clear();
        powerCoreImages.clear();

        //use helper function to load the images into the lists
        getImagesFromDatabase();

        //Clear any old data out of these lists
        mainBodyIds.clear();
        cannonIds.clear();
        engineIds.clear();
        extraPartIds.clear();
        powerCoreIds.clear();


        mainBodyIds.addAll(content.loadImages(mainBodyImages));
        cannonIds.addAll(content.loadImages(cannonImages));
        engineIds.addAll(content.loadImages(engineImages));
        extraPartIds.addAll(content.loadImages(extraPartImages));
        powerCoreIds.addAll(content.loadImages(powerCoreImages));

        sba.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, mainBodyIds);
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
        //Unload the images in content associated with all of the IDs in all the lists
        for (Integer i : mainBodyIds)
        {
            content.unloadImage(i);
        }
        for (Integer i : cannonIds)
        {
            content.unloadImage(i);
        }
        for (Integer i : engineIds)
        {
            content.unloadImage(i);
        }
        for (Integer i : extraPartIds)
        {
            content.unloadImage(i);
        }
        for (Integer i : powerCoreIds)
        {
            content.unloadImage(i);
        }

    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
    @Override
    public void draw() {

        float scale = (float).25;
        //draw the ship in the lower box
        //this should only draw the parts that have been selected
        float mainBodyXcoord = DrawingHelper.getGameViewWidth()/2;
        float mainBodyYcoord = DrawingHelper.getGameViewHeight()/2;
        PointF mainBodyLocation = new PointF(mainBodyXcoord, mainBodyYcoord);
        PointF mainBodyCenter = null;
        PointF mainBodyCannonAttachPoint = null;
        PointF mainBodyEngineAttachPoint = null;
        PointF mainBodyExtraAttachPoint = null;

        //draw the other parts with positions relative to the position of main body
        if (mainBodyIsSelected)
        {
            float mainBodyCenterXcoord = Database.getInstance().getShip().getMainBody().getImageWidth()/2;
            float mainBodyCenterYcoord = Database.getInstance().getShip().getMainBody().getImageHeight()/2;
            mainBodyCenter = GraphicsUtils.scale(new PointF(mainBodyCenterXcoord, mainBodyCenterYcoord), scale);
            //draw main body
            DrawingHelper.drawImage(mainBodySelectedID, mainBodyXcoord, mainBodyYcoord, 0, scale, scale, 255);
            //Scale the attach points down to the same scale as the ship parts
            mainBodyCannonAttachPoint = GraphicsUtils.scale(Database.getInstance().getShip().getMainBody().getCannonAttach(), scale);
            mainBodyEngineAttachPoint = GraphicsUtils.scale(Database.getInstance().getShip().getMainBody().getEngineAttach(), scale);
            mainBodyExtraAttachPoint = GraphicsUtils.scale(Database.getInstance().getShip().getMainBody().getExtraAttach(), scale);
        }
        if (cannonIsSelected)
        {
            //Scale the attach points down at the same factor as the parts
            PointF cannonAttachPoint = GraphicsUtils.scale(Database.getInstance().getShip().getCannon().getAttachPoint(), scale);
            float cannonCenterXcoord = Database.getInstance().getShip().getCannon().getImageWidth()/2;
            float cannonCenterYcoord = Database.getInstance().getShip().getCannon().getImageHeight()/2;
            PointF cannonCenter = GraphicsUtils.scale(new PointF(cannonCenterXcoord, cannonCenterYcoord), scale);
            PointF partOffset = GraphicsUtils.add(GraphicsUtils.subtract(mainBodyCannonAttachPoint, mainBodyCenter),
                                GraphicsUtils.subtract(cannonCenter, cannonAttachPoint));
            PointF cannonLocation = GraphicsUtils.add(mainBodyLocation, partOffset);

            DrawingHelper.drawImage(cannonSelectedID, cannonLocation.x, cannonLocation.y, 0, scale, scale, 255);
        }
        if (engineIsSelected)
        {
            //draw engine
            PointF engineAttachPoint = GraphicsUtils.scale(Database.getInstance().getShip().getEngine().getAttachPoint(), scale);
            float engineCenterXcoord = Database.getInstance().getShip().getEngine().getImageWidth()/2;
            float engineCenterYcoord = Database.getInstance().getShip().getEngine().getImageHeight()/2;
            PointF engineCenter = GraphicsUtils.scale(new PointF(engineCenterXcoord, engineCenterYcoord), scale);
            //Calculate the part offset with these scaled values
            PointF partOffset =  GraphicsUtils.add(GraphicsUtils.subtract(mainBodyEngineAttachPoint, mainBodyCenter),
                                GraphicsUtils.subtract(engineCenter, engineAttachPoint));
            //Calculate location of engine image
            PointF engineLocation = GraphicsUtils.add(mainBodyLocation, partOffset);
            DrawingHelper.drawImage(engineSelectedID, engineLocation.x, engineLocation.y, 0, scale, scale, 255);
        }
        if (extraPartIsSelected)
        {
            //draw extra part
            PointF extraAttachPoint = GraphicsUtils.scale(Database.getInstance().getShip().getExtraPart().getAttachPoint(), scale);
            float engineCenterXcoord = Database.getInstance().getShip().getExtraPart().getImageWidth()/2;
            float engineCenterYcoord = Database.getInstance().getShip().getExtraPart().getImageHeight()/2;
            PointF extraPartCenter = GraphicsUtils.scale(new PointF(engineCenterXcoord, engineCenterYcoord), scale);
            PointF partOffset =  GraphicsUtils.add(GraphicsUtils.subtract(mainBodyExtraAttachPoint, mainBodyCenter),
                                GraphicsUtils.subtract(extraPartCenter, extraAttachPoint));
            PointF extraPartLocation = GraphicsUtils.add(mainBodyLocation, partOffset);
            DrawingHelper.drawImage(extraPartSelectedID, extraPartLocation.x, extraPartLocation.y, 0, scale, scale, 255);
        }
        //you dont actually draw the power core
    }

    /**
     * The ShipBuildingView calls this function when the user makes a swipe/fling motion in the
     * screen. Respond to the user's swipe/fling motion in this function.
     *
     * @param direction The direction of the swipe/fling.
     */
    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        //if the direction is right, and to the right you have cannons, go
        //switch case of currentEnumView
            //inner switch case of direction
                //for each case (up, left, right, down) if it is valid, change the currentEnumView to the one you are going to
                //Then call sba.animateToView
        switch (currentPartView){
            case MAIN_BODY:
                switch(direction){
                    case UP:
                        currentPartView = IShipBuildingView.PartSelectionView.ENGINE;
                        sba.animateToView(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.DOWN);
                        break;
                    case RIGHT:
                        currentPartView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                        sba.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case DOWN:
                        currentPartView = IShipBuildingView.PartSelectionView.POWER_CORE;
                        sba.animateToView(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.UP);
                        break;
                    case LEFT:
                        currentPartView = IShipBuildingView.PartSelectionView.CANNON;
                        sba.animateToView(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case CANNON:
                switch(direction){
                    case UP:
                        break;
                    case RIGHT:
                        currentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        sba.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        break;
                }
                break;
            case ENGINE:
                switch(direction){
                    case UP:
                        break;
                    case RIGHT:
                        break;
                    case DOWN:
                        currentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        sba.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.UP);
                        break;
                    case LEFT:
                        break;
                }
                break;
            case EXTRA_PART:
                switch(direction){
                    case UP:
                        break;
                    case RIGHT:
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        currentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        sba.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case POWER_CORE:
                switch(direction){
                    case UP:
                        currentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        sba.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.DOWN);
                        break;
                    case RIGHT:
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        break;
                }
                break;
        }
    }

    /**
     * The part selection fragments call this function when a part is selected from the parts list. Respond
     * to the part selection in this function.
     *
     * @param index The list index of the selected part.
     */
    @Override
    public void onPartSelected(int index) {
        if (!mainBodyIsSelected && currentPartView != IShipBuildingView.PartSelectionView.MAIN_BODY)
        {
            //do nothing
        }
        else
        {
            /*
            The draw function needs to know exactly what parts to draw.
            This tells the program that one of the parts has been selected, but not which
             */
            switch(currentPartView){
                case MAIN_BODY:
                    mainBodyIsSelected = true;
                    mainBodySelectedID = mainBodyIds.get(index);
                    //Set the main body of the ship to the main body at row *index* of the table of main bodies in the database
                    Database.getInstance().getShip().setMainBody(Database.getInstance().getDAO().getMainBodies().get(index));
                    break;
                case CANNON:
                    cannonIsSelected = true;
                    cannonSelectedID = cannonIds.get(index);
                    Database.getInstance().getShip().setCannon(Database.getInstance().getDAO().getCannons().get(index));
                    //Set the main body reference for the other parts of the ship
                    Database.getInstance().getShip().getCannon().setMainBodyRef(Database.getInstance().getShip().getMainBody());
                    break;
                case ENGINE:
                    engineIsSelected = true;
                    engineSelectedID = engineIds.get(index);
                    Database.getInstance().getShip().setEngine(Database.getInstance().getDAO().getEngines().get(index));
                    //Set the main body reference for the other parts of the ship
                    Database.getInstance().getShip().getEngine().setMainBodyRef(Database.getInstance().getShip().getMainBody());
                    break;
                case EXTRA_PART:
                    extraPartIsSelected = true;
                    extraPartSelectedID = extraPartIds.get(index);
                    Database.getInstance().getShip().setExtraPart(Database.getInstance().getDAO().getExtraParts().get(index));
                    //Set the main body reference for the other parts of the ship
                    Database.getInstance().getShip().getExtraPart().setMainBodyRef(Database.getInstance().getShip().getMainBody());
                    break;
                case POWER_CORE:
                    powerCoreIsSelected = true;
                    powerCoreSelectedID = powerCoreIds.get(index);
                    Database.getInstance().getShip().setPowerCore(Database.getInstance().getDAO().getPowerCores().get(index));
                    break;
            }
            if (allPartsPicked())
            {
                sba.setStartGameButton(true);
            }
        }
    }

    /**
     * The ShipBuildingView calls this function is called when the start game button is pressed.
     */
    @Override
    public void onStartGamePressed() {
        //Set these back to false so the ship builder can be used again upon press of the back button
        mainBodyIsSelected = false;
        cannonIsSelected = false;
        engineIsSelected = false;
        extraPartIsSelected = false;
        powerCoreIsSelected = false;
        //Start the game
        sba.startGame();
    }

    /**
     * The ShipBuildingView calls this function when ship building has resumed. Reset the Camera and
     * the ship position as needed when this is called.
     */
    @Override
    public void onResume() {
        //leave this empty too
    }

    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {
        return null;
    }

    /**
     * Sets the controller's view
     *
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {

    }
}
