package entities.Enemy;
import graphics.ScreenSettings;
import graphics.imgLoader;
import java.awt.image.BufferedImage;

public class Spirit extends Enemy {
    private BufferedImage slimeImage = imgLoader.getImageResource("spirit.png"); //Default slime preloaded
    private int lifespan = 3; // Slime will reach maturity at 30 seconds in which it either reproduces or dies
    private final int movementSpeed = 4;

    public Spirit(int x, int y) {
        super(1, x, y); // Slime has a default health of 1
        this.worldPosX = x / ScreenSettings.TILE_SIZE;
        this.worldPosY =y / ScreenSettings.TILE_SIZE;
        this.screenPosX = x;
        this.screenPosY = y;
        this.dir = getRandomValidDirection(worldPosX, worldPosY);//This will give it a random starting dir that is valid

    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        slimeImage = imgLoader.getImageResource("spirit.png");
    }

    // Getter for the image if needed
    public BufferedImage getImage() {
        return slimeImage;
    }

    public void behavior(){

        //We see if we can move this direction
        if(validateWalkableDirection(dir, worldPosX, worldPosY)){
            move(movementSpeed);
            updateWorldPosition();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            dir = getRandomValidDirection(worldPosX, worldPosY);
        }
        agingCycle();
    }

    protected void eat(){

    }

    protected void reproductionCycle(){

    }



}