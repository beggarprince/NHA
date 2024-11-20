package entities.Heroes;

import entities.Direction;
import entities.NPC;
import graphics.ScreenSettings;
import level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Hero extends NPC {

    protected int movementSpeed; // How many pixels an enemy offsets per frame
    protected int movementCycle; // How long it takes before we logically know we are at a new tile without math

    public Hero(int health, int x, int y){
        this.health = health;
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY = y/ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;

    }

    private final static Random random = new Random();

    public abstract void behavior();

    public abstract void destroy();

    public Image getImage() {
        return image;
    }

    protected void resetMovementCycle(){
        if(movementCycle == ScreenSettings.TILE_SIZE){
            movementCycle = 0;
        }
    }
}
