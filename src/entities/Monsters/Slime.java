package entities.Monsters;
import entities.Direction;
import entities.NPCType;
import graphics.ScreenSettings;
import level.TileType;
import util.ImgLoader;
import java.awt.image.BufferedImage;
import java.util.List;


import static entities.Monsters.Logic.EatingSystem.*;

public class Slime extends Monster {


    public Slime(int x, int y) {
        super(1, x, y); // Slime has a default health of 1
        this.hunger = 0;
        this.movementSpeed = 1;
        this.image = ImgLoader.getImageResource("slime.png"); //Default slime preloaded
        this.lifespan = ScreenSettings.FPS * 45;
        this.maxHunger = 1;
        this.movementCycle = 0;
        this.metamorphosisValue = "Slime_Flower";
        this.npc = NPCType.Slime;
        this.basicAttackStrength = 1;
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = ImgLoader.getImageResource("slime.png");
    }

    @Override
    public void destroy() {
        this.image = null;
    }

    // Getter for the image if needed
    public BufferedImage getImage() {
        return image;
    }

    public void behavior(){

        //TODO change it so it selects a function based on current circumstance, behavior should not be abstract and call abstract function MOVE/REPRODUCE/FIGHT


            //We see if we can move this direction
            if (npcMoved()) eatingCycleReady = true; // we are at a new tile;

            //System.out.println(zone);
            //Eat
            if (eatingCycleReady) {
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

    public String returnNpcType(){
        return "Slime";
    }


}
