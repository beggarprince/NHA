package entities.Enemy;
import graphics.ScreenSettings;
import graphics.imgLoader;
import util.Coordinate;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Slime extends Enemy {
    private BufferedImage slimeImage = imgLoader.getImage("slime.png"); //Default slime preloaded
    private int lifespan = 30; // Slime will reach maturity at 30 seconds in which it either reproduces or dies
    private final int movementSpeed = 4;

    public Slime(Coordinate position) {
        super(1, position); // Slime has a default health of 1
        this.worldPosX = position.x / ScreenSettings.TILE_SIZE;
        this.worldPosY = position.y / ScreenSettings.TILE_SIZE;
        this.screenPosX = position.x;
        this.screenPosY = position.y;
        this.dir =Direction.RIGHT;// getRandomValidDirection(worldPosX, worldPosY);//This will give it a random starting dir that is valid

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
        //System.out.println(screenPosX + ":" + screenPosY);
       // System.out.println(worldPosX+ ":" +worldPosY);

        //We see if we can move this direction
        if(validateDirection(dir, worldPosX, worldPosY)){
            move(movementSpeed);
            updateWorldPosition();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            dir = getRandomValidDirection(worldPosX, worldPosY);
        }
    }




}
