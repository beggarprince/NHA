package entities.Enemy;
import graphics.ScreenSettings;
import graphics.TileType;
import graphics.ImgLoader;
import java.awt.image.BufferedImage;

public class Slime extends Enemy {

    public Slime(int x, int y) {
        super(1, x, y); // Slime has a default health of 1
        this.enemyWorldPositionX = x / ScreenSettings.TILE_SIZE;
        this.enemyWorldPositionY =y / ScreenSettings.TILE_SIZE;
        this.enemyScreenPositionX = x;
        this.enemyScreenPositionY = y;

        this.enemyCurrentDirection = enemyGetRandomDirection(enemyWorldPositionX, enemyWorldPositionY);//This will give it a random starting dir that is valid
        this.enemyHunger = 0;
        this.enemyMovementSpeed = 1;
        this.image = ImgLoader.getImageResource("slime.png"); //Default slime preloaded
        this.enemyLifespan = 300;
        this.enemyHasFullStomach = false;
        this.enemyMaxHunger =1;

        this.enemyMetamorphosis = "Slime_Flower";
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = ImgLoader.getImageResource("slime.png");
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
            resetMovementCycle();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            enemyCurrentDirection = enemyGetRandomDirection(enemyWorldPositionX, enemyWorldPositionY);
        }
        //Eat
        if(enemyEatingCycleReady) {
            if (enemyHunger < enemyMaxHunger) eat();
            else poop();
        }
        //TODO make this abstract
        agingCycle();
    }

    protected void eat(){
       if(eatSurroundingTile(TileType.NUTRIENT)) {
           //System.out.println("ate");

           enemyHunger++;
        //   if(enemyHunger == enemyMaxHunger) System.out.println("Ready to poop");
           enemyEatingCycleReady = false; //This can only be set true by moving to a new tile
       }
    }

    @Override
    protected void agingCycle() {
        enemyLifespan--;
        if(enemyLifespan == 0){
         //There needs to be code  here to determine whether the slime reproduces or just dies
            enemyMetamorphosisIsReady = true;
        }
    }

    protected void poop(){
        if(depositSurroundingTile(TileType.NUTRIENT)) {
            //System.out.println("shat");
            enemyHunger--;
          //  if(enemyHunger == 0) System.out.println("Ready to eat");
            enemyEatingCycleReady = false;
        }
    }

    //To be implemented
    protected void reproductionCycle(){

    }



}
