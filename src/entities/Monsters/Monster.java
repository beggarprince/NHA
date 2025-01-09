package entities.Monsters;

import entities.NPC;
import graphics.ScreenSettings;
import java.util.Random;
import java.awt.image.BufferedImage;


//TODO REFACTOR THIS, SPLIT INTO DIFFERENT FILES, GET LOGIC OUT OF NPC
public abstract class Monster extends NPC {

    protected int lifespan;
    protected int hunger;
    protected boolean hasFullStomach = false;
    protected boolean eatingCycleReady = true;
    protected int maxHunger;
    public String metamorphosisValue;
    public boolean metamorphosisReady = false;
    private final static Random random = new Random();


    public Monster(int health, int x, int y) {
        this.health = health;
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.hasFullStomach = false;

        this.currDirection = getRandomDirection(worldPositionX, worldPositionY);//This will give it a random starting dir that is valid
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
    //TODO call this indirectly in concrete function to avoid having to manage cooldowns/logic across all enemy types

    public abstract BufferedImage getImage();


    //TODO each npc will need to determine a behavior type based on their current circumstances, atm it'll be a nasty if/else

    protected abstract void eat();

    protected  abstract void agingCycle();

    protected abstract void reproductionCycle();

    protected void death(){
        isDead = true;
        //Add nutrients back to the ecosystem
    }

    //public abstract void attack();




}
