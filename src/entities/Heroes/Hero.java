package entities.Heroes;

import entities.Direction;
import graphics.ScreenSettings;
import level.Level;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Hero {
    protected BufferedImage image;
    protected int health;
    protected int lifespan;
    protected Direction currDirection;
    public boolean isDead = false;

    //Logical position on array
    public int worldPositionX;
    public int worldPositionY;
    //Where the hero is drawn on the screen, out of bounds enemies from the camera are not rendered
    //World position
    public int screenPositionX;
    public int screenPositionY;

    protected int movementSpeed; // How many pixels an enemy offsets per frame
    protected int movementCycle; // How long it takes before we logically know we are at a new tile without math
    Level level = Level.getInstance("res/levelTest.csv");

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
}
