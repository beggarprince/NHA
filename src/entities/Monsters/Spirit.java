package entities.Monsters;
import graphics.ScreenSettings;
import graphics.ImgLoader;
import java.awt.image.BufferedImage;

public class Spirit extends Monster {

    public Spirit(int x, int y) {
        super(1, x, y); // Slime has a default health of 1
        this.image = ImgLoader.getImageResource("spirit.png");
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.currDirection = enemyGetRandomDirection(worldPositionX, worldPositionY);//This will give it a random starting dir that is valid
        this.lifespan = 3;
        this.movementSpeed = 1;
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = ImgLoader.getImageResource("spirit.png");
    }

    @Override
    protected void destroy() {
        this.image = null;
    }

    // Getter for the image if needed
    public BufferedImage getImage() {
        return image;
    }

    public void behavior(){

        //We see if we can move this direction
        if(validateWalkableDirection(currDirection, worldPositionX, worldPositionY)){
            move(movementSpeed);
            updateWorldPosition();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            currDirection = enemyGetRandomDirection(worldPositionX, worldPositionY);
        }
        agingCycle();
    }

    protected void eat(){

    }

    @Override
    protected void agingCycle() {

    }

    protected void reproductionCycle(){

    }



}
