package entities.Enemy;
import graphics.ScreenSettings;
import graphics.TileType;
import graphics.imgLoader;
import java.awt.image.BufferedImage;

public class Slime extends Enemy {
    private BufferedImage slimeImage = imgLoader.getImage("slime.png"); //Default slime preloaded
    private int lifespan = 3; // Slime will reach maturity at 30 seconds in which it either reproduces or dies
    private final int movementSpeed = 4;
    private final int maxHunger  = 1;

    public Slime(int x, int y) {
        super(1, x, y); // Slime has a default health of 1
        this.worldPosX = x / ScreenSettings.TILE_SIZE;
        this.worldPosY =y / ScreenSettings.TILE_SIZE;
        this.screenPosX = x;
        this.screenPosY = y;
        this.dir = getRandomValidDirection(worldPosX, worldPosY);//This will give it a random starting dir that is valid
        this.hunger = 0;
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        slimeImage = imgLoader.getImage("slime.png");
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
        //Eat
        if(hunger != maxHunger)eat();
        else poop();
        agingCycle();
    }

    protected void eat(){
       if(eatSurroundingTile(TileType.GRASS)) hunger++;
    }
    protected void poop(){
        if(depositSurroundingTile(TileType.GRASS)) hunger--;
    }

    protected void reproductionCycle(){

    }



}
