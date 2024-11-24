package entities.Monsters;
import entities.Direction;
import graphics.ScreenSettings;
import level.TileType;
import util.ImgLoader;
import java.awt.image.BufferedImage;
import java.util.List;

import static entities.Monsters.Logic.EatingSystem.*;

public class Slime extends Monster {

    public Slime(int x, int y) {
        super(1, x, y); // Slime has a default health of 1

        this.currDirection = getRandomDirection(worldPositionX, worldPositionY);//This will give it a random starting dir that is valid
        this.hunger = 0;
        this.movementSpeed = 1;
        this.image = ImgLoader.getImageResource("slime.png"); //Default slime preloaded
        this.lifespan = ScreenSettings.FPS * 45;

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

        //TODO extract this so i don't have to copy paste it
        //We see if we can move this direction
        if(validateWalkableDirection(currDirection, worldPositionX, worldPositionY)){
            move(movementSpeed);

            updateWorldPosition();
            resetMovementCycle();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            currDirection = getRandomDirection(worldPositionX, worldPositionY);
        }
        //Eat
        if(eatingCycleReady) {
            if (hunger < maxHunger) eat();
            else poop();
        }

        agingCycle();
    }

    //Extract all the logic, it should only know if it ate and increment itself
    protected void eat(){
        List<Direction> list = getPossibleDirections(false);
       if(eatSurroundingTile(TileType.NUTRIENT, list, worldPositionX,  worldPositionY)) {
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

        List<Direction> list = getPossibleDirections(false);

        if(depositSurroundingTile(TileType.NUTRIENT, list, worldPositionX, worldPositionY)) {
            hunger--;
            eatingCycleReady = false;
        }
    }

    //To be implemented
    protected void reproductionCycle(){

    }



}
