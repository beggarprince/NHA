package entities.Enemy;

import util.Coordinate;

import java.awt.image.BufferedImage;

import static graphics.imgLoader.getImage;

public abstract class Enemy {
    protected int health;
    protected Coordinate position;
    public int worldPosX;
    public int worldPosY;

    public Enemy(int health, Coordinate position) {
        this.health = health;
        this.position = position;
    }

    // Getter and setter methods
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Coordinate getPosition() {
        return position;
    }
    public int getWorldPosX(){
        return worldPosX;
    }
    public int getWorldPosY(){
        return worldPosY;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    // Abstract method for setting the image, to be implemented by subclasses
    protected abstract void setImage();

    public abstract BufferedImage getImage();

    public abstract void behavior();
    // Other common methods can be added here
}
