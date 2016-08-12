package edu.byu.cs.superasteroids.model;

/**
 * Created by Tyler on 7/12/2016.
 */
public class Ship extends MovingObject {

    //The main body of the ship
    private MainBody mainBody;
    //The cannon of the ship
    private Cannon cannon;
    //The extra part of the ship
    private ExtraPart extraPart;
    //The engine of the ship
    private Engine engine;
    //The power core of the ship
    private PowerCore powerCore;


    //Getters and setters
    public MainBody getMainBody() {
        return mainBody;
    }

    public void setMainBody(MainBody mainBody) {
        this.mainBody = mainBody;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public ExtraPart getExtraPart() {
        return extraPart;
    }

    public void setExtraPart(ExtraPart extraPart) {
        this.extraPart = extraPart;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public PowerCore getPowerCore() {
        return powerCore;
    }

    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }

    /**
     * Updates the object, handling movement and collision detection
     */
    @Override
    public void update(double elapsedTime) {
        mainBody.update(elapsedTime);
        cannon.update(elapsedTime);
        engine.update(elapsedTime);
        extraPart.update(elapsedTime);
    }

    /**
     * Draws the image associated with this PositionedObject
     */
    @Override
    public void drawImage() {
        mainBody.drawImage();
        cannon.drawImage();
        engine.drawImage();
        extraPart.drawImage();
    }

    //Useful toString method for seeing what is in the ship at any given time
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        if (mainBody == null)
        {
            s.append("MainBody == null, ");
        }
        else
        {
            s.append("MainBody == " + mainBody.getImage()+ ", ");
        }
        if (cannon == null)
        {
            s.append("Cannon == null, ");
        }
        else
        {
            s.append("Cannon == " + cannon.getImage()+ ", ");
        }
        if (engine == null)
        {
            s.append("Engine == null, ");
        }
        else
        {
            s.append("Engine == " + engine.getImage()+ ", ");
        }
        if (extraPart == null)
        {
            s.append("ExtraPart == null, ");
        }
        else
        {
            s.append("ExtraPart == " + extraPart.getImage()+ ", ");
        }
        if (powerCore == null)
        {
            s.append("PowerCore == null");
        }
        else
        {
            s.append("PowerCore == " + powerCore.getImage());
        }
        return s.toString();

    }
}
