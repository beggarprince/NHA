package entities.Monsters;
import graphics.ScreenSettings;
import graphics.TileType;
import graphics.ImgLoader;
import java.awt.image.BufferedImage;

public class Slime extends Monster {

    public Slime(int x, int y) {
        super(1, x, y); // Slime has a default health of 1
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;

        this.currDirection = enemyGetRandomDirection(worldPositionX, worldPositionY);//This will give it a random starting dir that is valid
        this.hunger = 0;
        this.movementSpeed = 1;
        this.image = ImgLoader.getImageResource("slime.png"); //Default slime preloaded
        this.lifespan = ScreenSettings.FPS * 45;
        this.hasFullStomach = false;
        this.maxHunger = 1;

        this.metamorphosisValue = "Slime_Flower";
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = ImgLoader.getImageResource("slime.png");
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
            resetMovementCycle();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            currDirection = enemyGetRandomDirection(worldPositionX, worldPositionY);
        }
        //Eat
        if(eatingCycleReady) {
            if (hunger < maxHunger) eat();
            else poop();
        }

        agingCycle();
    }

    protected void eat(){
       if(eatSurroundingTile(TileType.NUTRIENT)) {
           //System.out.println("ate");

           hunger++;
        //   if(enemyHunger == enemyMaxHunger) System.out.println("Ready to poop");
           eatingCycleReady = false; //This can only be set true by moving to a new tile
       }
    }

    @Override
    protected void agingCycle() {
        lifespan--;
        if(lifespan == 0){
         //There needs to be code  here to determine whether the slime reproduces or just dies
            metamorphosisReady = true;
        }
    }

    protected void poop(){
        if(depositSurroundingTile(TileType.NUTRIENT)) {
            hunger--;
            eatingCycleReady = false;
        }
    }

    //To be implemented
    protected void reproductionCycle(){

    }



}
