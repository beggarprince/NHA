package main.java.entities.NPC.Monsters;

import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.entities.NPC.Movement;
import main.java.entities.NPC.NPC;
import main.java.entities.NPC.NPCType;
import main.java.graphics.ScreenSettings;
import java.util.Random;
import java.awt.image.BufferedImage;

import static main.java.entities.NPC.NPCLogicKTKt.checkCollisionsEAT;


//TODO REFACTOR THIS, SPLIT INTO DIFFERENT FILES, GET LOGIC OUT OF NPC
public abstract class Monster extends NPC {

    protected int lifespan;
    public int hunger;
    protected boolean hasFullStomach = false;
    protected boolean eatingCycleReady = true;
    public int maxHunger;
    public String metamorphosisValue;
    public boolean metamorphosisReady = false;
    private final static Random random = new Random();

    public static int timesCheckedMonsterLoop = 0;

    public static void incrementLoopCounter(){
        timesCheckedMonsterLoop++;
    }
    public static void resetLoopCounter(){
        timesCheckedMonsterLoop = 0;
    }


    public Monster(int health, int x, int y) {
        this.health = health;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.hasFullStomach = false;

        this.currDirection = Movement.getRandomDirection(tilePositionX, tilePositionY,this);//This will give it a random starting dir that is valid
    }

    // Getter and setter methods
    public int getMonsterHealth() {
        return health;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.health = monsterHealth;
    }


    public int getWorldPositionX() {
        return tilePositionX;
    }

    public int getWorldPositionY() {
        return tilePositionY;
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


    public void signalMonsterAte(){
        eatingCycleReady = false;
    }

    //Removes final references of the object so it can be removed, allows unique deaths based on type bc it's abstract
    //TODO call this indirectly in concrete function to avoid having to manage cooldowns/logic across all enemy types

    public abstract BufferedImage getImage();


    //TODO each npc will need to determine a behavior type based on their current circumstances, atm it'll be a nasty if/else

    public abstract void eat();

    protected  abstract void agingCycle();

    protected abstract void reproductionCycle();

    protected void death(){
        isDead = true;
        //Add nutrients back to the ecosystem
    }

    public void incrementHunger(int amount){
        this.hunger += amount;
    }


    //public abstract void attack();
    protected void basicPredatorEat(Monster monster){
        monster.startAnimation();
        monster.hunger += monster.basicAttackStrength;
        monster.health += monster.basicAttackStrength;
        if(monster.hunger <= monster.maxHunger / 2) {
            monster.hasFullStomach = true;
        }
    }

    protected void basicPredation(NPCType prey){
        //check if they can eat
            if (checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), prey)) {
                //System.out.println("The bug shall engage in eating");
                eat();
            }
            else {
                //If they eat we do not want them moving
                //System.out.println("The bug did not find any food");
                moveNpcAndSignalTrueIfWeMove();
            }

    }

}
