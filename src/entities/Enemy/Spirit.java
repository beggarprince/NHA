package entities.Enemy;
import graphics.ScreenSettings;
import graphics.imgLoader;
import java.awt.image.BufferedImage;

public class Spirit extends Enemy {

    public Spirit(int x, int y) {
        super(1, x, y); // Slime has a default health of 1
        this.image = imgLoader.getImageResource("spirit.png");
        this.enemyWorldPositionX = x / ScreenSettings.TILE_SIZE;
        this.enemyWorldPositionY =y / ScreenSettings.TILE_SIZE;
        this.enemyScreenPositionX = x;
        this.enemyScreenPositionY = y;
        this.enemyCurrentDirection = enemyGetRandomDirection(enemyWorldPositionX, enemyWorldPositionY);//This will give it a random starting dir that is valid
        this.enemyLifespan = 3;
        this.enemyMovementSpeed = 1;

    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = imgLoader.getImageResource("spirit.png");
    }

    // Getter for the image if needed
    public BufferedImage getImage() {
        return image;
    }

    public void behavior(){

        //We see if we can move this direction
        if(validateWalkableDirection(enemyCurrentDirection, enemyWorldPositionX, enemyWorldPositionY)){
            move(enemyMovementSpeed);
            updateWorldPosition();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            enemyCurrentDirection = enemyGetRandomDirection(enemyWorldPositionX, enemyWorldPositionY);
        }
        agingCycle();
    }

    protected void eat(){

    }

    protected void reproductionCycle(){

    }



}
