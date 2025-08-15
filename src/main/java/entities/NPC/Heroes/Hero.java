package entities.NPC.Heroes;

import   entities.NPC.Monsters.MonsterLogic.MonsterFactory;
import   entities.NPC.Movement;
import   entities.NPC.Mvp;
import   entities.NPC.NPC;
import entities.WorldObjects.SkeletonHead;
import   graphics.ScreenSettings;
import world.World;

import java.awt.*;
import java.util.Random;

public abstract class Hero extends NPC {

    protected int movementSpeed; // How many pixels an enemy offsets per frame
    protected int movementCycle; // How long it takes before we logically know we are at a new tile without math
    protected boolean hasMVP = false;
    public String name;


    public Hero(int health, int x, int y, String heroName){
        this.health = health;
        this.name = heroName;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY = y/ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementCycle = 0;
        this.movementSpeed = 1;
        this.combatCooldown = 60;
        this.currDirection = Movement.getRandomDirection(tilePositionX, tilePositionY, this);//This will give it a random starting dir that is valid
        this.fxIndex = 1;
        //this.spriteType =
        this.pathfinding = new HeroPathfinder(this);
        pathfinding.logPath(false);
        this.hasMVP = false;
        //World.INSTANCE.addHeroToTile(this);
    }

    private final static Random random = new Random();
    protected HeroPathfinder pathfinding;

    public Image getImage() {
        if(image == null) System.out.println("Error null image");
        return image;
    }

    public void kidnap(){
     //   System.out.println("Mvp has been kidnapped");
        this.hasMVP = true;

    }

    protected void spawnSkeletonHead(){
        new SkeletonHead(this.tilePositionX, this.tilePositionY);
    }


}
