package entities.NPC.Heroes;

import entities.NPC.NPC;
import graphics.ScreenSettings;

import java.awt.*;
import java.util.Random;

public abstract class Hero extends NPC {

    protected int movementSpeed; // How many pixels an enemy offsets per frame
    protected int movementCycle; // How long it takes before we logically know we are at a new tile without math

    //TODO remove health from this pos
    public Hero(int health, int x, int y){
        this.health = health;
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY = y/ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementCycle = 0;
        this.movementSpeed = 1;
        this.currDirection = getRandomDirection(worldPositionX, worldPositionY);//This will give it a random starting dir that is valid
    }

    private final static Random random = new Random();

    public Image getImage() {
        return image;
    }

}
