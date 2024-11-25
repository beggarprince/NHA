package entities.Monsters;

import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;

public class Bug extends Monster {

    public Bug( int x, int y) {
        super(12, x, y);

            setImage();
            this.currDirection = getRandomDirection(worldPositionX, worldPositionY);//This will give it a random starting dir that is valid
            this.hunger = 0;

            this.image = ImgLoader.getImageResource("bug.png"); //Default slime preloaded
            this.movementSpeed = 1;
            this.lifespan = ScreenSettings.FPS * 45;
            this.hasFullStomach = false;
            this.maxHunger = 1;


    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("bug.png");
    }

    @Override
    protected void destroy() {
        this.image = null;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void behavior() {

        boolean canMove = true;
        if(movementCycle == 0)  canMove = validateWalkableDirection(currDirection, worldPositionX, worldPositionY);

        if(canMove){
            move(movementSpeed);
            updateWorldPosition();
            signalNewTile();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            currDirection = getRandomDirection(worldPositionX, worldPositionY);
        }
    }

    @Override
    protected void eat() {

    }

    @Override
    protected void agingCycle() {

    }

    @Override
    protected void reproductionCycle() {

    }
}
