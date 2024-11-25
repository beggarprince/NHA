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
        this.movementCycle = 0;
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
//        int test = worldPositionX;
//        int test2 = worldPositionY;

        //TODO extract this so i don't have to copy paste it
        //We see if we can move this direction
        boolean canMove = true;
        if(movementCycle == 0)  canMove = validateWalkableDirection(currDirection, worldPositionX, worldPositionY);

        if(canMove){
            move(movementSpeed);
            //System.out.println(screenPositionX);
            updateWorldPosition();
            signalNewTile();
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            currDirection = getRandomDirection(worldPositionX, worldPositionY);
        }

//
//        if(worldPositionX - test > 1 || worldPositionX - test < -1){
//            System.out.println("Moved X too many times");
//        }
//        if(worldPositionY - test2 > 1 || worldPositionY - test2 < -1){
//            System.out.println("Moved Y too many times");
//        }
//        if(canMove == false && test != worldPositionX && test2 != worldPositionY){
//            System.out.println("Moved when it should not have");
//        }

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
