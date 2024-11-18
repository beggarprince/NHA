package entities.Heroes;

import entities.Direction;
import level.Level;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Hero {
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

    private final static Random random = new Random();


}
