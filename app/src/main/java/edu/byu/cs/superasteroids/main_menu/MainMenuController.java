package edu.byu.cs.superasteroids.main_menu;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.database.Database;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.PowerCore;

/**
 * Created by Tyler on 7/16/2016.
 */
public class MainMenuController implements IMainMenuController {

    //An instance of IMainMenuView
    private IMainMenuView menuView;


    /**
     * Constructor, takes in IMainMenuView instance
     */
    public MainMenuController(IMainMenuView menuView)
    {
        this.menuView = menuView;
    }

    /**
     * The MainActivity calls this function when the "quick play" button is pressed.
     */
    @Override
    public void onQuickPlayPressed() {
        //Put some ship parts in the Ship
        MainBody m = Database.getInstance().getDAO().getMainBodies().get(1);
        Cannon c = Database.getInstance().getDAO().getCannons().get(1);
        Engine en = Database.getInstance().getDAO().getEngines().get(1);
        ExtraPart ex = Database.getInstance().getDAO().getExtraParts().get(1);
        PowerCore p = Database.getInstance().getDAO().getPowerCores().get(1);
        //Put them in the ship
        Database.getInstance().getShip().setMainBody(m);
        Database.getInstance().getShip().setCannon(c);
        Database.getInstance().getShip().setEngine(en);
        Database.getInstance().getShip().setExtraPart(ex);
        Database.getInstance().getShip().setPowerCore(p);
        //Set the main body reference for the other parts of the ship
        Database.getInstance().getShip().getCannon().setMainBodyRef(Database.getInstance().getShip().getMainBody());
        Database.getInstance().getShip().getEngine().setMainBodyRef(Database.getInstance().getShip().getMainBody());
        Database.getInstance().getShip().getExtraPart().setMainBodyRef(Database.getInstance().getShip().getMainBody());
        //Start the game
        menuView.startGame();
    }

    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {

        return menuView;
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
