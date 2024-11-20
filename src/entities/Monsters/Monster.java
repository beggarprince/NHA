package entities.Monsters;

import entities.Direction;
import entities.NPC;
import graphics.ScreenSettings;
import level.TileType;
import level.Level;

import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static util.CollisionKt.detectNPCCollision;
/*
* A clusterfck of functions every enemy will inherit
* ATM handles
*   movement
*   eating
*   eatingCycles
*   knowledge check when the creature is in a new tile
*   pooping back into tiles
* */

//TODO REFACTOR THIS, SPLIT INTO DIFFERENT FILES
public abstract class Monster extends NPC {

    protected int lifespan;
    protected int hunger;
    protected boolean hasFullStomach = false;
    protected boolean eatingCycleReady = true;
    protected int maxHunger;

    public String metamorphosisValue;
    public boolean metamorphosisReady = false;

    Level level = Level.getInstance("res/levelTest.csv");

    private final static Random random = new Random();


    public Monster(int health, int x, int y) {
        this.health = health;
    }

    // Getter and setter methods
    public int getMonsterHealth() {
        return health;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.health = monsterHealth;
    }



    public int getWorldPositionX() {
        return worldPositionX;
    }

    public int getWorldPositionY() {
        return worldPositionY;
    }

    protected void decreaseHunger() {
        hunger--;
    }

    protected void damage(int damage) {
        health -= damage;
    }

    protected void age() {
        lifespan--;
    }

    protected abstract void setImage();


    //Removes final references of the object so it can be removed, allows unique deaths based on type bc it's abstract
    protected abstract void destroy();

    public abstract BufferedImage getImage();

    public abstract void behavior();

    protected abstract void eat();

    protected  abstract void agingCycle();

    protected abstract void reproductionCycle();

    protected void death(){
        isDead = true;
        //Add nutrients back to the ecosystem
    }




    //TODO getting possible directions should be it's own method since it's being used 3 times in different methods
    //TODO there should be a method to removeIf and then do out of bounds, atm we can do remove unwalkable and remove walkable
    protected boolean eatSurroundingTile(TileType t){
        //t is target food

        List<Direction> possibleDirections = getPossibleDirections( false);

        for(Direction d : possibleDirections){
            if (d == Direction.UP && worldPositionY > 0) {
                if(level.tileData.get(worldPositionY - 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY - 1).get(worldPositionX).eatNutrients();

                    return true;
                }
            }
            else if (d == Direction.DOWN && worldPositionY < Level.levelRows -1) {
                if(level.tileData.get(worldPositionY + 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY + 1).get(worldPositionX).eatNutrients();

                    return true;
                }
            }
            else if (d == Direction.LEFT && worldPositionX > 0) {
                if (level.tileData.get(worldPositionY).get(worldPositionX - 1).type == t) {

                    level.tileData.get(worldPositionY).get(worldPositionX - 1).eatNutrients();
                    return true;
                }
            }
            else if (d == Direction.RIGHT && worldPositionX < Level.levelColumns -1 ) {
                if(level.tileData.get(worldPositionY).get(worldPositionX + 1).type == t) {
                    level.tileData.get(worldPositionY).get(worldPositionX + 1).eatNutrients();
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean depositSurroundingTile(TileType t){
        //t is target food

        // List of possible directions
        List<Direction> possibleDirections = getPossibleDirections(false);
        // Since we can only eat non walkable directions we remove if validateWalkableDirection returns true
       // possibleDirections.removeIf(direction -> validateWalkableDirection(direction, worldPosX, worldPosY));
        // whatever is not valid won't be used in the random function

        for(Direction d : possibleDirections){
            if (d == Direction.UP && worldPositionY > 0) {
                if(level.tileData.get(worldPositionY - 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY - 1).get(worldPositionX).depositNutrients();

                    return true;
                }
            }
            else if (d == Direction.DOWN && worldPositionY < Level.levelRows -1) {
                if(level.tileData.get(worldPositionY + 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY + 1).get(worldPositionX).depositNutrients();

                    return true;
                }
            }
            else if (d == Direction.LEFT && worldPositionX > 0) {
                if (level.tileData.get(worldPositionY).get(worldPositionX - 1).type == t) {

                    level.tileData.get(worldPositionY).get(worldPositionX - 1).depositNutrients();
                    return true;
                }
            }
            else if (d == Direction.RIGHT && worldPositionX < Level.levelColumns -1 ) {
                if(level.tileData.get(worldPositionY).get(worldPositionX + 1).type == t) {
                    level.tileData.get(worldPositionY).get(worldPositionX + 1).depositNutrients();
                    return true;
                }
            }
        }
        return false;
    }

    protected void resetMovementCycle(){
        if(movementCycle == ScreenSettings.TILE_SIZE){
            eatingCycleReady = true; // we are at a new tile
            movementCycle = 0;
        }
    }


}
